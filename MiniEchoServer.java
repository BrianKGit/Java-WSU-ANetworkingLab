// Simple echo server.
package minilab;
import java.io.*;
import java.net.*;

public class MiniEchoServer {
	static DatagramSocket serverSocket;
	private static final int PORT = 56789;

	public static void main(String[] args) {
		try {
			// Open a UDP datagram socket with a specified port number
			int portNumber = PORT;
			serverSocket = new DatagramSocket(portNumber);

			System.out.println("Echo server starts ...");

			// Create a buffer for receiving
			byte[] receivedData = new byte[2048];
			// Run forever
			while (true) {
				// Create a datagram
				DatagramPacket receivedDatagram =
						new DatagramPacket(receivedData, receivedData.length);

				// Receive a datagram			
				serverSocket.receive(receivedDatagram);

				// Prepare for sending an echo message
				InetAddress destination = receivedDatagram.getAddress();			
				int clientPortNumber = receivedDatagram.getPort();
				int lengthOfMessage = receivedDatagram.getLength();			
				String message = new String(receivedData, 0, receivedDatagram.getLength());

				// Display received message and client address		 
				System.out.println("The received message is: " + message);
				System.out.println("The client address is: " + destination);
				System.out.println("The client port number is: " + clientPortNumber);

				// Create a buffer for sending
				byte[] data = new byte[lengthOfMessage];
				data = message.getBytes();

				// Create a datagram
				DatagramPacket datagram = 
						new DatagramPacket(data, lengthOfMessage, destination, clientPortNumber);

				// Send a datagram carrying the echo message			
				serverSocket.send(datagram);
			}
		} 
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		} 
		finally {
			// Close the socket 
			serverSocket.close();
		}		

	}

}
