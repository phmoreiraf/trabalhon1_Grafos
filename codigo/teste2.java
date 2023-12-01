package codigo;

//import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

// Classe para representar um grafo
class Graph {
    private int V; // Número de vértices
    private LinkedList<Integer> adj[]; // Lista de adjacências

    // Construtor
    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // Função para adicionar uma aresta ao grafo
    public void addEdge(int v, int w) {
        adj[v].add(w); // Adiciona w à lista de v
        adj[w].add(v); // Adiciona v à lista de w
    }

    // Método Naive para encontrar pontes
    public void bridgeNaive() {
        // Percorre todas as arestas uma por uma
        for (int u = 0; u < V; u++) {
            for (int v : adj[u]) {
                // Remove aresta da lista de adjacências
                adj[u].remove((Integer) v);
                adj[v].remove((Integer) u);

                // Verifica se o grafo ainda é conexo
                boolean[] visited = new boolean[V];
                int numVisited = 0;
                LinkedList<Integer> queue = new LinkedList<Integer>();
                queue.add((u + 1) % V); // Começa a busca em um vértice diferente de u
                visited[(u + 1) % V] = true;
                while (!queue.isEmpty()) {
                    int s = queue.poll();
                    numVisited++;
                    for (int i : adj[s]) {
                        if (!visited[i]) {
                            visited[i] = true;
                            queue.add(i);
                        }
                    }
                }

                // Se o número de vértices visitados é menor que V, então a aresta é uma ponte
                if (numVisited < V)
                    System.out.println(u + " " + v);

                // Adiciona a aresta de volta à lista de adjacências
                adj[u].add(v);
                adj[v].add(u);
            }
        }
    }

    // Método de Tarjan para encontrar pontes
    public void bridgeTarjan() {
        boolean visited[] = new boolean[V];
        int disc[] = new int[V];
        int low[] = new int[V];
        int parent[] = new int[V];

        // Inicializa os arrays de visitados e pais
        for (int i = 0; i < V; i++) {
            parent[i] = -1;
            visited[i] = false;
        }

        // Chama a função recursiva para encontrar pontes
        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                bridgeTarjanUtil(i, visited, disc, low, parent);
    }

    // Função recursiva para encontrar pontes
    public void bridgeTarjanUtil(int u, boolean visited[], int disc[], int low[], int parent[]) {
        int time = 0;
        visited[u] = true;
        disc[u] = low[u] = ++time;

        // Percorre todos os vértices adjacentes a este
        for (int v : adj[u]) {
            // Se v não foi visitado, então o marca como visitado e processa a recursão
            if (!visited[v]) {
                parent[v] = u;
                bridgeTarjanUtil(v, visited, disc, low, parent);

                // Verifica se a subárvore enraizada em v tem uma conexão com um dos ancestrais
                // de u
                low[u] = Math.min(low[u], low[v]);

                // Se o vértice de menor grau que pode ser alcançado de v é menor que o tempo de
                // descoberta de u, então u-v é uma ponte
                if (low[v] > disc[u])
                    System.out.println(u + " " + v);
            }

            // Atualiza o vértice de menor grau
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }
    }

