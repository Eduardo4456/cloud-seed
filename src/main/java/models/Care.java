package models;

public class Care {
	private CarePlant tarefa;
	private boolean concluida;
	
	public Care(CarePlant tarefa) {
		this.tarefa = tarefa;
		this.concluida = false;
	}

	public CarePlant getTarefa() {
		return tarefa;
	}

	public boolean isConcluida() {
		return concluida;
	}

	public void concluir() {
		this.concluida = true;
	}

	@Override
	public String toString() {
		return tarefa + (concluida ? " [concluida] ": " [pendente] ");
	}
	
	
	
}
