/**
 * 
 */
package chat.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * @author lingjiemeng
 *
 */
public class ServerHandler implements Runnable{
	private Socket clientSocket;
//	private BufferedReader input;
	private ObjectInputStream input;
	private PrintWriter output;
	private Map<String, StringBuffer> records;
	/**
	 * 
	 */
	public ServerHandler(Socket clientSocket, Map<String, StringBuffer> records) {
		init(clientSocket, records);
	}


	/**
	 * @param clientSocket
	 */
	private void init(Socket clientSocket, Map<String, StringBuffer> records) {
		this.records = records;
		this.clientSocket = clientSocket;
		try {
//			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			input = new ObjectInputStream(this.clientSocket.getInputStream());
			output = new PrintWriter(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				ClientData clientData = (ClientData) input.readObject();
				String name = clientData.getName();
				String message = clientData.getMessage();
				boolean quitRequest = clientData.isCloseRequest();
				if (quitRequest) {
					break;
				}
				
				StringBuffer tmp = records.get(name);
				if (tmp == null) {
					records.put(name, new StringBuffer(message));
				} else {
					tmp.append(System.getProperty("line.separator"));
					tmp.append(message);
				}
				//System.out.println(records.get(name));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				System.exit(1);
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			
		}
		
		try {
			if (output != null) {
				output.close();
			}
			
			if (input != null) {
				input.close();
			}
			
			if (!clientSocket.isClosed()){
				clientSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
	}

}
