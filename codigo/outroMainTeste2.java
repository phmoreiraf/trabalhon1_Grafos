package codigo;

import java.util.*;

public class outroMainTeste2 {
    public static void main(String[] args) {
        // Cria um scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Pede ao usuário para escolher o tamanho do grafo
        System.out.println("Escolha o tamanho do grafo:");
        System.out.println("1. 100 vértices");
        System.out.println("2. 1000 vértices");
        System.out.println("3. 10000 vértices");
        System.out.println("4. 100000 vértices");
        int choice = scanner.nextInt();

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
            default:
                System.out.println("Escolha inválida.");
                return;
        }
        Graph g = new Graph(v);
        for (int i = 0; i < v; i++) {
            for (int j = i + 1; j < v; j++) {
                g.addEdge(i, j);
            }
        }

        // Pede ao usuário para escolher o método para identificar pontes ou encontrar
        // um caminho euleriano
        System.out.println("Escolha o método:");
        System.out.println("1. Método Naive");
        System.out.println("2. Método de Tarjan");
        System.out.println("3. Método de Fleury");
        choice = scanner.nextInt();

        // Executa o método escolhido e mede o tempo
        long startTime, endTime;
        switch (choice) {
            case 1:
                startTime = System.nanoTime();
                g.bridgeNaive();
                endTime = System.nanoTime();
                System.out.println(
                        "Tempo para o método Naive com " + v + " vértices: " + (endTime - startTime) + " nanosegundos");
                break;
            case 2:
                startTime = System.nanoTime();
                g.bridgeTarjan();
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Tarjan com " + v + " vértices: " + (endTime - startTime)
                        + " nanosegundos");
                break;
            case 3:
                startTime = System.nanoTime();
                int res = g.isEulerian();
                if (res == 0)
                    System.out.println("O grafo não é euleriano.");
                else if (res == 1)
                    System.out.println("O grafo é semi-euleriano.");
                else
                    System.out.println("O grafo é euleriano.");

                // Encontra um caminho euleriano usando o método de Fleury
                g.fleury(0);
                endTime = System.nanoTime();
                System.out.println("Tempo para o método de Fleury com " + v + " vértices: " + (endTime - startTime)
                        + " nanosegundos");
                break;
            default:
                System.out.println("Escolha inválida.");
                return;
        }
    }
}
