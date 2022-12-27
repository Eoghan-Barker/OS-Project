import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Provider {
	ServerSocket providerSocket;
	Socket connection = null;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	ServerThread s;
	Users userList;
	BugList bugList;
	

	Provider() {
		// Create employee and bug databases
		userList = new Users();
		bugList = new BugList();
	}

	void run() {
		try {
			// 1. creating a server socket
			providerSocket = new ServerSocket(2004, 10);
			
			// 2. Wait for connection
			while (true) {
				System.out.println("Waiting for connection");
				connection = providerSocket.accept();
				System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				s = new ServerThread(connection, userList, bugList);
				s.start();
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				providerSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	// public static void saveToFile() {
	// 	String filename = "data.txt";

	// 	// Serialization
	// 	try {
	// 		// Saving of object in a file
	// 		FileOutputStream file = new FileOutputStream(filename);
	// 		ObjectOutputStream out = new ObjectOutputStream(file);

	// 		// Method for serialization of object
	// 		//out.writeObject(bugList);
	// 		out.writeObject(userList);

	// 		out.close();
	// 		file.close();

	// 		System.out.println("Object has been serialized");

	// 	}

	// 	catch (IOException ex) {
	// 		System.out.println("IOException is caught");
	// 	}
	// }

	// public static void loadFromFile() {
	// 	String filename = "data.txt";
	// 	// Deserialization
	// 	try {
	// 		// Reading the object from a file
	// 		FileInputStream file = new FileInputStream(filename);
	// 		ObjectInputStream in = new ObjectInputStream(file);

	// 		// Method for deserialization of object
	// 		//bugList = (BugList) in.readObject();
	// 		userList = (Users) in.readObject();

	// 		in.close();
	// 		file.close();

	// 		System.out.println("Object has been deserialized ");
	// 	}

	// 	catch (IOException ex) {
	// 		System.out.println("IOException is caught");
	// 	}

	// 	catch (ClassNotFoundException ex) {
	// 		System.out.println("ClassNotFoundException is caught");
	// 	}

	// }

	public static void main(String args[]) {
		Provider server = new Provider();
		System.out.println("\n\n--Server--\n\n");
		while (true) {
			server.run();
		}
	}
}
