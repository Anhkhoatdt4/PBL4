package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTML.Tag;

public class DijkstraMatrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số đỉnh của đồ thị: ");
        int vertices = scanner.nextInt();
        scanner.nextLine();

        String[] vertexNames = new String[vertices];
        List<Vert> vertList = new ArrayList<>();

        System.out.println("Nhập tên các đỉnh:");
        for (int i = 0; i < vertices; i++) {
            System.out.print("Đỉnh " + (i + 1) + ": ");
            vertexNames[i] = scanner.nextLine();
            Vert newVert = new Vert();
            newVert.setName(vertexNames[i]);
            vertList.add(newVert);
        }

        double[][] graph = new double[vertices][vertices];
        
        System.out.println("Nhập ma trận trọng số:");
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                System.out.print("Nhập trọng số cho cạnh từ đỉnh " + vertexNames[i] + " tới đỉnh " + vertexNames[j] + ": ");
                double weight = scanner.nextDouble();
                while (weight < 0) {
                    System.out.println("Trọng số không thể âm. Vui lòng nhập lại.");
                    weight = scanner.nextDouble();
                }
                graph[i][j] = weight;
                graph[j][i] = weight;
            }
        }

        // Tạo danh sách các cạnh và thêm vào mỗi đỉnh
        for (int i = 0; i < vertices; i++) {
            Vert vert = vertList.get(i);
            List<Edge> edges = new ArrayList<>();
            for (int j = 0; j < vertices; j++) {
                if (graph[i][j] > 0) {
                    Edge edge = new Edge(graph[i][j], vert, vertList.get(j));
                    edges.add(edge);
                }
            }
            vert.setList(edges);
        }

        Vert sourceVert = null, targetVert = null;
        scanner.nextLine();
        while (sourceVert == null || targetVert == null) {
            System.out.print("Nhập tên đỉnh nguồn: ");
            String sourceName = scanner.nextLine();
            System.out.print("Nhập tên đỉnh đích: ");
            String targetName = scanner.nextLine();

            // Tìm đỉnh nguồn và đỉnh đích
            for (Vert vert : vertList) {
                if (vert.getName().equals(sourceName)) {
                    sourceVert = vert;
                }
                if (vert.getName().equals(targetName)) {
                    targetVert = vert;
                }
            }

            if (sourceVert == null || targetVert == null) {
                System.out.println("Tên đỉnh không hợp lệ. Vui lòng nhập lại.");
            }
        }

        DijkstraBuild.ShortestDistance(sourceVert);
        List<Vert> shortestPath = DijkstraBuild.getShortestVertTarget(targetVert);

        System.out.println("Khoảng cách ngắn nhất từ " + sourceVert.getName() + " đến " + targetVert.getName() + " là: " + targetVert.getDist());
        System.out.print("Đường đi ngắn nhất: ");
        for (int i = shortestPath.size() - 1; i >= 0; i--) {
            System.out.print(shortestPath.get(i).getName());
            if (i != 0) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Bảng chỉ đường: ");
        
        for ( Vert target : vertList )
        {
        	if ( !target.equals(sourceVert)) {
        		List<Vert> shortestPathVariable = DijkstraBuild.getShortestVertTarget(target);
        		
        		if ( !shortestPathVariable.isEmpty()) {
        			System.out.print( target.getName() + " : ");
        			
        			for ( int i = shortestPathVariable.size() - 1 ; i >= 0 ; i --) {
        				System.out.print(shortestPathVariable.get(i).getName());
        				if ( i != 0 ) {
        					System.out.print(" -> ");
        				}
        			}
        			System.out.println();
        		}
        		
        	}
        	
        }
        
    }
}
