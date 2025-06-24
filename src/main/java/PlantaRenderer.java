import model.PlantaCadastrada;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.Set;

public class PlantaRenderer extends JPanel implements ListCellRenderer<PlantaCadastrada> {
	

    private String formatarDiasRega(Set<DayOfWeek> dias) {
        Map<DayOfWeek, String> diasPt = Map.of(
            DayOfWeek.MONDAY, "Seg",
            DayOfWeek.TUESDAY, "Ter",
            DayOfWeek.WEDNESDAY, "Qua",
            DayOfWeek.THURSDAY, "Qui",
            DayOfWeek.FRIDAY, "Sex",
            DayOfWeek.SATURDAY, "Sáb",
            DayOfWeek.SUNDAY, "Dom"
        );

        return dias.stream()
                   .sorted()
                   .map(diasPt::get)
                   .reduce((d1, d2) -> d1 + ", " + d2)
                   .orElse("");
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends PlantaCadastrada> list, PlantaCadastrada planta,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        setBackground(isSelected ? new Color(220, 245, 220) : new Color(240, 255, 240)); // verde claro

        JLabel nome = new JLabel("🌱 " + planta.nome);
        nome.setFont(new Font("SansSerif", Font.BOLD, 14));

        String info = "💧 Rega: " + planta.freqRegaSemanal + "x/semana | Última: " +
                (planta.getUltimaRega() != null ? planta.getUltimaRega() : "Nunca") +
                " | Dias: " + formatarDiasRega(planta.diasRega);

        JLabel detalhes = new JLabel(info);
        detalhes.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detalhes.setForeground(new Color(50, 80, 50));

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setOpaque(false);
        content.add(nome);
        content.add(detalhes);

        removeAll(); // limpa se for reaproveitado
        add(content, BorderLayout.CENTER);

        return this;
    }
}
