package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import service.Edge;
import service.Vert;

public class FileRead {
	
	public FileRead() {
		
	}
	

	public static List<Vert> readGraphFromFile(String filePath)
	{
		List<Vert> vertList = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			int vertices = Integer.parseInt(reader.readLine().trim());
			String[] vertexNames = reader.readLine().trim().split(" ");
					
			for (String name : vertexNames)
			{
				Vert newVert = new Vert();
				newVert.setName(name);
				vertList.add(newVert);
			}
			
			double [][]graph = new double[vertices][vertices];
			
			for ( int i = 0 ; i < vertices ; i ++) {
				String[] weights = reader.readLine().trim().split(" ");
				for ( int j = 0 ; j < vertices ; j ++)
				{
					graph[i][j] = Double.parseDouble(weights[j]);
				}
			}
			
			for ( int i = 0 ; i < vertices ; i ++)
			{
				Vert vert = vertList.get(i);
				List<Edge> edges = new ArrayList<>();
				for ( int j = 0 ; j < vertices ; j ++)
				{
					if ( graph[i][j] > 0) {
						Edge edge = new Edge(graph[i][j], vert, vertList.get(j));
                        edges.add(edge);
					}
				}
				 vert.setList(edges);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi doc file ");
		}
		
		return vertList;
	}
	
	public static List<Vert> readGraphFromArray(String[] mannualArray){
		List<Vert> vertices = new ArrayList<>();
		try {
			int numVertices = Integer.parseInt(mannualArray[0].trim());
			String[] verticesName=  mannualArray[1].trim().split(" ");
			
			for (String name : verticesName) {
				Vert v = new Vert();
				v.setName(name);
				vertices.add(v);
			}
			
			double [][] graph = new double[numVertices][numVertices];
			for ( int i = 0 ; i < numVertices ; i ++ ) {
				String[] weights = mannualArray[i + 2 ].trim().split(" ");
				for ( int j = 0 ; j < numVertices ; j ++) {
					graph[i][j] = Double.parseDouble(weights[j]);
				}
			}
			
			for ( int i = 0 ; i < numVertices ; i ++ ) {
				Vert v = vertices.get(i);
				List<Edge> edges = new ArrayList<>();
				for ( int j = 0 ; j < numVertices ; j ++ ) {
					if ( graph[i][j] > 0 ) {
						Edge edge = new Edge(graph[i][j] , v , vertices.get(j)); 
						edges.add(edge);
					}
				}
				v.setList(edges);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi đọc ma trận");
		}
		
		
		
		return vertices;
	}
	
//	  public static void main(String[] args) {
//	        // Kiểm tra phương thức đọc từ mảng chuỗi
//	        String[] manualArray = {
//	            "4",        // Số lượng đỉnh
//	            "A B C D",  // Tên các đỉnh
//	            "0 1 2 0",  // Trọng số giữa A và các đỉnh khác
//	            "1 0 0 3",  // Trọng số giữa B và các đỉnh khác
//	            "2 0 0 4",  // Trọng số giữa C và các đỉnh khác
//	            "0 3 4 0"   // Trọng số giữa D và các đỉnh khác
//	        };
//	        
//	        List<Vert> verticesFromArray = FileRead.readGraphFromArray(manualArray);
//	        System.out.println("Đọc đồ thị từ mảng chuỗi:");
//	        printVertices(verticesFromArray);
//	    }
//	  
//	  private static void printVertices(List<Vert> vertices) {
//	        for (Vert vert : vertices) {
//	            System.out.print("Vert: " + vert.getName() + " -> ");
//	            for (Edge edge : vert.getList()) {
//	                System.out.print("Edge to "+  edge.getStartVert().getName().toString() + " (Weight: " + edge.getWeight() + "), ");
//	            }
//	            System.out.println();
//	        }
//	    }
}
