import java.io.File;
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
	File bugFile;
	File usersFile;
	

	Provider() {
		// Create employee and bug databases
		userList = new Users();
		bugList = new BugList();
		usersFile = new File("userList.txt");
		bugFile = new File("bugList.txt");
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
				s = new ServerThread(connection, userList, bugList, usersFile, bugFile);
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

	public static void main(String args[]) {
		Provider server = new Provider();
		System.out.println("\n\n--Server--\n\n");
		while (true) {
			server.run();
		}
	}
}
