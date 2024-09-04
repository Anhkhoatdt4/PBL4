package service;

import java.util.List;

public class Vert implements Comparable<Vert>{
	
	private boolean visited = false;  
	private String name ;
	private List<Edge> list; 
	private Double dist = Double.MAX_VALUE;
	private Vert v ; 
	
	public Vert() {
	}
	
	
	public Vert(boolean visited, String name, List<Edge> list, Double dist, Vert v) {
		super();
		this.visited = visited;
		this.name = name;
		this.list = list;
		this.dist = dist;
		this.v = v;
	}


	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Edge> getList() {
		return list;
	}


	public void setList(List<Edge> list) {
		this.list = list;
	}


	public Double getDist() {
		return dist;
	}


	public void setDist(Double dist) {
		this.dist = dist;
	}


	public Vert getV() {
		return v;
	}


	public void setV(Vert v) {
		this.v = v;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	@Override
	public int compareTo(Vert o) {
		return Double.compare(this.dist, o.getDist());
	}

}
