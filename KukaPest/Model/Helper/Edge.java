package Model.Helper;

public class Edge {

    private int node_1;
    private int node_2;

    public Edge(int node_1, int node_2){
        this.node_1 = node_1;
        this.node_2 = node_2;
    }

    public int getNode_1() {
        return node_1;
    }

    public int getNode_2() {
        return node_2;
    }

    @Override
    public String toString() {
        return String.format("(%d - %d)", node_1, node_2);
    }
}
