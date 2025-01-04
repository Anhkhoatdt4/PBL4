package UI;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.List;
import java.awt.Font;
import service.Vert;
import service.Edge;

public class GraphPanel extends JPanel {
    private List<Vert> vertices;
    private final int vertexRadius = 20;
    private final int radius = 100;
    private final int centerX = 300;
    private final int centerY = 300;
    
    private List<Vert> shortestPath;
    private Vert source;
    private Vert destination;
    private int shortestPathDistance;

    public GraphPanel(List<Vert> vertices) {
        this.vertices = vertices;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        float strokeWidth = 3.0f;
        g2d.setStroke(new BasicStroke(strokeWidth));

        int numVertices = vertices.size();
        double angleStep = 2 * Math.PI / numVertices;
        double startAngle = Math.PI;

        int[] xPoints = new int[numVertices];
        int[] yPoints = new int[numVertices];

        Color darkGreen = new Color(0, 148, 0);
        Color darkBlue = new Color(0, 0, 139);
        Color shortestPathColor = Color.RED;
 
        
        int infoAreaHeight = 130; 
        int graphPanelHeight = getHeight() - infoAreaHeight; 

        for (int i = 0; i < numVertices; i++) {
            Vert v = vertices.get(i);
            double angle = startAngle + i * angleStep;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));

            xPoints[i] = x;
            yPoints[i] = y;
            
            if ( v.equals(source) || v.equals(destination) ) {
            	g2d.setColor(darkGreen);
            }
            else g2d.setColor(Color.yellow);
            
            g2d.fillOval(x - vertexRadius, y - vertexRadius, vertexRadius * 2, vertexRadius * 2);
            g2d.setColor(new Color(0, 0, 180));
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.drawString(v.getName(), x - vertexRadius / 2, y + vertexRadius / 4);
        }

        for (int i = 0; i < numVertices; i++) {
            Vert v = vertices.get(i);
            for (Edge e : v.getList()) {
                Vert target = e.getTargetVert();
                int targetIndex = vertices.indexOf(target);

                boolean isShortestPath = shortestPath != null &&
                                         shortestPath.contains(v) && shortestPath.contains(target);

                g2d.setColor(isShortestPath ? darkBlue : shortestPathColor);
                g2d.draw(new Line2D.Double(xPoints[i], yPoints[i], xPoints[targetIndex], yPoints[targetIndex]));
                
              
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
        for (int i = 0; i < numVertices; i++) {
            Vert v = vertices.get(i);
            for (Edge e : v.getList()) {
                Vert target = e.getTargetVert();
                int targetIndex = vertices.indexOf(target);

                int midX = (xPoints[i] + xPoints[targetIndex]) / 2;
                int midY = (yPoints[i] + yPoints[targetIndex]) / 2;
                g2d.drawString(String.valueOf(e.getWeight()), midX, midY);
            }
        }

        if (shortestPath != null && !shortestPath.isEmpty()) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            
            int xOffset = 200;
            int yOffset = graphPanelHeight;

            g2d.drawString("Đường đi ngắn nhất : ", xOffset, yOffset);
            StringBuilder path = new StringBuilder();
           
            Collections.reverse(shortestPath);
            for (Vert vert : shortestPath) {
                path.append(vert.getName()).append("   ");
            }
            g2d.drawString(path.toString(), xOffset + 150, yOffset);
            g2d.drawString("Khoảng cách :         " + shortestPathDistance, xOffset, yOffset + 30);
        }
    }

    public void setShortestPath(List<Vert> shortestPath) {
        this.shortestPath = shortestPath;
        repaint(); 
    }

    public void setSource(Vert source) {
        this.source = source;
    }

    public void setDestination(Vert destination) {
        this.destination = destination;
    }

    public void setShortestPathDistance(int distance) {
        this.shortestPathDistance = distance;
        repaint(); 
    }
}
