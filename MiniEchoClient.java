// Simple echo client.
package minilab;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MiniEchoClient {
	static DatagramSocket clientSocket;
	static int TIMEOUT = 5000;
	static boolean flag = true;

	public static void main(String[] args) throws Exception {
		try {
			// Open a UDP datagram socket
			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(TIMEOUT);
			InetAddress destination = null;

			Scanner in = new Scanner(System.in);

			int portNumber = 0;
			try {
	        	//destination = InetAddress.getByAddress(b);
		        System.out.print("Please enter Server IP address: ");
		        destination = InetAddress.getByName(in.nextLine());
		        System.out.print("Please enter Server port number: ");
		        portNumber = in.nextInt();
			} catch (UnknownHostException e) {
				System.out.println("Unable to determine the host by address!");
				System.exit(-1);
			}

			// Determine server port number ** Server port number is hard coded to 56789 for the purpose of this assignment

			// Message and its length		
			System.out.print("Enter message to send: ");
			in.nextLine();
			String message = in.nextLine();
			int lengthOfMessage = message.length(); 
			byte[] data = new byte[lengthOfMessage];
			data = message.getBytes();

			// Create a datagram
			DatagramPacket datagram = 
					new DatagramPacket(data, lengthOfMessage, destination, portNumber);
			while(flag) {
				try {
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
					flag = false;
				} catch (SocketTimeoutException e) {
					System.out.println("Timeout and Retransmission");
					clientSocket.send(datagram);
				}
			}
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
