package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import interfaces.Gerenciavel;
import models.CarePlant;
import models.Plant;

public class PlantService implements Gerenciavel{
	private List<Plant> plantas;

	public PlantService() {
		 this.plantas = new ArrayList<>();
	}
	
	public void adicionarPlanta(Scanner scanner) {
		Scanner escolhaScanner = new Scanner(System.in);
		int escolha = 0;
		
		do {
			System.out.println("Nome da planta: ");
			String nomePlanta = scanner.nextLine();
			plantas.add(new Plant(nomePlanta));
			System.out.println("Planta adicionada");
			
			System.out.println("\nClique [1] para adicionar tarefas para a planta, e qualquer tecla para adicionar outra planta:");
			escolha = escolhaScanner.nextInt();
		} while (escolha != 1);		
	}
	
	public void adicionarTarefa(Scanner scanner) {
        Plant planta = selecionarPlanta(scanner);
        if (planta != null) {
            System.out.print("Descrição da tarefa: ");
            String descricao = scanner.nextLine();
            planta.adicionarTarefa(new CarePlant(descricao));
            System.out.println("Tarefa adicionada!");
        }
    }
	
	public void listarPlantasETarefas() {
		for (int i = 0; i < plantas.size(); i++) {
            Plant p = plantas.get(i);
            System.out.println("\n[" + i + "] " + p.getNomePlanta());
            List<models.Care> tarefas = p.getTarefas();
            if (tarefas.isEmpty()) {
                System.out.println("  Nenhuma tarefa.");
            } else {
                for (int j = 0; j < tarefas.size(); j++) {
                    System.out.println("  [" + j + "] " + tarefas.get(j));
                }
            }
        }
	}
	
	public void concluirTarefa(Scanner scanner) {
        Plant planta = selecionarPlanta(scanner);
        if (planta != null) {
            List<models.Care> tarefas = planta.getTarefas();
            if (tarefas.isEmpty()) {
                System.out.println("Essa planta não tem tarefas.");
                return;
            }

            System.out.println("Tarefas:");
            for (int i = 0; i < tarefas.size(); i++) {
                System.out.println("[" + i + "] " + tarefas.get(i));
            }

            System.out.print("Digite o número da tarefa a concluir: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // limpar buffer
            if (index >= 0 && index < tarefas.size()) {
                tarefas.get(index).concluir();
                System.out.println("Tarefa concluída!");
            } else {
                System.out.println("Índice inválido.");
            }
        }
    }

    private Plant selecionarPlanta(Scanner scanner) {
        if (plantas.isEmpty()) {
            System.out.println("Nenhuma planta cadastrada.");
            return null;
        }

        System.out.println("Selecione uma planta:");
        for (int i = 0; i < plantas.size(); i++) {
            System.out.println("[" + i + "] " + plantas.get(i));
        }

        System.out.print("Número da planta: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        if (index >= 0 && index < plantas.size()) {
            return plantas.get(index);
        } else {
            System.out.println("Índice inválido.");
            return null;
        }
    }

	@Override
	public void listar() {
		listarPlantasETarefas();
	}

}
