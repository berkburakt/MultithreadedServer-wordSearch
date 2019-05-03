// Authors: Berk Burak Taþdemir - 2152171 | Ahmet Emre Onursoy - 1801182

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
 
public class Client
{
 
    private static Socket socket;
 
    public static void main(String args[])
    {
        try
        {
            String host = "localhost";
            int port = 6789;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            OutputStream outputMessage = socket.getOutputStream();
            OutputStreamWriter outputMessageWriter = new OutputStreamWriter(outputMessage);
            BufferedWriter bufferOutputMessage = new BufferedWriter(outputMessageWriter);            
            BufferedReader bufferedMessageReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter a word to count in directory folder: ");
            String keyword = bufferedMessageReader.readLine();            
            String sendKeyword = keyword + "\n";
            bufferOutputMessage.write(sendKeyword);
            bufferOutputMessage.flush();
            System.out.println("Keyword sent to the multithreaded server: " + sendKeyword);
            InputStream inputCountNumber = socket.getInputStream();
            InputStreamReader inputCountReader = new InputStreamReader(inputCountNumber);
            BufferedReader br = new BufferedReader(inputCountReader);
            String message = br.readLine();
            System.out.println("Message received from the server : " + message);
        }       
        catch (Exception exception)
        {
            exception.printStackTrace();
        }       
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}