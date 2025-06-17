package model;

import java.io.Serializable;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlantaCadastrada implements Serializable {
    public String nome, tipo, cidade;
    public List<LocalDate> historicoRega = new ArrayList<>();
    public int freqRegaSemanal;
    public Set<DayOfWeek> diasRega;

    public PlantaCadastrada(String nome, String tipo, String cidade, int freq, Set<DayOfWeek> dias) {
        this.nome = nome;
        this.tipo = tipo;
        this.cidade = cidade;
        this.freqRegaSemanal = freq;
        this.diasRega = dias;
    }

    public void regar() {
        historicoRega.add(LocalDate.now());
    }

    public LocalDate getUltimaRega() {
        return historicoRega.isEmpty() ? null : historicoRega.get(historicoRega.size() - 1);
    }

    public boolean precisaRegar() {
        LocalDate hoje = LocalDate.now();
        if (getUltimaRega() != null && getUltimaRega().isEqual(hoje)) return false;
        long nasUltimas7 = historicoRega.stream()
                .filter(d -> d.isAfter(hoje.minusDays(7))).count();
        return nasUltimas7 < freqRegaSemanal;
    }

    public boolean hojeEDiaDeRega() {
        return diasRega.contains(LocalDate.now().getDayOfWeek());
    }

    public int diasAteProximaRega() {
        DayOfWeek hoje = LocalDate.now().getDayOfWeek();
        List<Integer> ordenados = diasRega.stream()
                .map(DayOfWeek::getValue).sorted().collect(Collectors.toList());
        for (int d : ordenados) if (d > hoje.getValue()) return d - hoje.getValue();
        return 7 - hoje.getValue() + ordenados.get(0);
    }

    @Override
    public String toString() {
        String ult = Optional.ofNullable(getUltimaRega()).map(LocalDate::toString).orElse("Nunca regada");
        String dias = diasRega.stream()
                .map(d -> d.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()))
                .collect(Collectors.joining(", "));
        return String.format("🌱 %s (%s) - %s | Última rega: %s | %dx/semana | Dias: %s",
                nome, tipo, cidade, ult, freqRegaSemanal, dias);
    }
}