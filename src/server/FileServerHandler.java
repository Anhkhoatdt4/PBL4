package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import UI.ShortestPathRouting;
import client.FileClient;

public class FileServerHandler implements Runnable {
	private Socket clientSocket ; 
	private JTextArea serverTextArea;
	private BufferedReader in;
	private PrintWriter out;
	private String clientName ;
	private JComboBox<String> comboBox;
	protected  boolean turn = true;
	public Map<Integer, String> messageMap = new HashMap<>();
	
	public FileServerHandler() {
	}
	
	public FileServerHandler(Socket clientSocket , JTextArea serverTextArea, String name , JComboBox<String> comboBox) {
		this.clientSocket = clientSocket;
		this.serverTextArea = serverTextArea;
		this.clientName = name ;
		this.comboBox = comboBox;
		
	}

	@Override
	public void run() {
	    try {      
	        serverTextArea.append("Client đã kết nối từ địa chỉ: " + clientSocket.getInetAddress() + " và port: " + clientSocket.getPort() + "\n");
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        out.println("Server is running on port: " + clientSocket.getLocalPort());

	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	            if (inputLine.equals("QUIT")) {
	                break;
	            }
	            System.out.println("Received from client: " + inputLine); 
	            processClientRequest(inputLine);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        stop();
	    }
	}

	private void processClientRequest(String request) {
	    if (request.startsWith("PORT:")) {
	        String port = request.substring(5).trim();
	        clientName = "Client " + port;
	        comboBox.addItem("Client " + port);
	    }

	    
	    
	    Object StringBuilder;
		if (request.startsWith("FILE_PATH:")) {
	    	sendMessageToServer(clientName + " đã gửi yêu cầu " + request);
	        String filePath = request.substring(10).trim();
	        sendMessageToServer(clientName + " đã gửi đường dẫn file: " + filePath);
	        ShortestPathRouting shortestPathRouting = new ShortestPathRouting(filePath, null);
	        shortestPathRouting.showShortestPathRouting();
	    } else if (request.startsWith("MANUAL_INPUT:")) {
	        StringBuilder manualInputBuilder = new StringBuilder();
	        String manualInput = request.substring(13).trim();
	      
	        while (!manualInput.equals("END")) {
	            manualInputBuilder.append(manualInput).append("\n");
	            try {
	                manualInput = in.readLine();
	                if (manualInput == null) {
	                    break; 
	                    }
	            } catch (IOException e) {
	                e.printStackTrace();
	                break; 
	            }
	        }

	        String[] manualInputArray = manualInputBuilder.toString().split("\\n");
	        sendMessageToServer(clientName + " đã gửi dữ liệu nhập thủ công: \n" + manualInputBuilder.toString());
	        
	        ShortestPathRouting shortestPathRouting = new ShortestPathRouting(null, manualInputArray);
	        shortestPathRouting.showShortestPathRouting();
	    } else if (request.startsWith("DISCONNECT")) {
	        sendMessageToServer(clientName + " đã ngắt kết nối.\n");
	    }
	    else if (request.startsWith("CONTENT:")) {
	    	StringBuilder contentBuilder = new StringBuilder();
	    	String message = request.substring(8).trim();
	    	contentBuilder.append(message).append("\n");
	    	while(true) {
	    		try {
					String nextLine = in.readLine();
					   if (nextLine == null || nextLine.trim().isEmpty()) {
			                break;
			            }
					   contentBuilder.append(nextLine).append("\n");
				} catch (Exception e) {
					 e.printStackTrace();
			            break;
				}
	    	}
	    	    String fullContent = contentBuilder.toString().trim();
	    	    System.out.println("Full content nhận được: \n" + fullContent);
	    	  
	    	    int port = clientSocket.getPort();
	    	    storeMessage(port, fullContent); 
	    }
	}


	
	public int getClientPort() {
		return clientSocket.getPort();
	}
	

	private void sendMessageToServer(String message) {
	    SwingUtilities.invokeLater(() -> serverTextArea.append(message + "\n"));
	}
	
    private void storeMessage(int port, String message) {
        messageMap.put(port, message);
        System.out.println("Stored message for port " + port + ": " + message);
    }
	public void stop() {
		try {
			if(!clientSocket.isClosed()) {
				clientSocket.close();
			}
		} catch (Exception e) {
			System.out.println("con cac");
			e.printStackTrace();
		}
	}

}
