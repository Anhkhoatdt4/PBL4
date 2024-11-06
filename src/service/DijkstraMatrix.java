package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTML.Tag;

import input.FileRead;


public class DijkstraMatrix {
    public static void main(String[] args) {
    	
    	String filePath = "C:\\Users\\nguye\\Documents\\Data.txt";; 

    	
        Scanner scanner = new Scanner(System.in);
        List<Vert> vertList = FileRead.readGraphFromFile(filePath);
        
        Vert sourceVert = null, targetVert = null;
        while (sourceVert == null || targetVert == null) {
            System.out.print("Nhập tên đỉnh nguồn: ");
            String sourceName = scanner.nextLine();
            System.out.print("Nhập tên đỉnh đích: ");
            String targetName = scanner.nextLine();

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
        
       
        
    }
}
