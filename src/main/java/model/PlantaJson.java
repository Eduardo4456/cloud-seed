package model;

public class PlantaJson {
    public String nome_popular;
    public String nome_cientifico;
    public String temperatura_ideal_celsius;
    public String frequencia_rega;
    public String luz;
	public Object regas;

    public double[] getFaixaTemperatura() {
        try {
            String faixa = temperatura_ideal_celsius.replace("–", "-").replace("°C", "");
            String[] partes = faixa.split("-");
            return new double[] {
                Double.parseDouble(partes[0].trim()),
                Double.parseDouble(partes[1].trim())
            };
        } catch (Exception e) {
            return new double[]{0, 0}; // ou lançar uma exceção
        }
    }

    @Override
    public String toString() {
        return nome_popular + " (" + nome_cientifico + ") - Temperatura ideal: " + temperatura_ideal_celsius;
    }
}
