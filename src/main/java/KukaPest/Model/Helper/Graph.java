package KukaPest.Model.Helper;


import KukaPest.Model.Map.ResidentialZone;
import KukaPest.Model.Map.Workplace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph implements java.io.Serializable{

    private int nodes;

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes_array = new ArrayList<>();

    private LinkedList<Integer> adj[]; // Adjacency Lists


    public Graph(int nodes){
        this.nodes = nodes;
        adj = new LinkedList[nodes];
        for (int i = 0; i < nodes; ++i)
            adj[i] = new LinkedList();


    }

    /**
     * This method creates a node for the graph, and adds it to the adjacency list
     * @param id Node id
     * @param isRoad True/false depending on whether node is for a road
     * @param coordinates Coordinates of given node
     */
    public void addNode(int id, boolean isRoad, Coordinates coordinates){

        nodes_array.add(new Node(id,isRoad,coordinates));
        if(id > nodes){
            nodes = nodes + 1;
        }

        adj[id] = new LinkedList();
    }

    /**
     * This method creates an Edge between two given nodes.
     * @param node1 Given node #1
     * @param node2 Given node #2
     */
    public void addEdge(int node1, int node2){
        for(Edge edge: edges){
            if(edge.getNode_1() == node1 && edge.getNode_2() == node2){
                return;
            }
        }
        edges.add(new Edge(node1,node2));
        edges.add(new Edge(node2,node1));
        adj[node1].add(node2);
        adj[node2].add(node1);
    }

    public int getNodeID(Coordinates coordinates){
        for(Node node: nodes_array){
            if(node.getCoordinates().getHeight() == coordinates.getHeight() && node.getCoordinates().getWidth() == coordinates.getWidth()){
                return node.getID();
            }
        }
        return -1;
    }

    public boolean getNodeRoad(int id){
        for(Node node_1: nodes_array){
            if(node_1.getID() == id){
                return node_1.Isroad();
            }
        }
        return false;

    }

    /**
     * This method executes a Breadth First Search on the Graph, searching from given point to numberBuilding
     * @param s The index of starting node
     * @param numberBuilding The building searched
     * @return True/false, depending on whether building was found from the starting point
     */
    public boolean BFS(int s, int numberBuilding) {

        boolean visited[] = new boolean[nodes];
        // System.out.println("Node száma: " + nodes);

        LinkedList<Integer> queue
                = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {

            s = queue.poll();
            // System.out.print(s + " ");
            if(getNodeRoad(s) == false){
                continue;
            }

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    //System.out.println(n + " " + visited[n] + " ");
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        // System.out.println("\n");
        // System.out.println(numberBuilding);

        int visit = 0;
        for(int i = 0; i < visited.length; i++){
            if(visited[i] == true && (nodes_array.get(i).Isroad() == false) ){
                visit = visit + 1;
            }
        }
        if(visit == numberBuilding){
            // System.out.println("Rendben");
            return true;

        }
        return false;
    }

    public boolean BFS_movein(int s, int numberBuilding, Coordinates coords) {

        boolean visited[] = new boolean[nodes];
        // System.out.println("Node száma: " + nodes);

        LinkedList<Integer> queue
                = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {

            s = queue.poll();
            // System.out.print(s + " ");
            if(getNodeRoad(s) == false){
                continue;
            }

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    //System.out.println(n + " " + visited[n] + " ");
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        // System.out.println("\n");
        // System.out.println(numberBuilding);

        int visit = 0;

        for(int i = 0; i < visited.length; i++) {

            if (visited[i] == true) {
                if (nodes_array.get(i).getCoordinates().getHeight() == coords.getHeight()
                        && nodes_array.get(i).getCoordinates().getWidth() == coords.getWidth()) {
                    visit = visit + 1;
                    // System.out.println("OK");
                }
            }
        }
        if(visit == 1){
            return true;
        }

        return false;
    }


    @Override
    public String toString() {
        String str = "Nodes:\n";
        for (Node n: nodes_array) {
            str += n + "\n";
        }
        str += "Edges:\n";
        for (Edge e: edges) {
            str += e + "\n";
        }
        return str;
    }

}
