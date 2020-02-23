package sch.frog.clrs;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Breadth First Search 广度优先搜索
 */
public class BFS {

    public static void main(String[] args){
        GraphByAdjacencyList<String> graph = init();
        String sourceVertexName = "A";
        GraphByAdjacencyList.Vertex<String> source = new GraphByAdjacencyList.Vertex<>();
        for (GraphByAdjacencyList.Vertex<String> vertex : graph.vertices) {
            if(sourceVertexName.equals(vertex.data)){
                source = vertex;
                break;
            }
        }

        breadthFirstSearch(source);

        for (GraphByAdjacencyList.Vertex<String> vertex : graph.vertices) {
            System.out.print(sourceVertexName + " -> " + vertex.data + " : " + vertex.d + " : ");
            printShortPath(source, vertex);
            System.out.println();
        }
    }

    /**
     * 广度优先搜索<br/>
     * 该方法会对顶点进行着色, 并且更新源节点到这个顶点的距离
     * @param s 源节点
     */
    private static void breadthFirstSearch(GraphByAdjacencyList.Vertex<String> s){
        s.color = GraphByAdjacencyList.Color.GRAY;
        s.d = 0;
        Queue<GraphByAdjacencyList.Vertex<String>> queue = new LinkedList<>();
        queue.add(s);

        while(!queue.isEmpty()){
            GraphByAdjacencyList.Vertex<String> parent = queue.poll();
            for (GraphByAdjacencyList.Vertex<String> v : parent.adjacencyList) {
                if(v.color == GraphByAdjacencyList.Color.WHITE){
                    v.color = GraphByAdjacencyList.Color.GRAY;
                    v.d = parent.d + 1;
                    v.p = parent;
                    queue.add(v);
                }
            }
            parent.color = GraphByAdjacencyList.Color.BLACK;
        }
    }

    /**
     * 输出最短路径
     */
    private static void printShortPath(GraphByAdjacencyList.Vertex<String> s, GraphByAdjacencyList.Vertex<String> v){
        if(v == s){
            System.out.print(s.data);
        }else{
            if(v.p == null){
                System.out.print(s.data + " -> " + v.data + "不可达");
            }else{
                printShortPath(s, v.p);
                System.out.print(" - " + v.data);
            }
        }
    }

    /**
     * 初始化一张图
     */
    private static GraphByAdjacencyList<String> init(){
        // 初始化图
        GraphByAdjacencyList<String> graph = new GraphByAdjacencyList<>();

        // 初始化顶点
        GraphByAdjacencyList.Vertex<String> v1 = new GraphByAdjacencyList.Vertex<>();
        v1.data = "A";

        GraphByAdjacencyList.Vertex<String> v2 = new GraphByAdjacencyList.Vertex<>();
        v2.data = "B";

        GraphByAdjacencyList.Vertex<String> v3 = new GraphByAdjacencyList.Vertex<>();
        v3.data = "C";

        GraphByAdjacencyList.Vertex<String> v4 = new GraphByAdjacencyList.Vertex<>();
        v4.data = "D";

        GraphByAdjacencyList.Vertex<String> v5 = new GraphByAdjacencyList.Vertex<>();
        v5.data = "E";

        GraphByAdjacencyList.Vertex<String> v6 = new GraphByAdjacencyList.Vertex<>();
        v6.data = "F";

        // 构建edge
        v1.adjacencyList.add(v2);
        v1.adjacencyList.add(v4);
//        v1.adjacencyList.add(v6);

        v2.adjacencyList.add(v1);
        v2.adjacencyList.add(v3);

        v3.adjacencyList.add(v4);
        v3.adjacencyList.add(v1);

        v4.adjacencyList.add(v4);   // 自指
        v4.adjacencyList.add(v5);

        v5.adjacencyList.add(v6);

        // 将顶点放入图中
        graph.vertices.add(v1);
        graph.vertices.add(v2);
        graph.vertices.add(v3);
        graph.vertices.add(v4);
        graph.vertices.add(v5);
        graph.vertices.add(v6);

        return graph;
    }

}

/**
 * 图邻接表表示
 */
class GraphByAdjacencyList<T> {

    // 顶点集
    List<Vertex<T>> vertices = new LinkedList<>();

    /**
     * 重置节点颜色及距离
     */
    void reset(){
        for (Vertex<T> vertex : vertices) {
            vertex.color = Color.WHITE;
            vertex.d = 0;
            vertex.p = null;
        }
    }

    enum Color{
        WHITE,
        GRAY,
        BLACK
    }

    /**
     * 顶点
     */
    public static class Vertex<T> {
        // 邻接表
        List<Vertex<T>> adjacencyList = new LinkedList<>();

        T data;

        /**
         * 节点颜色, 广度优先搜索会用到
         */
        Color color = Color.WHITE;

        /**
         * 源节点到该节点的距离, 广度优先搜索会用到
         */
        int d;

        /**
         * 通过广度优先搜索, 获取到的该节点的前驱节点
         */
        Vertex<T> p;
    }

}
