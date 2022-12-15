
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	Scanner input;

	Requester() {

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
				message = (String) in.readObject();
				System.out.println(message);
				message = input.nextLine();
				sendMessage(message);

				// if (message.equalsIgnoreCase("1")) {
				// 	message = (String) in.readObject();
				// 	System.out.println(message);
				// 	message = input.nextLine();
				// 	sendMessage(message);

				// 	message = (String) in.readObject();
				// 	System.out.println(message);
				// 	message = input.nextLine();
				// 	sendMessage(message);
				// }

				// else if (message.equalsIgnoreCase("2")) {
				// 	// Option 1
				// 	/*
				// 	 * message = (String)in.readObject();
				// 	 * System.out.println(message);
				// 	 * 
				// 	 * String[] books = message.split("\\?");
				// 	 * 
				// 	 * for(int i=0;i<books.length;i++)
				// 	 * {
				// 	 * System.out.println(books[i]);
				// 	 * 
				// 	 * String[] components = books[i].split("\\*");
				// 	 * 
				// 	 * System.out.println("Book "+i+" "+components[0]+" "+components[1]);
				// 	 * }
				// 	 */

				// 	// Option 2
				// 	message = (String) in.readObject();
				// 	int numBooks = Integer.parseInt(message);

				// 	for (int i = 0; i < numBooks; i++) {
				// 		message = (String) in.readObject();
				// 		System.out.println(message);

				// 		message = (String) in.readObject();
				// 		System.out.println(message);
				// 	}

				// }

				// message = (String) in.readObject();
				// System.out.println(message);
				// message = input.nextLine();
				// sendMessage(message);
			} while (message.equalsIgnoreCase("1"));
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