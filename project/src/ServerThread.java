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

	public ServerThread(Socket s) {
		socket = s;
	}

	public void run() {
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
								"2. Log-in");
				message = (String) in.readObject();

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
				// } else if (message.equalsIgnoreCase("2")) {
				// 	// message = "name"+"*"+"author"+"?"+"name1"+"*"+"author1"+"?";
				// 	message = lib.getList();

				// 	// Option 1
				// 	// sendMessage(message);

				// 	// Option2
				// 	String[] books = message.split("\\?");

				// 	sendMessage("" + books.length);

				// 	for (int i = 0; i < books.length; i++) {
				// 		String[] details = books[i].split("\\*");

				// 		sendMessage(details[0]);
				// 		sendMessage(details[1]);

				// 	}
				}
				// message2 = users.getList();
				// sendMessage(message2);

				sendMessage("Please enter 1 to repeat or 2 to exit");
				message = (String) in.readObject();

			} while (message.equalsIgnoreCase("1"));
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

}
