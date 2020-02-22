package sch.frog.clrs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Breadth First Search 广度优先搜索
 */
public class BFS {

    public static void main(String[] args){
        GraphByAdjacencyList<String> graph = init();
        String sourceVertexName = "F";
        GraphByAdjacencyList.Vertex<String> source = new GraphByAdjacencyList.Vertex<>();
        for (GraphByAdjacencyList.Vertex<String> vertex : graph.vertices) {
            if(sourceVertexName.equals(vertex.data)){
                source = vertex;
                break;
            }
        }

        List<PathInfo<String>> list = breadthFirstSearch(source, graph);
        for (PathInfo<String> stringPathInfo : list) {
            System.out.println(stringPathInfo);
        }

    }

    /**
     * 广度优先搜索, 最终返回值构建一个列表, 列出从s到达图上任意可达节点的最短路径
     * @param s 源节点
     * @param graph 要搜索的图
     * @return 最短路径列表
     */
    private static List<PathInfo<String>> breadthFirstSearch(GraphByAdjacencyList.Vertex<String> s, GraphByAdjacencyList<String> graph){
        List<PathInfo<String>> result = new ArrayList<>();
        graph.reset();  //  重置颜色

        PathInfo<String> start = new PathInfo<>(s);
        s.color = GraphByAdjacencyList.Color.GRAY;
        start.d = 0;
        Queue<PathInfo<String>> queue = new LinkedList<>();
        queue.add(start);

        while(!queue.isEmpty()){
            PathInfo<String> pathInfo = queue.poll();
            for (GraphByAdjacencyList.Vertex<String> v : pathInfo.vertex.adjacencyList) {
                if(v.color == GraphByAdjacencyList.Color.WHITE){
                    v.color = GraphByAdjacencyList.Color.GRAY;
                    PathInfo<String> p = new PathInfo<>(v);
                    p.d = pathInfo.d + 1;
                    queue.add(p);
                    result.add(p);
                }
            }
            pathInfo.vertex.color = GraphByAdjacencyList.Color.BLACK;
        }

        graph.reset();  // 重置颜色
        return result;
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
        v1.adjacencyList.add(v6);

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

class PathInfo<T>{
    GraphByAdjacencyList.Vertex<T> vertex = null;
    int d;  // 从源节点到该节点所经过的路径个数

    public PathInfo(GraphByAdjacencyList.Vertex<T> vertex) {
        this.vertex = vertex;
    }

    @Override
    public String toString() {
        return this.vertex.data + " : " + d;
    }
}

/**
 * 图邻接表表示
 */
class GraphByAdjacencyList<T> {

    // 顶点集
    List<Vertex<T>> vertices = new LinkedList<>();

    /**
     * 重置节点颜色
     */
    void reset(){
        for (Vertex<T> vertex : vertices) {
            vertex.color = Color.WHITE;
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
        Color color;
    }

}
