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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
public class Main
{
    static List<String> pathList;
    
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
            	int fileCount;
            	int maxThread = 5;
            	
            	String keyword;
            	Socket socket = serverSocket.accept();
                InputStream inputKeywordStream = socket.getInputStream();
                InputStreamReader inputKeywordReader = new InputStreamReader(inputKeywordStream);
                BufferedReader bufferedKeyword = new BufferedReader(inputKeywordReader);
                String keywordClient = bufferedKeyword.readLine();
                System.out.println("Keyword received from client is " + keywordClient);
                keyword = keywordClient;
                
                listDirectoryFiles("C:\\Users\\emreo\\eclipse-workspace\\CNG334_Word_Search\\SamplePath");
                fileCount = new File("C:\\Users\\emreo\\eclipse-workspace\\CNG334_Word_Search\\SamplePath").list().length;  
                
                FileHandler fileHandler = new FileHandler();
                SearchHandler task[] = new SearchHandler[fileCount];
                ExecutorService pool = Executors.newFixedThreadPool(maxThread);   
                for(int i=0;i<fileCount; i++) {
                	task[i] = new SearchHandler(i,keyword, pathList.get(i), fileHandler);
                	pool.execute(task[i]);
                } 
                pool.shutdown();
                pool.awaitTermination(10, TimeUnit.MINUTES);
                wordCount = fileHandler.getWordCount();
                OutputStream outputKeywordCounter;
    			outputKeywordCounter = socket.getOutputStream();
    			PrintWriter writer = new PrintWriter(outputKeywordCounter, true);
    	        writer.println("Total keyword count is equal to "+ wordCount);
    	        System.out.println("Total keyword count is equal to "+ wordCount);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
}



