package service;

public class Edge {
	
	private Vert startVert ; 
	private Vert targetVert ; 
	private Double weight ;
	
	public Edge() {
	}

	public Edge(Double i, Vert vert, Vert vert2) {
		this.weight = i ; 
		this.startVert = vert;
		this.targetVert = vert2;
	}

	public Vert getStartVert() {
		return startVert;
	}

	public void setStartVert(Vert startVert) {
		this.startVert = startVert;
	}

	public Vert getTargetVert() {
		return targetVert;
	}

	public void setTargetVert(Vert targetVert) {
		this.targetVert = targetVert;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	
	
}
