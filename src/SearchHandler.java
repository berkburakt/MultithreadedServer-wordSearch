class SearchHandler implements Runnable 
{ 
    private String fileName;
    private String keyword;
    private int taskNo;
    FileHandler fileHandler; 

    SearchHandler(int taskNo ,String keyword ,String fileName,  FileHandler fileHandler) 
    { 
    	this.taskNo = taskNo;
    	this.fileName = fileName; 
    	this.fileHandler = fileHandler;
    	this.keyword = keyword;
    }
  
    public void run() 
    { 
        synchronized(fileHandler) 
        { 
        	
        	fileHandler.search(fileName, keyword);
        	fileHandler.notifyAll();
        }
        System.out.println("Task " +  taskNo + " is completed.");       
    }
} 