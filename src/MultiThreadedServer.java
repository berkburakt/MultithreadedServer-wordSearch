// Authors: Berk Burak Taþdemir - 2152171 | Ahmet Emre Onursoy - 1801182

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
public class MultiThreadedServer implements Runnable
{
    private static Socket socket;
    private String fileName;
    static List<String> pathList;
    static int threadCount = 1;
    static String keyword;
    private int wordCount;
    private static int totalWord;
    MultiThreadedServer(Socket clientsocket, String fileName, int wordCount) {
		this.socket = clientsocket;
		this.fileName = fileName;
		this.wordCount = wordCount;
	}
    public static void main(String[] args)
    {
        try
        {
 
            int port = 6789;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening to the port 6789");
            while(true)
            {
            	int wordCount = 0;
            	Socket socket = serverSocket.accept();
                InputStream inputKeywordStream = socket.getInputStream();
                InputStreamReader inputKeywordReader = new InputStreamReader(inputKeywordStream);
                BufferedReader bufferedKeyword = new BufferedReader(inputKeywordReader);
                String keywordClient = bufferedKeyword.readLine();
                System.out.println("Keyword received from client is " + keywordClient);
                keyword = keywordClient;
                listDirectoryFiles("C:\\Users\\emreo\\eclipse-workspace\\CNG334_Word_Search\\SamplePath");
                threadCount = new File("C:\\Users\\emreo\\eclipse-workspace\\CNG334_Word_Search\\SamplePath").list().length;               
                for(int i=0;i<threadCount; i++) {
                	new Thread(new MultiThreadedServer(socket, pathList.get(i), wordCount)).start();
                }      
                OutputStream outputKeywordCounter;
    			outputKeywordCounter = socket.getOutputStream();
    			PrintWriter writer = new PrintWriter(outputKeywordCounter, true);
    	        writer.println("Total keyword count is equal to "+ totalWord);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){
            	System.out.println(e);
            }
        }
        
    }
    
    public static void listDirectoryFiles(String directoryPath){
    	try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {

    		List<String> result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
    		pathList = result;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}

    }
    
    public static int readContent(String directoryFilePath, int wordCount) throws IOException{
    	Path path = Paths.get(directoryFilePath);
        List<String> keywordList = Files.readAllLines(path);
        for(String key : keywordList) {
        	if(keyword.equals(key)) {
        		wordCount++;
        	}
        }
        //System.out.println("" + keywordList);
        return wordCount;
        
    }

	@Override
	public void run() {				
		try {
			wordCount = readContent(fileName, wordCount);
			totalWord += wordCount;
			System.out.println("Thread" + Thread.currentThread() + " - Keyword count is equal to " + wordCount + " in file: " + fileName);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
    
}