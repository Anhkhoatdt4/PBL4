package service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraBuild {
	public static void ShortestDistance(Vert sourceV )
	{
		sourceV.setDist(0.0);
		PriorityQueue<Vert> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(sourceV);
		sourceV.setVisited(true);
		
		while (!priorityQueue.isEmpty())
		{
			Vert actualVert = priorityQueue.poll();
			for (Edge edge : actualVert.getList())
			{
				Vert v = edge.getTargetVert();
				if ( ! v.isVisited())
				{
					double newDistance = actualVert.getDist() + edge.getWeight();
					// System.out.println("Độ dài " + actualVert.getDist());
					// System.out.println("Độ dài " + edge.getWeight());
					if ( newDistance < v.getDist())
					{
						priorityQueue.remove(v);
						v.setDist(newDistance);
						v.setV(actualVert);
						priorityQueue.add(v);
					}
				}
				actualVert.setVisited(true);
			}
		}
	}
	
	public static List<Vert> getShortestVertTarget(Vert targetVert)
	{
		List<Vert> list = new ArrayList<>();
		for ( Vert vert = targetVert ; vert != null ; vert = vert.getV()  )
		{
			list.add(vert);
		}
		return list ;
	}
}	
