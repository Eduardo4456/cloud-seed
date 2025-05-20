package com.plantcloudseed;

import java.util.Scanner;

import services.PlantService;

public class Main {
    public static void main(String[] args) {
        PlantService plantService = new PlantService();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Adicionar Planta");
            System.out.println("2. Adicionar Tarefa à Planta");
            System.out.println("3. Listar Plantas e Tarefas");
            System.out.println("4. Concluir Tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                	plantService.adicionarPlanta(scanner);
                	break;
                case 2:
                	plantService.adicionarTarefa(scanner);
                	break;
                case 3:
                	plantService.listarPlantasETarefas();
                	break;
                case 4:
                	plantService.concluirTarefa(scanner);
                	break;
                case 0:
                	System.out.println("Encerrando...");
                	break;
                default:
                	System.out.println("Opção inválida.");
                	break;
            }

        } while (opcao != 0);

        scanner.close();
    }
}
