package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import service.Edge;
import service.Vert;

public class FileRead {
	
	public FileRead() {
	}

	public static List<Vert> readGraphFromFile(String filePath)
	{
		List<Vert> vertList = new ArrayList<>();
		System.out.println("file Path : " + filePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			int vertices = Integer.parseInt(reader.readLine().trim());
			String[] vertexNames = reader.readLine().trim().split("\\s+");
				
			for (String name : vertexNames)
			{
				Vert newVert = new Vert();
				newVert.setName(name);
				vertList.add(newVert);
			}
			System.out.println();
			
			double [][]graph = new double[vertices][vertices];
			
			for ( int i = 0 ; i < vertices ; i ++) {
				String[] weights = reader.readLine().trim().split("\\s+");
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
			// e.printStackTrace();
			System.out.println("Loi doc file ");
		}
		
		return vertList;
	}
	
	
	
	public static List<Vert> readGraphFromArray(String[] mannualArray1) {
		for (String line : mannualArray1) {
	        System.out.println("Dòng: " + line);
	        if (line.endsWith("\n")) {
	            System.out.println("Dòng này có dấu xuống dòng.");
	        } else {
	            System.out.println("Dòng này không có dấu xuống dòng.");
	        }
		}
	    System.out.println("read matrix");
	    StringBuilder sbBuilder = new StringBuilder();
	    for(String line : mannualArray1) {
	    	if (!line.endsWith("\n")) {
	    		System.out.println("không có dấu xuống dòng");
                sbBuilder.append(line).append("\n");
            } else {
                sbBuilder.append(line);
            }
	    }
	    System.out.println("sbBuider "  + sbBuilder) ;
	    String[] mannualArray = sbBuilder.toString().split("\r?\n");
	    
	    List<Vert> vertices = new ArrayList<>();
	    for(int i = 0; i < mannualArray.length; i++) {
	    	System.out.println("Dong " + i + " : "  + mannualArray[i]);
	    }
	    try {

	        StringBuilder cleanedData = new StringBuilder();
	        for (String line : mannualArray) {
	            if (line.matches(".*\\d.*") && !line.matches(".*[a-zA-Z].*")) {
	                System.out.println("k bỏ qua: " + line);
	                cleanedData.append(line).append("\n");
	            } else {
	                System.out.println("line bị bỏ qua: " + line);
	            }
	        }

	        System.out.println("cleanedData: " + cleanedData);
	        String[] cleanedArray = cleanedData.toString().split("\n");

	        String firstLine = cleanedArray[0].trim();
	        System.out.println("firstline: " + firstLine);
	        int numVertices = Integer.parseInt(firstLine);  
	        System.out.println("Số lượng đỉnh: " + numVertices);

	        if (numVertices <= 0) {
	            throw new IllegalArgumentException("Số lượng đỉnh phải lớn hơn 0");
	        }

	        String[] verticesName = cleanedArray[1].split("\\s+");
	        for (String name : verticesName) {
	            Vert v = new Vert();
	            v.setName(name);
	            vertices.add(v);
	        }


	        double[][] graph = new double[numVertices][numVertices];
	        for (int i = 0; i < numVertices; i++) {
	            String[] weights = cleanedArray[i + 2].split("\\s+");
	            for (int j = 0; j < numVertices; j++) {
	                graph[i][j] = Double.parseDouble(weights[j]);  // Chuyển đổi các giá trị trọng số
	            }
	        }

	        // Gán các cạnh cho các đỉnh
	        for (int i = 0; i < numVertices; i++) {
	            Vert v = vertices.get(i);
	            List<Edge> edges = new ArrayList<>();
	            for (int j = 0; j < numVertices; j++) {
	                if (graph[i][j] > 0) {
	                    Edge edge = new Edge(graph[i][j], v, vertices.get(j));
	                    edges.add(edge);
	                }
	            }
	            v.setList(edges);
	        }

	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        System.out.println("Lỗi đọc ma trận: Định dạng số không hợp lệ");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Lỗi đọc ma trận");
	    }

	    return vertices;
	}
	
	public static String[] generate(String text) {
	    String[] mannualArray = text.split("\r?\n");
	    StringBuilder sb = new StringBuilder();

	    for (String line : mannualArray) {
	        if (!line.matches(".*[a-zA-Z]+.*")) {
	            sb.append(line).append("\n");
	        }
	    }
	    String[] filteredArray = sb.toString().split("\r?\n");
	    for (String line : filteredArray) {
	        System.out.println("Dòng: " + line);
	    }

	    return filteredArray;
	}


	public static void main(String[] args) {
//	    String[] input = {
//	
//	         "8\r\n"
//	        + "1 2 3 4 5 6 7 8\r\n"
//	        + "0 2 0 6 0 0 0 0\r\n"
//	        + "2 0 3 8 5 0 0 0\r\n"
//	        + "0 3 0 0 7 4 0 0\r\n"
//	        + "6 8 0 0 9 0 0 0\r\n"
//	        + "0 5 7 9 0 6 2 0\r\n"
//	        + "0 0 4 0 6 0 1 7\r\n"
//	        + "0 0 0 0 2 1 0 3\r\n"
//	        + "0 0 0 0 0 7 3 0"
//	    };
//
//	    // Gọi phương thức để đọc đồ thị
//	    List<Vert> vertices = readGraphFromArray(input);
//
//	    // Hiển thị thông tin các đỉnh và ma trận trọng số
//	    for (Vert v : vertices) {
//	        System.out.println("Đỉnh: " + v.getName());
//	        for (Edge e : v.getList()) {
//	            System.out.println("Cạnh từ " + v.getName() + " tới " + e.getTargetVert().getName() + " có trọng số: " + e.getWeight());
//	        }
//	    }
		
		String input = "Client 54023 đã kết nối đến server.\r\n"
	            + "8\r\n"
	            + "1 2 3 4 5 6 7 8\r\n"
	            + "0 2 0 6 0 0 0 0\r\n"
	            + "2 0 3 8 5 0 0 0\r\n"
	            + "0 3 0 0 7 4 0 0\r\n"
	            + "6 8 0 0 9 0 0 0\r\n"
	            + "0 5 7 9 0 6 2 0\r\n"
	            + "0 0 4 0 6 0 1 7\r\n"
	            + "0 0 0 0 2 1 0 3\r\n"
	            + "0 0 0 0 0 7 3 0";
		
		FileRead.generate(input);
	}


}
