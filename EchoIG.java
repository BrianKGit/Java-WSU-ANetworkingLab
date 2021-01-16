// Simple echo server.
package lab1;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class EchoIG {
	static DatagramSocket IGSocket;
	private static final int PORT = 56789;

	public static void main(String[] args) {
		try {
			// Open a UDP datagram socket with a specified port number

			Scanner in = new Scanner(System.in);
			//System.out.print("Enter Port Number: ");
	        //int portNumber = in.nextInt();

			int portNumber = PORT;

			IGSocket = new DatagramSocket(portNumber);

			System.out.println("Echo IG starts ...");

			// Create a buffer for receiving
			byte[] receivedData = new byte[2048];
			// Run forever
			while (true) {
				// Create a datagram
				DatagramPacket receivedDatagram =
						new DatagramPacket(receivedData, receivedData.length);

				// Receive a datagram
				IGSocket.receive(receivedDatagram);
				byte[] addressFromData = Arrays.copyOf(receivedData, 4);
				
				// Prepare for sending an echo message
				InetAddress clientIP = receivedDatagram.getAddress();
				InetAddress serverIP = InetAddress.getByAddress(addressFromData); //get first 4 bytes from the receivedData for the IP address
				//System.out.println(new String(receivedData, 0, 3));
				int clientPortNumber = receivedDatagram.getPort();
				int serverPortNumber = (0xffff & (receivedData[4] << 8)) + (0xff & receivedData[5]);
				int lengthOfMessage = receivedDatagram.getLength() - 6;
				String message = new String(receivedData, 6, receivedDatagram.getLength());


				// Display received message and client address
				System.out.println("The client message is: " + message);
				System.out.println("The client address is: " + clientIP);
				System.out.println("The client port number is: " + clientPortNumber);

				// Create a buffer for sending
				byte[] data = new byte[lengthOfMessage];
				data = message.getBytes();

				// Create a datagram
				DatagramPacket datagram =
						new DatagramPacket(data, lengthOfMessage, serverIP, serverPortNumber);

				// Send a datagram carrying the echo message
				IGSocket.send(datagram);
				//System.out.println("made it");
				
				// Create a buffer for sending
				data = new byte[lengthOfMessage];
				data = message.getBytes();

				// Create a datagram
				DatagramPacket receivedEchoDatagram =
						new DatagramPacket(receivedData, receivedData.length);

				// Receive a datagram
				IGSocket.receive(receivedEchoDatagram);

				String echoMessage = new String(receivedData, 0, receivedEchoDatagram.getLength());

				// Display echo message and server address
				System.out.println("The server message is: " + echoMessage);
				System.out.println("The server address is: " + serverIP);
				System.out.println("The server port number is: " + serverPortNumber);

				// Create a datagram
				DatagramPacket echoDatagram =
						new DatagramPacket(data, lengthOfMessage, clientIP, clientPortNumber);

				// Send a datagram carrying the echo message
				IGSocket.send(echoDatagram);
			}
		}
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		finally {
			// Close the socket
			IGSocket.close();
		}

	}

}
