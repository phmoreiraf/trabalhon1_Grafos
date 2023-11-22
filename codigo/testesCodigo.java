package codigo;

import java.util.*;

class Graph {
    private int vertices;
    private LinkedList<Integer> adj[];
    private int time = 0;
    private static final int NIL = -1;

    // Construtor
    Graph(int v) {
        vertices = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // Método para adicionar uma aresta ao grafo
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // Método DFS para verificar se o grafo está conectado
    void DFSUtil(int v, boolean visited[]) {
        visited[v] = true;
        int n;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    // Método para verificar se todos os vértices não-zero estão conectados
    boolean isConnected() {
        boolean visited[] = new boolean[vertices];
        int i;
        for (i = 0; i < vertices; i++)
            visited[i] = false;

        for (i = 0; i < vertices; i++)
            if (adj[i].size() != 0)
                break;

        if (i == vertices)
            return true;

        DFSUtil(i, visited);

        for (i = 0; i < vertices; i++)
            if (visited[i] == false && adj[i].size() > 0)
                return false;

        return true;
    }

    // Método para verificar se o grafo é euleriano
    int isEulerian() {
        if (isConnected() == false)
            return 0;

        int odd = 0;
        for (int i = 0; i < vertices; i++)
            if (adj[i].size() % 2 != 0)
                odd++;

        if (odd > 2)
            return 0;

        return (odd == 2) ? 1 : 2;
    }

    // Método para imprimir o tour euleriano
    void printEulerTour() {
        int u = 0;
        for (int i = 0; i < vertices; i++) {
            if (adj[i].size() % 2 == 1) {
                u = i;
                break;
            }
        }

        printEulerUtil(u);
        System.out.println();
    }

    // Método para imprimir o tour euleriano a partir de um vértice
    void printEulerUtil(int u) {
        for (int i = 0; i < adj[u].size(); i++) {
            int v = adj[u].get(i);

            if (isValidNextEdge(u, v)) {
                System.out.print(u + "-" + v + " ");

                adj[u].remove(i);
                for (int j = 0; j < adj[v].size(); j++) {
                    if (adj[v].get(j) == u) {
                        adj[v].remove(j);
                        break;
                    }
                }

                printEulerUtil(v);
            }
        }
    }

    // Método para verificar se a próxima aresta é válida
    boolean isValidNextEdge(int u, int v) {
        if (adj[u].size() == 1) {
            return true;
        }

        boolean visited[] = new boolean[vertices];
        int count1 = DFSCount(u, visited);

        adj[u].remove((Integer) v);
        for (int i = 0; i < adj[v].size(); i++) {
            if (adj[v].get(i) == u) {
                adj[v].remove(i);
                break;
            }
        }

        visited = new boolean[vertices];
        int count2 = DFSCount(u, visited);

        adj[u].add(v);
        adj[v].add(u);

        return (count1 > count2) ? false : true;
    }

    // Método para contar o número de vértices alcançáveis a partir de v
    int DFSCount(int v, boolean visited[]) {
        visited[v] = true;
        int count = 1;

        for (int adj : adj[v]) {
            if (!visited[adj])
                count += DFSCount(adj, visited);
        }

        return count;
    }

    // Método Naïve para detecção de pontes
    void bridgeNaive() {
        boolean visited[] = new boolean[vertices];
        int disc[] = new int[vertices];
        int low[] = new int[vertices];
        int parent[] = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = NIL;
            visited[i] = false;
        }

        for (int i = 0; i < vertices; i++)
            if (visited[i] == false)
                bridgeNaiveUtil(i, visited, disc, low, parent);
    }

    void bridgeNaiveUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext()) {
            int v = i.next();
            if (!visited[v]) {
                parent[v] = u;
                bridgeNaiveUtil(v, visited, disc, low, parent);

                low[u] = Math.min(low[u], low[v]);

                if (low[v] > disc[u])
                    System.out.println(u + " " + v);
            } else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }

