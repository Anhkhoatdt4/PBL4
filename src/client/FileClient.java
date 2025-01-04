package client;

import javax.swing.*;

import UI.ShortestPathRouting;
import input.FileRead;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class FileClient extends JFrame {

    private JTextField filePathField;
    private JButton importButton, manualInputButton, connectButton;
    private JTextArea inputArea;
    private Socket socket;
    private PrintWriter out;
    private boolean isManualInput = false;
    private JButton btnNhpFile;
    private boolean isConnected = false;
    private static FileClient currentClient; 
    
    public static void setCurrentClient(FileClient client) {
        currentClient = client;
    }
    
    public static FileClient getCurrentClient() {
        return currentClient;
    }
    
    public boolean getConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public FileClient() {
        setTitle("Client");
        setSize(445, 389);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        
        JLabel fileLabel = new JLabel("Chọn file:");
        fileLabel.setBounds(24, 52, 100, 20);
        getContentPane().add(fileLabel);

        filePathField = new JTextField();
        filePathField.setBounds(113, 53, 180, 20);
        getContentPane().add(filePathField);

        importButton = new JButton("Import file");
        importButton.setBounds(300, 52, 120, 20);
        getContentPane().add(importButton);

        manualInputButton = new JButton("Nhập thủ công");
        manualInputButton.setBounds(128, 80, 150, 20);
        getContentPane().add(manualInputButton);

        connectButton = new JButton("Kết nối");
        connectButton.setBounds(150, 312, 120, 30);
        getContentPane().add(connectButton);

        inputArea = new JTextArea(); 
        inputArea.setEditable(false);
        inputArea.setBounds(20, 110, 400, 192);
        getContentPane().add(inputArea);
        
        btnNhpFile = new JButton("Nhập file");
        btnNhpFile.setBounds(128, 23, 150, 20);
        btnNhpFile.setEnabled(false);
        getContentPane().add(btnNhpFile);
        
        btnNhpFile.addActionListener(e -> toggleInputOption(false));
        manualInputButton.addActionListener(e -> toggleInputOption(true));
        importButton.addActionListener(e -> showFileChooser());
        connectButton.addActionListener(e -> connectToServer());
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		disconnectFromServer();
        	};
		});
    }

    private void disconnectFromServer() {
        if (socket != null && !socket.isClosed()) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("DISCONNECT"); 
                out.flush();
                
                socket.close();
            } catch (IOException e) {
            	System.out.println("Client đã ngắt kết nối");
            	inputArea.append("Client " + socket.getLocalPort() + " đã ngắt kết nối .\n");
            }
        }
    }
    


    private void toggleInputOption(boolean isManual) {
    	if(isManual) System.out.println("nhập ma trận");
    	else System.out.println("đọc file");
    	isManualInput = isManual;
        
        if (isManualInput) {
        	filePathField.setText("");
            filePathField.setEnabled(false);
            importButton.setEnabled(false);
            inputArea.setVisible(true);
            inputArea.setEditable(true);
            manualInputButton.setEnabled(false);
            btnNhpFile.setEnabled(true);
        } else {
            filePathField.setEnabled(true); 
            importButton.setEnabled(true); 
            inputArea.setText("");
            inputArea.setEditable(false);
            manualInputButton.setEnabled(true);
            btnNhpFile.setEnabled(false);
        }
    }
    
    private void showFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this); 
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); 
            filePathField.setText(selectedFile.getAbsolutePath()); 
        }
    }

    private void connectToServer() {
        if (isConnected) {
        	if(isManualInput)
        	{
        		String[] text = FileRead.generate(inputArea.getText());
//        		for(String line : text) {
//        			System.out.println("Dòng: " + line);
//        		}
        		ShortestPathRouting shortestPathRouting = new ShortestPathRouting(null, text);
        		 shortestPathRouting.showShortestPathRouting();
        	}
        	else {
        		ShortestPathRouting shortestPathRouting = new ShortestPathRouting(filePathField.getText(), null);
                shortestPathRouting.showShortestPathRouting();
        	}
            return; 
        }
    	
//    	if (isConnected) {
//            // Nếu đã kết nối, thông báo người dùng và không làm gì thêm
//            JOptionPane.showMessageDialog(this, "Bạn đã kết nối rồi! Không cần kết nối lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            return;
//        }
        new Thread(() -> {
            try {
                if (!isManualInput && filePathField.getText().isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đường dẫn tệp hoặc chọn 'Nhập thủ công' trước khi kết nối!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    });
                    return;
                }

                if (isManualInput && inputArea.getText().isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập ma trận trước khi kết nối!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    });
                    return;
                }

                String serverIp = "localhost"; 
                int port = 0;
                if(!isConnected) {
	                String portInput = JOptionPane.showInputDialog(this, "Nhập cổng(port): ");
	                if (portInput == null || portInput.trim().isEmpty()) {
	                    SwingUtilities.invokeLater(() -> {
	                        JOptionPane.showMessageDialog(this, "Cổng không hợp lệ! Vui lòng nhập lại.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	                    });
	                    return;
	                }
	                
	                    try {
	                    port = Integer.parseInt(portInput);
	                } catch (NumberFormatException e) {
	                    SwingUtilities.invokeLater(() -> {
	                        JOptionPane.showMessageDialog(this, "Cổng không hợp lệ! Vui lòng nhập một số nguyên.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	                    });
	                    return;
	                }
                }
                socket = new Socket(serverIp, port); 
                System.out.println("connected : " + socket.isConnected());
                if (socket.isConnected()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage = in.readLine();

                    if (serverMessage != null) {
                        int serverPort = Integer.parseInt(serverMessage.split(":")[1].trim());

                        if (serverPort == port) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(this, "Kết nối thành công đến port: " + serverPort);
                                if (!isManualInput)
                                inputArea.append("Client " + socket.getLocalPort() + " đã kết nối đến server.\n");
                                else inputArea.append("\nClient " + socket.getLocalPort() + " đã kết nối đến server.\n");
                                System.out.println(inputArea.getText());
                            });
                            isConnected = true; 
//                            System.out.println("setCurrenClient ");
//                            FileClient.setCurrentClient(FileClient.getCurrentClient());
                            sendRequest(); 
                            Thread.sleep(2000);
                            sendContent(socket); 
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(this, "Port không khớp! Server đang chạy trên port: " + serverPort);
                            });
                        }
                    }
                }
            } catch (SocketException e) {
                System.out.println("Lỗi socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Lỗi I/O: " + e.getMessage());
            } catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println("Lỗi khi đóng socket: " + e.getMessage());
                }
            }
        }).start(); 
    }

    private void sendRequest() {
        try {
            if (socket == null || socket.isClosed()) {
                System.out.println("Không thể gửi yêu cầu: Socket đã đóng.");
                return;
            }

            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("PORT:" + socket.getLocalPort());
            out.println("end");

            if (!isManualInput) {
                String filePath = filePathField.getText().trim();
                if (!filePath.isEmpty()) {
                	System.out.println("Đã gửi đường dẫn: " + filePath);
                    out.println("FILE_PATH:" + filePath); 
                    out.flush();
                } else {
                    out.println("MANUAL_INPUT:" + inputArea.getText());
                    out.println("END");
                    out.flush();
                }
            } else {
            	System.out.println("Đã gửi dữ liệu thủ công");
                out.println("MANUAL_INPUT:" + inputArea.getText());
                out.println("END");
                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi gửi yêu cầu: " + e.getMessage());
        } 
    }


    public void sendContent(Socket socket) {
        if (socket == null || socket.isClosed()) {
            System.out.println("Không thể gửi nội dung: Socket không hợp lệ.");
            return;
        }
        try {
     
            out = new PrintWriter(socket.getOutputStream(), true);
            String content = inputArea.getText(); 
            System.out.println("Content trong send Content " + content);
            out.println("CONTENT:" + content);
        } catch (IOException e) {
            System.out.println("Lỗi khi gửi dữ liệu: " + e.getMessage());
        }
    }

	public void showClient() {
		this.toFront();
		this.setLocationRelativeTo(null);
		this.requestFocus();
		this.setVisible(true);
	}
	
    public static void main(String[] args) {
        FileClient client = new FileClient();
        client.setVisible(true);
    }
}
