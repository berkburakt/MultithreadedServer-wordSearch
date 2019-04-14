// Authors: Berk Burak Tasdemir - 2152171 | Ahmet Emre Onursoy - 1801182

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadedServer implements Runnable{
	Socket connectionSocket;
	MultiThreadedServer(Socket clientsocket) {
		this.connectionSocket = clientsocket;
	}
	public static void main(String[] args) throws Exception
	{
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("Local Server Port: 6789 Listening");
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Connected");		
			new Thread(new MultiThreadedServer(connectionSocket)).start();
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public void run() {
		System.out.println("Thread: " + Thread.currentThread());
		try {
			connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
}