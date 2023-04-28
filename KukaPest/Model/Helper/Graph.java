package Model.Helper;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

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

    public void addNode(int id, boolean isRoad, Coordinates coordinates){

        nodes_array.add(new Node(id,isRoad,coordinates));
        nodes = nodes + 1;
        adj[id] = new LinkedList();
    }

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

    public boolean BFS(int s, int numberBuilding) {

        boolean visited[] = new boolean[nodes];

        LinkedList<Integer> queue
                = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {

            s = queue.poll();
            System.out.print(s + " ");
            if(getNodeRoad(s) == false){
                continue;
            }

            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    System.out.println(n + " " + visited[n] + " ");
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        System.out.println("\n");
        System.out.println(numberBuilding);

        int visit = 0;
        for(int i = 0; i < visited.length; i++){
            if(visited[i] == true && (nodes_array.get(i).Isroad() == false) ){
                visit = visit + 1;
            }
        }
        if(visit == numberBuilding){
            System.out.println("Rendben");
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
