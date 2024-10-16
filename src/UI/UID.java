package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class UID extends JFrame implements ActionListener {

    private JButton btnStart;
    private JButton btnStop;
    private JComboBox<String> cbbClients;
    private JTextArea txtServerArea;
    private JLabel lblPort;
    private JTextField txtPort;
    private JLabel lblAppStatus;
    private JPanel pnlMain;
    private List<JTextArea> listTextArea = new ArrayList<>();
    private Font Arial_Plain_15 = new Font("Arial", Font.PLAIN, 15); // Khai báo phông chữ
    private int screenWidth = 800; // Thay đổi kích thước theo nhu cầu của bạn
    private int screenHeight = 600; // Thay đổi kích thước theo nhu cầu của bạn
    private int port = 11111; // Port giả định, thay đổi theo nhu cầu của bạn

    public UID() {
        GUI();
    }

    private void GUI() {
        int x, y, w, h;
        
        pnlMain = new JPanel();
        pnlMain.setLayout(null); // Sử dụng layout null để kiểm soát chính xác vị trí
        
        btnStart = new JButton("Start");
        btnStart.setFont(Arial_Plain_15);
        btnStart.addActionListener(this);
        w = (int) (getBoundOfText(btnStart.getText(), Arial_Plain_15).getWidth() * 2.1);
        h = (int) getBoundOfText(btnStart.getText(), Arial_Plain_15).getHeight() + 2;
        x = (screenWidth - w) / 2;
        y = (int) (0.1 * screenHeight);
        btnStart.setBounds(x, y, w, h);
        
        btnStop = new JButton("Stop");
        btnStop.addActionListener(this);
        btnStop.setFont(Arial_Plain_15);
        w = (int) (getBoundOfText(btnStop.getText(), Arial_Plain_15).getWidth() * 2.2);
        h = (int) getBoundOfText(btnStop.getText(), Arial_Plain_15).getHeight() + 2;
        x = screenWidth / 3 * 2 + (screenWidth / 3 - w) / 2;
        y = (int) (0.1 * screenHeight);
        btnStop.setBounds(x, y, w, h);
        
        cbbClients = new JComboBox<>();
        cbbClients.addActionListener(this);
        cbbClients.addItem("Server");
        cbbClients.setFont(Arial_Plain_15);
        w = (int) (getBoundOfText("client xx", Arial_Plain_15).getWidth() * 1.5);
        h = (int) getBoundOfText("client xx", Arial_Plain_15).getHeight() + 2;
        x = (int) (0.05 * screenWidth);
        y = (int) (0.1 * screenHeight);
        cbbClients.setBounds(x, y, w, h);
        
        txtServerArea = new JTextArea();
        settingJTextArea(txtServerArea, 0);
        txtServerArea.setVisible(true);
        
        lblPort = new JLabel("port:");
        lblPort.setFont(Arial_Plain_15);
        w = (int) (getBoundOfText(lblPort.getText(), Arial_Plain_15).getWidth() + 2);
        h = (int) getBoundOfText(lblPort.getText(), Arial_Plain_15).getHeight() + 2;
        double percentWidth = w * 1.0 / screenWidth;
        x = (int) ((screenWidth / 2 * (1 - percentWidth / 2) - w) - 0.01 * screenWidth);
        y = (int) (0.03 * screenHeight);
        lblPort.setBounds(x, y, w, h);
        
        txtPort = new JTextField();
        txtPort.setFont(Arial_Plain_15);
        txtPort.setEditable(false);
        txtPort.setText(Integer.toString(port));
        w = (int) (getBoundOfText("11111", Arial_Plain_15).getWidth() + 2);
        h = (int) getBoundOfText("11111", Arial_Plain_15).getHeight() + 2;
        x = (int) (screenWidth / 2 + 0.01 * screenWidth);
        y = (int) (0.03 * screenHeight);
        txtPort.setBounds(x, y, w, h);
        
        lblAppStatus = new JLabel("running...");
        lblAppStatus.setFont(Arial_Plain_15);
        w = (int) (getBoundOfText(lblAppStatus.getText(), Arial_Plain_15).getWidth() * 2.2);
        h = (int) getBoundOfText(lblAppStatus.getText(), Arial_Plain_15).getHeight() + 2;
        x = (int) (0.02 * screenWidth);
        y = (int) (0.885 * screenHeight);
        lblAppStatus.setBounds(x, y, w, h);
        
        // Thêm các thành phần vào pnlMain
        pnlMain.add(lblPort);
        pnlMain.add(txtPort);
        pnlMain.add(btnStart);
        pnlMain.add(btnStop);
        pnlMain.add(cbbClients);
        pnlMain.add(lblAppStatus);
        
        // Thêm pnlMain vào JFrame
        this.add(pnlMain);
        
        // Cấu hình JFrame
        this.setSize(new Dimension(screenWidth, screenHeight));
        this.setTitle("Server");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Đặt JFrame ở giữa màn hình
        this.setVisible(true);
    }

    private Rectangle2D getBoundOfText(String text, Font font) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return font.getStringBounds(text, frc);
    }

    public void settingJTextArea(JTextArea jTextArea, int index) {
        if (listTextArea.size() == index)
            listTextArea.add(jTextArea);
        else if (listTextArea.size() > index) {
            listTextArea.add(index, jTextArea);
        }
        jTextArea.setFont(Arial_Plain_15);
        int w = (int) (screenWidth * 0.95);
        int h = (int) (screenHeight * 0.73);
        int x = (int) (screenWidth * 0.01);
        int y = (int) (screenHeight * 0.15);
        jTextArea.setBounds(x, y, w, h);
        jTextArea.setEditable(false);
        jTextArea.setVisible(false);
        Border tempBorder = BorderFactory.createLineBorder(Color.black);
        jTextArea.setBorder(tempBorder);
        pnlMain.add(jTextArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Xử lý sự kiện khi nút được nhấn
    }

//    public static void main(String[] args) {
//        // Khởi chạy ứng dụng Swing
//        javax.swing.SwingUtilities.invokeLater(UID::new);
//    }

}
