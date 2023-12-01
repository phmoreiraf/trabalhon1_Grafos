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
