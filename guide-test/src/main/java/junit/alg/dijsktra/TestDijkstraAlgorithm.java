package junit.alg.dijsktra;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class TestDijkstraAlgorithm {

    private List<Vertex> nodes;
    private List<Edge> edges;

    @Test
    public void testExcute() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        char x='A';
        for (int i = 0; i <7 ; i++) {
            Vertex location = new Vertex("Node_" + (char)(i+x), "Node_" + (char)(i+x));
            nodes.add(location);
        }
        for (Vertex vertex : nodes) {
            System.out.println(vertex);
        }

        addLane("Edge_1", 0, 1, 9);
        addLane("Edge_2", 0, 2, 2);

        addLane("Edge_3", 1, 2,6);
        addLane("Edge_4", 1, 3,3);
        addLane("Edge_5", 1, 4,1);

        //C
        addLane("Edge_6", 2, 3, 2);
        addLane("Edge_7", 2, 5, 9);

        //D
        addLane("Edge_8", 3, 4, 5);
        addLane("Edge_9", 3, 5, 6);

        //E
        addLane("Edge_10", 4, 6, 7);

        //F
        addLane("Edge_6", 5, 6, 4);

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(6));

        assertNotNull(path);
        assertTrue(path.size() > 0);

        log.info("haha .....      ");
        for (Vertex vertex : path) {
            System.out.println(vertex);
        }

    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo,
                         int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}