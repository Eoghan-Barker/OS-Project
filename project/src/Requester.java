import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Requester {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message, message2;
	Scanner input;

	Requester() {
		// initialize scanner
		input = new Scanner(System.in);
	}

	void run() {
		try {
			// 1. creating a socket to connect to the server
			requestSocket = new Socket("127.0.0.1", 2004);
			System.out.println("Connected to localhost in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());

			// 3: Communicating with the server from the client
			do {
				// 0 - receieve first menu, send choice 1-3
				message = (String) in.readObject();
				System.out.println(message);
				message = input.nextLine();
				sendMessage(message);

				// Handle register
				if (message.equalsIgnoreCase("1")) {
					// 1.1 - Enter registration details
					for (int i = 0; i < 4; i++) {
						message = (String) in.readObject();
						System.out.println(message);
						message = input.nextLine();
						sendMessage(message);
					}

					// 1.2 - Receieve registration success/failure message
					message = (String) in.readObject();
					System.out.println(message);

				// Handle login
				} else if (message.equalsIgnoreCase("2")) {

					// 2.1 - Enter email
					message = (String) in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);

					// 2.2 - Enter ID
					message = (String) in.readObject();
					System.out.println(message);
					message = input.nextLine();
					sendMessage(message);

					// 2.3 - Receive login success/failure message
					message = (String) in.readObject();
					System.out.println(message);

					// Handle 2nd menu
					if (message.equalsIgnoreCase("Login successful")) {
						// Loop to stay in this menu until user logs out
						do {
							// 3.0 - Get 2nd menu and send user choice 1-5
							message = (String) in.readObject();
							System.out.println(message);
							message2 = input.nextLine();
							sendMessage(message2);

							// Add a bug
							if (message2.equalsIgnoreCase("1")) {

								// 3.1 - Enter bug details
								for (int i = 0; i < 3; i++) {
									message = (String) in.readObject();
									System.out.println(message);
									message = input.nextLine();
									sendMessage(message);
								}
							
							// Assign Bug to a User
							} else if (message2.equalsIgnoreCase("2")) {

								// 3.2 - Select a bug and a user to assign it to
								for (int i = 0; i < 2; i++) {
									message = (String) in.readObject();
									System.out.println(message);
									message = input.nextLine();
									sendMessage(message);
								}

							// Display unnassigned bugs
							} else if (message2.equalsIgnoreCase("3")) {

								// 3.3 - Print unnassigned bugs
								message = (String) in.readObject();
								System.out.println(message);

							// Update bug status
							} else if (message2.equalsIgnoreCase("4")) {

								// 3.4 - Prompt
								message = (String) in.readObject();
								System.out.println(message);

								// 3.5 - Display list of bugIDs and send choice
								message = (String) in.readObject();
								System.out.println(message);
								message = input.nextLine();
								sendMessage(message);

								// 3.6 - Set new status
								message = (String) in.readObject();
								System.out.println(message);
								message = input.nextLine();
								sendMessage(message);
							}
						} while (!message2.equalsIgnoreCase("5"));
					}
				}
			} while (!message.equalsIgnoreCase("3"));
		} catch (ClassNotFoundException e) {

		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Requester client = new Requester();
		System.out.println("\n\n--Client--\n\n");
		client.run();
	}
}