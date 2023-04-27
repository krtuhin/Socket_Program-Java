package com.Connected_Graph;

import java.util.ArrayList;

public class ConnectedGraph {
    static int V = 8; // Number of vertices in the graph

    public static void dfs(int[][] adj_mat, int node, boolean[] visited, ArrayList<Integer> component) {
        visited[node] = true;
        component.add(node);

        for (int i = 0; i < V; i++) {
            if (adj_mat[node][i] != 0 && !visited[i]) {
                dfs(adj_mat, i, visited, component);
            }
        }
    }

    public static void findComponents(int[][] adj_mat, ArrayList<ArrayList<Integer>> components) {
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                ArrayList<Integer> component = new ArrayList<Integer>();
                dfs(adj_mat, i, visited, component);
                components.add(component);
            }
        }
    }

    public static void main(String[] args) {
        int[][] adj_mat = {
                {0, 2, 6, 3, Integer.MAX_VALUE, 4, Integer.MAX_VALUE, 1},
                {2, 0, 4, 1, 5, 5, 1, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 4, 0, 6, 1, 3, 1, 3},
                {3, 1, 6, 0, 5, Integer.MAX_VALUE, Integer.MAX_VALUE, 2},
                {Integer.MAX_VALUE, 5, 1, 5, 0, 4, 5, 2},
                {4, Integer.MAX_VALUE, 3, Integer.MAX_VALUE, 4, 0, 3, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 1, 1, 8, 5, 3, 0, Integer.MAX_VALUE},
                {1, 4, 3, 2, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, 0},
        };

        int[][] adj_list = new int[V][V];

        // Convert adjacency matrix to adjacency list
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (adj_mat[i][j] != Integer.MAX_VALUE) {
                    adj_list[i][j] = 1;
                }
            }
        }

        ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();
        findComponents(adj_list, components);

        if (components.size() == 1) {
            System.out.println("The graph is a connected graph.");
        } else {
            System.out.println("The graph is a disconnected graph.");
            for (int i = 0; i < components.size(); i++) {
                System.out.print("Component " + (i + 1) + ": ");
                for (int j = 0; j < components.get(i).size(); j++) {
                    System.out.print((char) ('A' + components.get(i).get(j)) + " ");
                }
                System.out.println();
            }
        }
    }

}
