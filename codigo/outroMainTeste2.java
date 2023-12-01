package codigo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public void fleury(int start) {
        // Percorre todas as arestas uma por uma
        for (int v : adj[start]) {
            // Se a aresta não é uma ponte, então a remove do grafo e imprime o caminho
            // euleriano
            if (adj[start].size() == 1 || !isBridge(start, v)) {
                System.out.print(start + "-" + v + " ");
                adj[start].remove((Integer) v);
                adj[v].remove((Integer) start);
                fleury(v);
            }
        }
    }
    /// INICIO

    // Cria três grafos: euleriano, semi-euleriano e não euleriano
        //Graph eulerian = new Graph(v);
        //Graph semiEulerian = new Graph(v);
        //Graph nonEulerian = new Graph(v);
        Graph g = new Graph(v);

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
                        + (endTime - startTime) + " nanosegundos");

                startTime = System.nanoTime();
                semiEulerian.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método Naive com grafo semi-euleriano de " + v + " vértices: "
                        + (endTime - startTime) + " nanosegundos");

                startTime = System.nanoTime();
                nonEulerian.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método Naive com grafo não euleriano de " + v + " vértices: "
                        + (endTime - startTime) + " nanosegundos");
                break;
            case 2:
                startTime = System.nanoTime();
                eulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo euleriano de " + v + " vértices: "
                        + (endTime - startTime) + " nanosegundos");

                startTime = System.nanoTime();
                semiEulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo semi-euleriano de " + v + " vértices: "
                        + (endTime - startTime) + " nanosegundos");

                startTime = System.nanoTime();
                nonEulerian.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com grafo não euleriano de " + v + " vértices: "
                        + (endTime - startTime) + " nanosegundos");
                break;
            case 3:

                if (eulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    eulerian.isEulerian();
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo euleriano de " + v + " vértices: "
                            + (endTime - startTime) + " nanosegundos");
                } else {
                    startTime = System.nanoTime();
                    eulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo não euleriano ou semi-euleriano de " + v
                            + " vértices: " + (endTime - startTime) + " nanosegundos");
                }

                /*if (semiEulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    semiEulerian.isEulerian();
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo semi-euleriano de " + v + " vértices: "
                            + (endTime - startTime) + " nanosegundos");
                } else {
                    startTime = System.nanoTime();
                    semiEulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo não euleriano ou semi-euleriano de " + v
                            + " vértices: " + (endTime - startTime) + " nanosegundos");
                }

                if (nonEulerian.isEulerian() != 0) {
                    startTime = System.nanoTime();
                    nonEulerian.isEulerian();
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo não euleriano de " + v + " vértices: "
                            + (endTime - startTime) + " nanosegundos");
                } else {
                    startTime = System.nanoTime();
                    nonEulerian.fleury(0);
                    endTime = System.nanoTime();
                    System.out.println("Tempo para o método de Fleury com grafo não euleriano ou semi-euleriano de " + v
                            + " vértices: " + (endTime - startTime) + " nanosegundos");
                }*/
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

//FIM

public class outroMainTeste2 {
 

      public static void main(String[] args) {
        // Cria um scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Pede ao usuário para inserir o nome do arquivo
        System.out.print("Digite o nome do arquivo: ");
        String filename = scanner.nextLine();

        // Lê o grafo do arquivo
        Graph g = readGraphFromFile(filename);

        // Pede ao usuário para escolher o método para identificar pontes
        System.out.println("Escolha o método para identificar pontes:");
        System.out.println("1. Método Naive");
        System.out.println("2. Método de Tarjan");
        int choice = scanner.nextInt();

        // Identifica as pontes usando o método escolhido
        switch (choice) {
            case 1:
                g.bridgeNaive();
                break;
            case 2:
                g.bridgeTarjan();
                break;
            case 3:
            System.out.println("Saindo...");
            scanner.close();
            break;
            default:
                System.out.println("Escolha inválida.");
                //return;
        }
        
        // Verifica se o grafo é euleriano, semi-euleriano ou não euleriano
        int res = g.isEulerian();
        if (res == 0)
            System.out.println("O grafo não é euleriano.");
        else if (res == 1)
            System.out.println("O grafo é semi-euleriano.");
        else
            System.out.println("O grafo é euleriano.");

        // Encontra um caminho euleriano usando o método de Fleury
        g.fleury(0);
    }

    // Função para ler um grafo de um arquivo
    static Graph readGraphFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            int V = Integer.parseInt(reader.readLine());
            Graph g = new Graph(V);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int v = Integer.parseInt(parts[0]);
                int w = Integer.parseInt(parts[1]);
                g.addEdge(v, w);
            }
            reader.close();
            return g;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
