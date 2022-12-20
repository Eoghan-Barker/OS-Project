import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message, message2, message3, message4;
	private Users users;
	private BugList bugs;

	public ServerThread(Socket s, Users u, BugList b) {
		socket = s;
		users = u;
		bugs = b;
	}

	public void run() {
		Bug tempBug;
		Employee tempEmp;

		// 3. get Input and Output streams
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Conversation from the server to the client
		try {
			do {
				sendMessage(
						"Welcome to the Bug Tracking Server." +
								" Please choose an option\n" +
								"1. Register with the system.\n" +
								"2. Log-in. \n" +
								"3. Exit.");
				message = (String) in.readObject();

				// Handle register
				if (message.equalsIgnoreCase("1")) {
					sendMessage("Enter username:");
					message = (String) in.readObject();

					sendMessage("Enter Employee ID:");
					message2 = (String) in.readObject();

					sendMessage("Enter Email:");
					message3 = (String) in.readObject();

					sendMessage("Enter Department:");
					message4 = (String) in.readObject();

					// Add user to the list
					users.addUser(message, message2, message3, message4);
					// Handle login
				} else if (message.equalsIgnoreCase("2")) {

					sendMessage("Enter Email:");
					message = (String) in.readObject();

					sendMessage("Enter ID:");
					message2 = (String) in.readObject();

					if (validateLogin(message, message2)) {
						sendMessage("Login successful");
						sendMessage("1. Add a bug record\n" +
								"2. Assign a bug to a registered user\n" +
								"3. Display all unassigned bugs\n" +
								"4. Update the status of a bug\n");

						message2 = (String) in.readObject();

						if (message2.equalsIgnoreCase("1")) {
							//add bug

							sendMessage("Enter Application Name:");
							message = (String) in.readObject();

							sendMessage("Choose Platform(Windows, Mac, Unix):");
							message2 = (String) in.readObject();

							sendMessage("Enter problem description:");
							message3 = (String) in.readObject();

							//Add bug to the list
							bugs.addBug(message, message2, message3);


							
						} else if (message2.equalsIgnoreCase("2")) {
							// assign bug
							message = bugs.getBugNames(); 
							sendMessage("Choose a bug to be assigned:\n" + message);
							message = (String) in.readObject();

							message2 = users.getUserNames(); 
							sendMessage("Choose an employee to assign it to:\n" + message2);
							message2 = (String) in.readObject();


							//bugs.assignBug(Integer.parseInt(message), Integer.parseInt(message));
							tempBug = bugs.getABug(Integer.parseInt(message));
							tempEmp = users.getAnEmp(Integer.parseInt(message2));

							tempBug.assignEmployee(tempEmp);

						} else if (message2.equalsIgnoreCase("3")) {
							// view bugs

						} else if (message2.equalsIgnoreCase("4")) {
							// update bug

						}
					} else {
						sendMessage("Login failed");
					}
				}
			} while (!message.equalsIgnoreCase("3"));
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

	private boolean validateLogin(String msg1, String msg2) {
		boolean success = false;
		String msgCheck;

		msgCheck = users.getLoginDetails();
		String[] employees = msgCheck.split("\\?");

		for (int i = 0; i < employees.length; i++) {
			String[] details = employees[i].split("\\*");

			if (details[0].equalsIgnoreCase(msg1) && details[1].equalsIgnoreCase(msg2)) {
				success = true;
				break;
			}
		}
		return success;
	}
}
