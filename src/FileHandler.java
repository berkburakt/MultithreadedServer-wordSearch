import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class FileHandler {
	private int wordCount;
	FileHandler(){
		wordCount = 0;
	}
	public void search(String directoryFilePath,String keyword) {
		int counter = 0;
		Path path = Paths.get(directoryFilePath);
        List<String> keywordList;
		try {
			keywordList = Files.readAllLines(path);
			for(String key : keywordList) {
	        	if(keyword.equals(key)) {
	        		counter++;
	        		wordCount++;
	        	}
	        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread" + Thread.currentThread() + " - Keyword count is equal to " + counter + " in file: " + directoryFilePath);
        //System.out.println("" + keywordList);
	}
	public int getWordCount() {
		return wordCount;
	}
}