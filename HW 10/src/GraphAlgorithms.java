import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Ruokun Niu
 * @version 1.0
 * @userid rniu8
 * @GTID 903487882
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Either one of the input is "
                    + "null or the start vertex does not exist in the graph;"
                    + "the traversal terminates");
        }
        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
        ArrayList<Vertex<T>> result = new ArrayList<Vertex<T>>();
        Map<Vertex<T>, List<VertexDistance<T>>> vertexMap = graph.getAdjList();
        visitedSet.add(start);
        queue.add(start);
        while (queue.size() != 0 && result.size()
                != graph.getVertices().size()) {
            Vertex<T> v = queue.remove();
            result.add(v);
            List<Vertex<T>> adjacent = new ArrayList<Vertex<T>>();
            for (VertexDistance<T> obj : vertexMap.get(v)) {
                adjacent.add(obj.getVertex());
            }
            for (Vertex<T> w: adjacent) {
                if (!visitedSet.contains(w)) {
                    visitedSet.add(w);
                    queue.add(w);
                }
            }
        }
        return result;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Either one of the input is "
                    + "null or the start vertex does not exist in the graph;"
                    + "the traversal terminates");
        }
        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        Map<Vertex<T>, List<VertexDistance<T>>> vertexMap = graph.getAdjList();
        List<Vertex<T>> result = new ArrayList<Vertex<T>>();
        dfsH(start, graph, visitedSet, vertexMap, result);
        return result;
    }

    /**
     * A private helper method that performs the depth first search recursively
     * @param start The start vertex for the current traversal
     * @param graph The graph that will be traversed
     * @param visitedSet A set that contains all visited verticies
     * @param vertexMap The map that contains information on the adjacent
     *                  vertices
     * @param result The result list with all of the traversed vertices
     * @param <T> The generic typing of the data
     */
    private static <T> void dfsH(Vertex<T> start, Graph<T> graph, Set<Vertex<T>>
            visitedSet, Map<Vertex<T>, List<VertexDistance<T>>> vertexMap,
                                 List<Vertex<T>> result) {
        visitedSet.add(start);
        result.add(start);
        List<Vertex<T>> adjacent = new ArrayList<Vertex<T>>();
        for (VertexDistance<T> obj : vertexMap.get(start)) {
            adjacent.add(obj.getVertex());
        }
        for (Vertex<T> neighbors : adjacent) {
            if (!visitedSet.contains(neighbors)) {
                dfsH(neighbors, graph, visitedSet, vertexMap, result);
            }
        }
    }
    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null
                || !graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("Either one of the input is "
                    + "null or the start vertex does not exist in the graph;"
                    + "the algorithm terminates");
        }
        Set<Vertex<T>> visitedSet = new HashSet<Vertex<T>>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<Vertex<T>, Integer>();
        PriorityQueue<VertexDistance<T>> pq = new
                PriorityQueue<VertexDistance<T>>();
        //setting all distance to INF
        for (Vertex<T> vertices : graph.getVertices()) {
            if (vertices != start) {
                distanceMap.put(vertices, Integer.MAX_VALUE);
            }
        }
        //enqueue start
        pq.add(new VertexDistance<T>(start, 0));
        while (!pq.isEmpty() && visitedSet.size()
                < graph.getVertices().size()) {
            VertexDistance<T> temp = pq.remove(); //deque the first obj
            if (!visitedSet.contains(temp.getVertex())) {
                visitedSet.add(temp.getVertex());
                distanceMap.put(temp.getVertex(), temp.getDistance());
                List<VertexDistance<T>> adjacent = graph.getAdjList().
                        get(temp.getVertex());
                for (VertexDistance<T> vd : adjacent) { //add adjacent to the q
                    VertexDistance<T> newVD = new VertexDistance
                            <T>(vd.getVertex(),
                            vd.getDistance() + temp.getDistance());
                    pq.add(newVD);
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null; the "
                    + "algorithm terminates");
        }
        DisjointSet<T> djset = new DisjointSet<T>(); //the disjointed set
        Set<Edge<T>> mst = new HashSet<Edge<T>>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<Edge<T>>();
        for (Edge<T> edge : graph.getEdges()) {
            pq.add(edge);
        }
        //priority queue has all of the edges added
        while (!pq.isEmpty() && mst.size() < graph.getEdges().size() - 1) {
            Edge<T> temp = pq.remove(); //deque from the pq
            if (!(djset.find(temp.getU()).equals(djset.find(temp.getV())))) {
                mst.add(temp);
                Edge<T> reverse = new Edge<>(temp.getV(), temp.getU(),
                        temp.getWeight());
                mst.add(reverse);
                djset.union(temp.getU().getData(), temp.getV().getData());
            }
        }
        if (mst.size() != ((graph.getVertices().size() - 1) * 2)) {
            return null;
        }
        return mst;
    }
}
