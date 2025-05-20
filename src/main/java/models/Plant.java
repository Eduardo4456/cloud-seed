package models;

import java.util.ArrayList;
import java.util.List;

public class Plant {
	
	private String nomePlanta;
	private String tipo;
	private String luzSolar;
	private String agua;
	private double tempMin;
	private double tempMax;
	
	//Lista de afazeres
	private List<Care> tarefas;
	
	public Plant() {}

	public Plant(String nomePlanta, String tipo, String luzSolar, String agua, double tempMin, double tempMax) {
		this.nomePlanta = nomePlanta;
		this.tipo = tipo;
		this.luzSolar = luzSolar;
		this.agua = agua;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
		this.tarefas = new ArrayList<>();
	}

	public Plant(String nomePlata) {
		this.nomePlanta = nomePlata;
		this.tarefas = new ArrayList<>();
	}

	public String getNomePlanta() {
		return nomePlanta;
	}

	public void setNomePlanta(String nomePlanta) {
		this.nomePlanta = nomePlanta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLuzSolar() {
		return luzSolar;
	}

	public void setLuzSolar(String luzSolar) {
		this.luzSolar = luzSolar;
	}

	public String getAgua() {
		return agua;
	}

	public void setAgua(String agua) {
		this.agua = agua;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public List<Care> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Care> tarefas) {
		this.tarefas = tarefas;
	}
	
	public void adicionarTarefa(CarePlant tarefa) {
		tarefas.add(new Care(tarefa));
	}

	@Override
	public String toString() {
		return "Planta:" + nomePlanta;
	}

}
