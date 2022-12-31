import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;

public class ServerThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message, message2, message3, message4;
	private Users users;
	private BugList bugs;

	public ServerThread(Socket s, Users u, BugList b, File bf, File uf) {
		socket = s;
		users = u;
		bugs = b;
		// load in the lists
		try {
			// create the files if they dont exist
			if(bf.createNewFile()) {
				System.out.println("Bug file created");
			}
			else {
				bugs.loadBugList();
				System.out.println("Bug list loaded");
			}
			
			if(uf.createNewFile()) {
				System.out.println("User file created");
			}
			else {
				users.loadUserList();
				System.out.println("User list loaded");
			}
		} catch (IOException e) {
			System.out.println("Error loading files");
			e.printStackTrace();
		}
	}

	public void run() {
		Bug tempBug;
		Employee tempEmp;

		// 2. get Input and Output streams
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3: Conversation from the server to the client
		try {
			do {
				// 0 - send first menu, recieve option 1-3
				sendMessage(
						"Welcome to the Bug Tracking Server." +
								" Please choose an option\n" +
								"1. Register with the system.\n" +
								"2. Log-in. \n" +
								"3. Exit.");
				message = (String) in.readObject();

				// Handle register
				if (message.equalsIgnoreCase("1")) {
					// 1.1 - Send registration details
					sendMessage("Enter username:");
					message = (String) in.readObject();
					sendMessage("Enter Employee ID:");
					message2 = (String) in.readObject();
					sendMessage("Enter Email:");
					message3 = (String) in.readObject();
					sendMessage("Enter Department:");
					message4 = (String) in.readObject();

					// Check if email and ID are unique
					if (checkUnique(message3, message2)) {
						// Add user to the list
						users.addUser(message, message2, message3, message4);
						// 1.2 - Send registration successful
						sendMessage("\n\nRegistration successful.\n\n");
					} else {
						// 1.2 - Send registration failed
						sendMessage("\n\nEmail and ID must be unique.\n\n");
					}

				// Handle login
				} else if (message.equalsIgnoreCase("2")) {

					// 2.1 - Send email request
					sendMessage("Enter Email:");
					message = (String) in.readObject();

					// 2.2 - Send ID request
					sendMessage("Enter ID:");
					message2 = (String) in.readObject();

					// Check if email and ID are registered
					if (validateLogin(message, message2)) {
						//2.3 - Send login successful message
						sendMessage("Login successful");

						// Loop to stay in this menu until user logs out
						do {
							// 3.0 - Send 2nd menu and get user choice 1-5
							sendMessage("1. Add a bug record\n" +
									"2. Assign a bug to a registered user\n" +
									"3. Display all unassigned bugs\n" +
									"4. Update the status of a bug\n" +
									"5. Logout\n");
							message2 = (String) in.readObject();

							// add bug
							if (message2.equalsIgnoreCase("1")) {

								// 3.1 - Get bug details
								sendMessage("Enter Application Name:");
								message = (String) in.readObject();
								sendMessage("Choose Platform(Windows, Mac, Unix):");
								message2 = (String) in.readObject();
								sendMessage("Enter problem description:");
								message3 = (String) in.readObject();

								// Add bug to the list
								bugs.addBug(message, message2, message3);

							// Assign Bug to a User
							} else if (message2.equalsIgnoreCase("2")) {
								
								// 3.2 - Display all bugs and users and get users choice
								message = bugs.getBugIDs();
								sendMessage("Choose the bugID of the bug to be assigned:\n" + message);
								message = (String) in.readObject();
								message2 = users.getUserNames();
								sendMessage("Choose an employee to assign it to:\n" + message2);
								message2 = (String) in.readObject();

								// Get selected bug and user
								tempBug = bugs.getABug(Integer.parseInt(message));
								tempEmp = users.getAnEmp(Integer.parseInt(message2) - 1);
								// Assign bugId to the employee and set bug status to assigned
								tempEmp.assignBug(tempBug.getBugID());
								tempBug.setStatus(3);

							// Display unnassigned bugs
							} else if (message2.equalsIgnoreCase("3")) {
								
								message = "Unassigned Bugs: \n";
								// Search through each bug in the list and add it to 
								// the message if it's status is open 
								for (int i = 0; i < bugs.getTotalBugs(); i++) {
									tempBug = bugs.getABug(i);
									if (tempBug.getStatus() == Status.Open) {
										message = message +
												"Bug ID: " + tempBug.getBugID() + " \n" +
												"App Name: " + tempBug.getName() + " \n" +
												"Date: " + tempBug.getDate() + "\n" +
												"Platform: " + tempBug.getPlatform() + "\n" +
												"Description: " + tempBug.getDescription() + "\n\n";
									}
								}

								// 3.3 - Send list of unnassigned bugs
								sendMessage(message);

							// Update bug status
							} else if (message2.equalsIgnoreCase("4")) {

								// 3.4 - Prompt
								sendMessage("Choose the bug you wish to update the status of: \n");
								
								// 3.5 - Send list of bugIDs and get users choice
								message = bugs.getBugIDs();
								sendMessage(message);
								message = (String) in.readObject();

								// 3.6 - Get new status
								sendMessage("Choose a Status(1 or 2):\n" +
										"1) Open\n2) Closed\n");
								message2 = (String) in.readObject();

								// update the status of selected bug
								bugs.getABug(Integer.parseInt(message)).setStatus(Integer.parseInt(message2));
								// If status set to closed the remove bug from list
								if (message2.equalsIgnoreCase("2")) {
									bugs.removeBug(Integer.parseInt(message));
								}
							}
						} while (!message2.equalsIgnoreCase("5"));
						// Save the shared objects to file
						bugs.saveBugList();
						users.saveUserList();
					} else {
						// 2.3 - send login failure message
						sendMessage("Login failed");
					}
				}
			} while (!message.equalsIgnoreCase("3"));

			// Provider.saveToFile();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {

		}
	}

	

	public void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// Check if login details match a user in the list
	private boolean validateLogin(String msg1, String msg2) {
		boolean success = false;
		String msgCheck;
		String[] employees;
		String[] details;

		msgCheck = users.getLoginDetails();
		employees = msgCheck.split("\\?");

		for (int i = 0; i < employees.length; i++) {
			details = employees[i].split("\\*");

			if (details[0].equalsIgnoreCase(msg1) && details[1].equalsIgnoreCase(msg2)) {
				success = true;
				break;
			}
		}
		return success;
	}

	private boolean checkUnique(String email, String id) {
		boolean unique = true;
		String msgCheck;

		msgCheck = users.getLoginDetails();

		// If list is empty then login is unique
		if (!msgCheck.equalsIgnoreCase("")) {
			String[] employees = msgCheck.split("\\?");

			for (int i = 0; i < employees.length; i++) {

				String[] details = employees[i].split("\\*");

				if (details[0].equalsIgnoreCase(email) || details[1].equalsIgnoreCase(id)) {
					unique = false;
					break;
				}
			}
		}

		return unique;
	}
}
