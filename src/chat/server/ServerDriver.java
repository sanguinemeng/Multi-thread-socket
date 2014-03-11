package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import chat.util.Checker;
import chat.util.PortChecker;
import chat.util.ServerHandler;
import chat.util.ThreadPool;

public class ServerDriver {

    /**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
    	Map<String, StringBuffer> records = Collections.synchronizedMap(new HashMap<String, StringBuffer>());
    	ThreadPool threadPool = ThreadPool.getInstance();
    	
    	Checker checker = new PortChecker();
    	int port = checker.check(args[0]);
		ServerSocket listener = new ServerSocket(port);	//port: 9191
		
    	while(true){
    		Socket clientSocket = listener.accept();
    		ServerHandler serverHandler = new ServerHandler(clientSocket, records);
    		threadPool.start(serverHandler);
    	}
    }
   
}