    // Método de Tarjan para detecção de pontes
    void bridgeTarjan() {
        boolean visited[] = new boolean[vertices];
        int disc[] = new int[vertices];
        int low[] = new int[vertices];
        int parent[] = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = NIL;
            visited[i] = false;
        }

        for (int i = 0; i < vertices; i++)
            if (visited[i] == false)
                bridgeTarjanUtil(i, visited, disc, low, parent);
    }

    void bridgeTarjanUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        Iterator<Integer> i = adj[u].iterator();
        while (i.hasNext()) {
            int v = i.next();
            if (!visited[v]) {
                parent[v] = u;
                bridgeTarjanUtil(v, visited, disc, low, parent);

                low[u] = Math.min(low[u], low[v]);

                if (low[v] > disc[u])
                    System.out.println(u + " " + v);
            } else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }

    public static void main(String args[]) {
        // Número de vértices nos grafos de teste
        int[] vertices = { 100, 1000, 10000, 100000 };

        for (int v : vertices) {
            switch (v) {
                case 100:
                    // Criação do grafo com 100 vértices
                    Graph g1 = new Graph(v);
                    // Adicione as arestas ao grafo g1 aqui...
                    for (int i = 0; i < v - 1; i++) {
                        g1.addEdge(i, i + 1);
                    }
                    // Para criar um grafo semi-euleriano, certifique-se de que exatamente dois
                    // vértices tenham grau ímpar.
                    g1.addEdge(v - 1, 0);
                    testGraph(g1);
                    break;
                case 1000:
                    // Criação do grafo com 1000 vértices
                    Graph g2 = new Graph(v);
                    // Adicione as arestas ao grafo g2 aqui...
                    for (int i = 0; i < v - 1; i++) {
                        g2.addEdge(i, i + 1);
                    }
                    // Para criar um grafo não euleriano, certifique-se de que mais de dois vértices
                    // tenham grau ímpar.
                    g2.addEdge(v - 1, 0); // Semi eureliano
                    g2.addEdge(0, 2); // Isso fará com que os vértices 0, 1 e 2 tenham grau ímpar.
                    testGraph(g2);
                    break;
                case 10000:
                    // Criação do grafo com 10000 vértices
                    Graph g3 = new Graph(v);
                    // Adicione as arestas ao grafo g3 aqui...
                    for (int i = 0; i < v - 1; i++) {
                        g3.addEdge(i, i + 1);
                    }
                    // Para criar um grafo semi-euleriano, certifique-se de que exatamente dois
                    // vértices tenham grau ímpar.
                    g3.addEdge(v - 1, 0);
                    testGraph(g3);
                    break;
                case 100000:
                    // Criação do grafo com 100000 vértices
                    Graph g4 = new Graph(v);
                    // Adicione as arestas ao grafo g4 aqui...
                    for (int i = 0; i < v - 1; i++) {
                        g4.addEdge(i, i + 1);
                    }
                    // Para criar um grafo não euleriano, certifique-se de que mais de dois vértices
                    // tenham grau ímpar.
                    g4.addEdge(v - 1, 0);
                    g4.addEdge(0, 2); // Isso fará com que os vértices 0, 1 e 2 tenham grau ímpar.
                    testGraph(g4);
                    break;
            }
        }
    }

    public static void testGraph(Graph g) {
        // Início da medição do tempo
        long startTime = System.nanoTime();

        // Verificação se o grafo é euleriano e impressão do tour euleriano
        int res = g.isEulerian();
        if (res == 0)
            System.out.println("O grafo não é euleriano");
        else if (res == 1)
            System.out.println("O grafo tem um caminho euleriano (Semi - Eureliano)");
        else
            System.out.println("O grafo tem um ciclo euleriano");

        g.printEulerTour();

        // Teste dos métodos de detecção de pontes
        System.out.println("Pontes no grafo:");
        g.bridgeNaive();
        g.bridgeTarjan();

        // Fim da medição do tempo
        long endTime = System.nanoTime();

        // Cálculo e impressão do tempo de execução
        long duration = (endTime - startTime);
        System.out.println("Tempo de execução para " + g.vertices + " vértices: " + duration + " nanosegundos");
    }
}
