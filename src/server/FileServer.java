package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JTextField;

public class FileServer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea textArea_1 = new JTextArea();
    private ServerSocket serverSocket;
    private ArrayList<FileServerHandler> clients = new ArrayList<>();
    private JTextField portField;
    private JComboBox<String> comboBox;
    private JButton btnStart;
    private JButton btnStop;
    private boolean isRunning = false;
    private String serverMessage = "";
 
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FileServer frame = new FileServer();
                frame.setTitle("Server");
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FileServer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnStop = new JButton("Stop");
        btnStop.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        btnStop.setBounds(306, 68, 91, 21);
        btnStop.setEnabled(false);
        btnStop.addActionListener(e -> stopServer());
        getContentPane().add(btnStop);

        textArea_1.setBounds(-35, 8, 349, 192);
        contentPane.add(textArea_1);

        comboBox = new JComboBox<>();
        comboBox.setBounds(45, 68, 80, 21);
        contentPane.add(comboBox);

        btnStart = new JButton("Start");
        btnStart.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        btnStart.setBounds(189, 68, 85, 21);
        btnStart.addActionListener(e -> startServer());
        contentPane.add(btnStart);

        JLabel lblPort = new JLabel("Port:");
        lblPort.setBounds(20, 10, 40, 20);
        contentPane.add(lblPort);
        
        JScrollPane scrollPane = new JScrollPane(textArea_1);
        scrollPane.setBounds(45, 120, 349, 192); 
        contentPane.add(scrollPane);
        
        portField = new JTextField("8080");
        portField.setBounds(60, 10, 100, 20);
        contentPane.add(portField);

        addComboBoxActionListener();
    }
    
    private void addComboBoxActionListener() {
        comboBox.addActionListener(e -> {
        	if (saveText().length() > serverMessage.length()) {
            serverMessage = textArea_1.getText();
        	System.out.println(serverMessage);
        	}
            String selectedClient = (String) comboBox.getSelectedItem();

            if (selectedClient != null && selectedClient.split(" ").length > 1) {
                int clientPort = Integer.parseInt(selectedClient.split(" ")[1]);
                FileServerHandler fileServerHandler = findClientHandlerByPort(clientPort);

                String message = null;

                for (Map.Entry<Integer, String> entry : fileServerHandler.messageMap.entrySet()) {
                    if (entry.getKey() == clientPort) {
                        message = entry.getValue();
                        break;
                    }
                }

                if (message != null) {
                    textArea_1.setText("");
                    textArea_1.append(message); 
                } else {
                    textArea_1.setText(""); 
                    textArea_1.append("No message from Client " + clientPort);
                }
            }
            else if (selectedClient != null && selectedClient.equals("Server")) {
                textArea_1.setText(serverMessage);
            }
        });
    }

    public String saveText() {
    	return textArea_1.getText();
    }
    
    public void startServer() {
        new Thread(() -> {
            try {
                int port = Integer.parseInt(portField.getText());

                if (isRunning) {
                    textArea_1.append("Server đang chạy trên port " + port + ". Không thể khởi động lại.\n");
                    return;
                }

                try {
                    serverSocket = new ServerSocket(port);
                    isRunning = true; 
                    String initialMessage = "Server đang chạy trên port " + port + "\n";
                    textArea_1.append(initialMessage);
                    comboBox.addItem("Server");

                    btnStart.setEnabled(false);
                    btnStop.setEnabled(true);

                    while (isRunning) {
                        Socket clientSocket = serverSocket.accept();
                        FileServerHandler fileServerHandler = new FileServerHandler(clientSocket, textArea_1, "", comboBox);
                        clients.add(fileServerHandler);
                        new Thread(fileServerHandler).start();
                    }

                } catch (BindException e) {
                    textArea_1.append("Cổng " + port + " đã được sử dụng. Vui lòng dừng server trước khi khởi động lại.\n");
                }
            } catch (NumberFormatException e) {
                textArea_1.append("Cổng không hợp lệ. Vui lòng nhập một số.\n");
              
            } catch (IOException e) {
              
            }
        }).start();
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                isRunning = false;  
                String stopMessage = "Server đã dừng.\n";
                textArea_1.append(stopMessage);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            }
        } catch (IOException e) {
            
        }
    }

    private FileServerHandler findClientHandlerByPort(int port) {
        for (FileServerHandler fileServerHandler : clients) {
            if (fileServerHandler.getClientPort() == port) {
                return fileServerHandler;
            }
        }
        return null;
    }
}
