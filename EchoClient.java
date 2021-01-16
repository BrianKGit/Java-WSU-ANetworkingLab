// Simple echo client.
package lab1;
import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {
	static DatagramSocket clientSocket;

	public static void main(String[] args) throws Exception {
		try {
			// Open a UDP datagram socket
			clientSocket = new DatagramSocket();
			InetAddress destination = null;
			InetAddress serverDestination = null;

			Scanner in = new Scanner(System.in);

			// Prepare for transmission
			// Determine server IP address

			// Approach 1: from the host name
			// Our example is "localhost"
			/*
			String hostname = "localhost";
			try {
				destination = InetAddress.getByName(hostname);
			} catch (UnknownHostException e) {
				System.out.println("Unable to determine the host by name!");
				System.exit(-1);
			}
			*/

			// Approach 2: from the IP address
			//*
			//byte [] b = new byte[] {(byte)172,(byte)16,(byte)68,(byte)78};
			//byte [] b = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
			//int IGPortNumber = 0;
			int serverPortNumber = 0;
			try {
				//System.out.print("Please enter IG IP address: ");
		        //destination = InetAddress.getByName(in.nextLine());
		        //System.out.print("Please enter IG port number: ");
		        //IGPortNumber = in.nextInt();
		        //in.nextLine();
		        System.out.print("Please enter Server IP address: ");
		        serverDestination = InetAddress.getByName(in.nextLine());
		        System.out.print("Please enter Server port number: ");
		        serverPortNumber = in.nextInt();
			} catch (UnknownHostException e) {
				System.out.println("Unable to determine the host by address!");
				System.exit(-1);
			}
			// */

			// Determine server port number
			//int serverPortNumber = 56789;

			// Message and its length
			System.out.print("Enter message to send: ");
			in.nextLine();
			String message = in.nextLine();
			//int lengthOfMessage = 6 + message.length();
			int lengthOfMessage = message.length();
			byte[] data = new byte[lengthOfMessage + 6];
			System.arraycopy(serverDestination.getAddress(), 0, data, 0, 4);
			
			//caste port number from int to bytes
			byte lowByte = (byte) (serverPortNumber);
			byte highByte = (byte) ((serverPortNumber >> 8));
			data[4] = highByte;
			data[5] = lowByte;
					
			System.arraycopy(message.getBytes(), 0, data, 6, message.length());
			//data = message.getBytes();

			// Create a datagram
			DatagramPacket datagram =
					new DatagramPacket(data, lengthOfMessage, serverDestination, serverPortNumber);

			// Send a datagram carrying the message
			clientSocket.send(datagram);

			// Print out the message sent
			System.out.println("Message sent is:   [" + message + "]");

			// Prepare for receiving
			// Create a buffer for receiving
			byte[] receivedData = new byte[2048];

			// Create a datagram
			DatagramPacket receivedDatagram =
					new DatagramPacket(receivedData, receivedData.length);

			// Receive a datagram
			clientSocket.receive(receivedDatagram);

			// Display the message in the datagram
			String echoMessage = new String(receivedData, 0, receivedDatagram.getLength());
			System.out.println("Message echoed is: [" + echoMessage + "]");
		}
		catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		finally {
			// Close the socket
			clientSocket.close();
		}
	}

}
