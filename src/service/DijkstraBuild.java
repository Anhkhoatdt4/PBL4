package service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraBuild {

    public static void ShortestDistance(Vert sourceV) {
        sourceV.setDist(0.0);
        PriorityQueue<Vert> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceV);
        sourceV.setVisited(true);

        while (!priorityQueue.isEmpty()) {
            Vert actualVert = priorityQueue.poll();
            for (Edge edge : actualVert.getList()) { // duyet qua tat ca cac canh cua dinh hien tai
                Vert v = edge.getTargetVert();
                if (!v.isVisited()) {
                    double newDistance = actualVert.getDist() + edge.getWeight();
                    if (newDistance < v.getDist()) {
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

    public static List<Vert> getShortestVertTarget(Vert targetVert) {
        List<Vert> list = new ArrayList<>();
        for (Vert vert = targetVert; vert != null; vert = vert.getV()) {
            list.add(vert);
        }
        return list;
    }

    public static double getDistance(Vert targetVert) {
        // Ensure the target vertex has its distance computed
        return targetVert.getDist();
    }
}
