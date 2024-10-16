package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import client.FileClient;

import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.List;

import input.FileRead;
import service.DijkstraBuild;
import service.Edge;
import service.Vert;

public class ShortestPathRouting extends JFrame {
    private JTextField sourceField;
    private JTextField destinationField;
    private String filePath ;
 
    public ShortestPathRouting(String filePath , String[] manulInput) {
        // Cấu hình JFrame
        setTitle("Shortest Path Routing");
        setSize(877, 551);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        // Tạo JPanel cho "GroupBox" với TitledBorder
        JPanel groupBox = new JPanel();
        groupBox.setBounds(10, 10, 265, 200); 
        getContentPane().add(groupBox);

        // Tạo TitledBorder với font tùy chỉnh (không BOLD)
        TitledBorder titledBorder = new TitledBorder("Chọn trạm trên mạng");
        titledBorder.setTitleFont(new Font("Arial", Font.PLAIN, 16)); 
        titledBorder.setTitleColor(Color.BLACK); 
        
        // Áp dụng TitledBorder cho JPanel
        groupBox.setBorder(titledBorder);
        groupBox.setLayout(null);
        
        JLabel lblSource = new JLabel("Nguồn");
        lblSource.setForeground(Color.BLUE);
        lblSource.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblSource.setBounds(20, 30, 68, 26);
        groupBox.add(lblSource);
        
        JLabel lblDestination = new JLabel("Đích");
        lblDestination.setForeground(Color.BLUE);
        lblDestination.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblDestination.setBounds(20, 80, 68, 26);
        groupBox.add(lblDestination);
        
        sourceField = new JTextField();
        sourceField.setBounds(100, 30, 140, 35);
        sourceField.setColumns(10);
        sourceField.setBorder(new LineBorder(Color.BLACK, 2));
        groupBox.add(sourceField);
        
        destinationField = new JTextField();
        destinationField.setColumns(10);
        destinationField.setBounds(100, 80, 140, 35);
        destinationField.setBorder(new LineBorder(Color.BLACK, 2));
        groupBox.add(destinationField);
        
        JButton btnSelect = new JButton("Chọn");
        btnSelect.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnSelect.setBounds(90, 130, 85, 35);
        btnSelect.setBorder(new LineBorder(Color.BLACK, 1));
        groupBox.add(btnSelect);
        
        // Tạo TitledBorder với font tùy chỉnh (không BOLD)
        TitledBorder controlBorder = new TitledBorder("Điều khiển");
        controlBorder.setTitleFont(new Font("Arial", Font.PLAIN, 16)); // Thay đổi kích thước chữ và không BOLD
        controlBorder.setTitleColor(Color.BLACK); // Thay đổi màu chữ nếu cần
        
        // Đọc đồ thị từ file và vẽ lên JPanel
        // List<Vert> vertices = FileRead.readGraphFromFile("D:\\PBL\\Input.txt"); 
        
        List<Vert> vertices;
        if (filePath != null) {
            // Đọc đồ thị từ file
            vertices = FileRead.readGraphFromFile(filePath);
        } else {
            // Xử lý dữ liệu từ manualInput
            vertices = FileRead.readGraphFromArray(manulInput);
            System.out.println(vertices);
        }
        
        GraphPanel graphPanel = new GraphPanel(vertices);
        graphPanel.setBounds(300, -181, 514, 576); // Xác định vị trí và kích thước của JPanel
        graphPanel.setPreferredSize(new Dimension(470, 431));
        getContentPane().add(graphPanel);
        graphPanel.setLayout(null);
        
      
        btnSelect.addActionListener(e -> {
        	resetGraph(vertices);
            String sourceName = sourceField.getText();
            String targetName = destinationField.getText();

            Vert sourceVert = null, targetVert = null;
            for (Vert vert : vertices) {
                if (vert.getName().equals(sourceName)) {
                    sourceVert = vert;
                }
                if (vert.getName().equals(targetName)) {
                    targetVert = vert;
                }
            }

            if (sourceVert != null && targetVert != null) {
                DijkstraBuild.ShortestDistance(sourceVert);
                List<Vert> shortestPath = DijkstraBuild.getShortestVertTarget(targetVert);
                double shortestPathDistance = DijkstraBuild.getDistance(targetVert); 

                graphPanel.setSource(sourceVert);
                graphPanel.setDestination(targetVert);
                graphPanel.setShortestPath(shortestPath);
                graphPanel.setShortestPathDistance((int)shortestPathDistance);
                
                JPanel panel = new JPanel();
                panel.setBounds(10, 220, 265, 285);
                getContentPane().add(panel);
                panel.setLayout(null);
                
                JLabel lblNewLabel = new JLabel("Bảng chỉ đường");
                lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                lblNewLabel.setBounds(70, 10, 149, 32);
                panel.add(lblNewLabel);
                
                
                JPanel pathPanel = new JPanel();
                pathPanel.setBounds(70, 52 , 162 ,228);
                panel.add(pathPanel);
                pathPanel.setLayout(new BoxLayout(pathPanel, BoxLayout.Y_AXIS));
               
                
                for ( Vert target : vertices) {
                	if ( ! target.equals(sourceVert)) {
                		List<Vert> shortestRemaining = DijkstraBuild.getShortestVertTarget(target);
                		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                		lblNewLabel.setForeground(new Color(128, 0, 128)); 
                		StringBuilder text = new StringBuilder("<html>");
                		if ( ! shortestRemaining.isEmpty()) {
                		 text.append (target.getName() + " : ");
                		
                			for ( int i  = shortestRemaining.size() -1  ; i >= 0 ; i --) {
                				
                				 text.append(shortestRemaining.get(i).getName());
                				
                				if ( i != 0 ) {
                					text.append(" -> ");
                					
                				}
                			}
                			 text.append("<br>");
                			 text.append("</html>");
                			 JLabel pathLabel = new JLabel(text.toString());
                			 pathLabel.setFont(new Font("Times New Roman", Font.BOLD, 17));
                			 pathLabel.setForeground(Color.BLUE);
                	         pathPanel.add(pathLabel);
                	         
                	      
                	         JPanel controlPanel = new JPanel();
                	         controlPanel.setBounds(596, 405, 200, 100);
                	         getContentPane().add(controlPanel);
                	         
                	         // Áp dụng TitledBorder cho JPanel
                	         controlPanel.setBorder(controlBorder);
                	         controlPanel.setLayout(null);
                	         
                	         JButton btnEnd = new JButton("Kết thúc");
                	         btnEnd.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                	         btnEnd.setBounds(50, 30, 100, 35); // Vị trí và kích thước của nút
                	         btnEnd.setBorder(new LineBorder(Color.BLACK, 1));
                	         controlPanel.add(btnEnd);
                	              			
                			 btnEnd.addActionListener(event -> {
                		     this.setVisible(false);
               				
              			 });
                		
                		}
                	}
                		
                }
               
               
                graphPanel.repaint();
            } else {
                System.out.println("Nguồn hoặc đích không hợp lệ.");
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void showShortestPathRouting() {
    	this.toFront();
    	this.requestFocus();
    	this.setVisible(true);
    	this.setLocationRelativeTo(null);
    }
   
  
    private void resetGraph(List<Vert> vertices) {
        for (Vert v : vertices) {
            v.setDist(Double.MAX_VALUE); 
            v.setVisited(false);
            v.setV(null);
        }
    }
    
}