    // Método de Fleury para encontrar um caminho euleriano
    public void fleury(int start) {
        // Cria uma pilha para armazenar o caminho euleriano
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        // Cria uma lista para armazenar o caminho euleriano final
        List<Integer> path = new ArrayList<>();

        while (!stack.isEmpty()) {
            int current = stack.peek();
            if (adj[current].size() > 0) {
                // Se o vértice atual tem arestas, remove a primeira aresta e empilha o vértice
                // adjacente
                int next = adj[current].get(0);
                adj[current].remove((Integer) next);
                adj[next].remove((Integer) current);
                stack.push(next);
            } else {
                // Se o vértice atual não tem arestas, adiciona-o ao caminho euleriano e
                // remove-o da pilha
                path.add(current);
                stack.pop();
            }
        }

        // Imprime o caminho euleriano
        for (int i : path) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    // Função para verificar se uma aresta é uma ponte
    public boolean isBridge(int u, int v) {
        // Conta o número de vértices alcançáveis a partir de u
        boolean visited[] = new boolean[V];
        int count1 = DFSCount(u, visited);

        // Remove a aresta (u, v) e conta o número de vértices alcançáveis a partir de u
        adj[u].remove((Integer) v);
        adj[v].remove((Integer) u);
        visited = new boolean[V];
        int count2 = DFSCount(u, visited);

        // Adiciona a aresta de volta ao grafo
        adj[u].add(v);
        adj[v].add(u);

        // Se o número de vértices alcançáveis é reduzido, então a aresta é uma ponte
        return (count1 > count2) ? true : false;
    }

    // Função para contar o número de vértices alcançáveis a partir de v
    public int DFSCount(int v, boolean visited[]) {
        visited[v] = true;
        int count = 1;
        for (int adj : adj[v])
            if (!visited[adj])
                count += DFSCount(adj, visited);
        return count;
    }

    // Função para verificar se um grafo é euleriano, semi-euleriano ou não
    // euleriano
    public int isEulerian() {
        // Verifica se todos os vértices não isolados têm grau par
        int odd = 0;
        for (int i = 0; i < V; i++)
            if (adj[i].size() % 2 != 0)
                odd++;

        // Se o número de vértices com grau ímpar é 0, então o grafo é euleriano
        // Se o número de vértices com grau ímpar é 2, então o grafo é semi-euleriano
        // Se o número de vértices com grau ímpar é maior que 2, então o grafo não é
        // euleriano
        if (odd > 2)
            return 0;
        else if (odd == 2)
            return 1;
        else
            return 2;
    }
}

public class teste2 {
    public static void main(String[] args) {
        // Cria um scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Pede ao usuário para escolher o tamanho do grafo
        System.out.println("Escolha o tamanho do grafo:");
        System.out.println("1. 100 vértices");
        System.out.println("2. 1000 vértices");
        System.out.println("3. 10000 vértices");
        System.out.println("4. 100000 vértices");
        System.out.println("5. Sair");
        int choice = scanner.nextInt();

        if (choice == 5) {
            scanner.close();
            return;
        }

        // Cria o grafo do tamanho escolhido
        int v;
        switch (choice) {
            case 1:
                v = 100;
                break;
            case 2:
                v = 1000;
                break;
            case 3:
                v = 10000;
                break;
            case 4:
                v = 100000;
                break;
            case 5:
                System.out.println("Saindo...");
                scanner.close();
            default:
                System.out.println("Escolha inválida.");
                return;
        }

        // Cria três grafos: euleriano, semi-euleriano e não euleriano
        Graph eulerian = new Graph(v);
        Graph semiEulerian = new Graph(v);
        Graph nonEulerian = new Graph(v);
        // Graph g = new Graph(v);

        /*
         * for (int i = 0; i < v; i++) {
         * for (int j = i + 1; j < v; j++) {
         * g.addEdge(i, j);
         * }
         * }
         */

        // Adiciona arestas aos grafos
        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                // O grafo euleriano é um grafo completo
                eulerian.addEdge(i, j);

                // O grafo semi-euleriano tem duas arestas a menos
                if (j != i + 1 && j != i + 2) {
                    semiEulerian.addEdge(i, j);
                }

                // O grafo não euleriano tem três arestas a menos
                if (j != i + 1 && j != i + 2 && j != i + 3) {
                    nonEulerian.addEdge(i, j);
                }
            }
        }

        // Pede ao usuário para escolher o método para identificar pontes ou encontrar
        // um caminho euleriano
        System.out.println("Escolha o método:");
        System.out.println("1. Método Naive");
        System.out.println("2. Método de Tarjan");
        System.out.println("3. Método de Fleury");
        System.out.println("4. Sair");
        choice = scanner.nextInt();

        // Executa o método escolhido e mede o tempo
        long startTime, endTime;
        switch (choice) {
            case 1:
                startTime = System.nanoTime();
                eulerian.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método Naive com grafo euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");

                startTime = System.nanoTime();
                semiEulerian.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método Naive com grafo semi-euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");

                startTime = System.nanoTime();
                nonEulerian.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método Naive com grafo não euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");
                break;
            case 2:
                startTime = System.nanoTime();
                eulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");

                startTime = System.nanoTime();
                semiEulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo semi-euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");

                startTime = System.nanoTime();
                nonEulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo não euleriano de " + v + " vértices: "
                        + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");
                break;
            case 3:

                if (eulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    eulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo euleriano de " + v + " vértices: "
                            + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");
                }

                if (semiEulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    semiEulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo semi-euleriano de " + v + " vértices: "
                            + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");
                }

                if (nonEulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    nonEulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo não euleriano de " + v + " vértices: "
                            + TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS) + " segundos");
                }
                break;

                /*
                 * startTime = System.nanoTime();
                 * int res = g.isEulerian();
                 * if (res == 0)
                 * System.out.println("O grafo não é euleriano.");
                 * else if (res == 1)
                 * System.out.println("O grafo é semi-euleriano.");
                 * else
                 * System.out.println("O grafo é euleriano.");
                 * 
                 * // Encontra um caminho euleriano usando o método de Fleury
                 * g.fleury(0);
                 * endTime = System.nanoTime();
                 * System.out.println("Tempo para o método de Fleury com " + v + " vértices: " +
                 * (endTime - startTime)
                 * + " segundos");
                 */
            case 5:
                System.err.println("Saindo...");
                scanner.close();
                break;
            default:
                System.out.println("Escolha inválida.");
                scanner.close();
                return;
        }
        // scanner.close();
    }
}
