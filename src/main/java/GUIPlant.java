import model.PlantaCadastrada;
import model.PlantaJson;
import service.ClimaService;
import service.PerenualService;
import util.Serializador;

import adapter.LocalDateAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class GUIPlant extends JFrame {
    private static final String ARQ = "minhas_plantas.dat";
    private DefaultListModel<PlantaCadastrada> modeloLista = new DefaultListModel<>();
    private JList<PlantaCadastrada> listaPlantas = new JList<>(modeloLista);

    public GUIPlant() {
        super("🌱 Gerenciador de Plantas");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        carregarPlantas();

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnRemover = new JButton("Remover");
        JButton btnRegar = new JButton("Regar");
        JButton btnSugestao = new JButton("Sugestão");
        JButton btnMonitorar = new JButton("Monitorar");
        JButton btnRegistroRega = new JButton("Regas");
        JButton btnSalvarSair = new JButton("Salvar e Sair");

        btnAdicionar.addActionListener(e -> adicionarPlanta());
        btnRemover.addActionListener(e -> removerPlanta());
        btnRegar.addActionListener(e -> regarPlanta());
        btnSugestao.addActionListener(e -> sugestaoPlanta());
        btnMonitorar.addActionListener(e -> monitorarPlantas());
        btnRegistroRega.addActionListener(e -> mostrarRegistroRega());
        btnSalvarSair.addActionListener(e -> {
            salvarPlantas();
            System.exit(0);
        });

        JPanel painelBotoes = new JPanel(new GridLayout(1, 6));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnRegar);
        painelBotoes.add(btnSugestao);
        painelBotoes.add(btnMonitorar);
        painelBotoes.add(btnRegistroRega);
        painelBotoes.add(btnSalvarSair);

        PainelComImagem painelPrincipal = new PainelComImagem("/img/2-removebg-preview2.png");
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.add(new JScrollPane(listaPlantas), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        ImageIcon icon = new ImageIcon("/home/eduardo/eclipse-workspace/com.project.cloudseed/src/main/java/img/2-removebg-preview2.png");
        if (icon.getIconWidth() == -1) {
            System.out.println("Imagem não encontrada!");
        }

        setContentPane(painelPrincipal);

        setVisible(true);
        
        Font fonte = new Font("SansSerif", Font.PLAIN, 14);
        listaPlantas.setFont(fonte);
        btnAdicionar.setFont(fonte); 
        btnRemover.setFont(fonte);
        btnRegar.setFont(fonte);
        btnSugestao.setFont(fonte);
        btnMonitorar.setFont(fonte);
        btnRegistroRega.setFont(fonte);
        btnSalvarSair.setFont(fonte);
        
        listaPlantas.setOpaque(false);
        ((JViewport)((JScrollPane)listaPlantas.getParent().getParent()).getViewport()).setOpaque(false);
        ((JScrollPane)listaPlantas.getParent().getParent()).setOpaque(false);
        
        painelBotoes.setOpaque(false);
        
        JButton[] botoes = {btnAdicionar, btnRemover, btnRegar, btnSugestao, btnMonitorar, btnRegistroRega, btnSalvarSair};
        for (JButton botao : botoes) {
            estilizarBotao(botao);
        }
        
        listaPlantas.setCellRenderer(new PlantaRenderer());
    }	
    
    private void estilizarBotao(JButton botao) {
        Color verde = new Color(58, 141, 58); // Verde moderno
        Color verdeHover = new Color(59, 174, 96); // Verde mais escuro

        botao.setBackground(verde);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding
        botao.setFont(new Font("SansSerif", Font.BOLD, 14));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito hover
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(verdeHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(verde);
            }
        });
       
    }


    private void carregarPlantas() {
        Object obj = Serializador.carregar(ARQ);
        if (obj instanceof List<?> listaBruta) {
            for (Object o : listaBruta) {
                if (o instanceof PlantaCadastrada p) {
                    modeloLista.addElement(p);
                }
            }
        }
    }

    private void salvarPlantas() {
        List<PlantaCadastrada> plantas = Collections.list(modeloLista.elements());
        Serializador.salvar(ARQ, plantas);
    }

    private void adicionarPlanta() {
        JTextField nome = new JTextField();
        JTextField tipo = new JTextField();
        JTextField cidade = new JTextField();
        JTextField freq = new JTextField();
        JTextField dias = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:")); panel.add(nome);
        panel.add(new JLabel("Tipo:")); panel.add(tipo);
        panel.add(new JLabel("Cidade:")); panel.add(cidade);
        panel.add(new JLabel("Regas semanais:")); panel.add(freq);
        panel.add(new JLabel("Dias da semana (ex: 1,3,5):")); panel.add(dias);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cadastrar Planta", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Set<DayOfWeek> diasSet = EnumSet.noneOf(DayOfWeek.class);
                for (String d : dias.getText().split(",")) {
                    diasSet.add(DayOfWeek.of(Integer.parseInt(d.trim())));
                }
                PlantaCadastrada nova = new PlantaCadastrada(nome.getText(), tipo.getText(), cidade.getText(), Integer.parseInt(freq.getText()), diasSet);
                modeloLista.addElement(nova);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar planta: " + e.getMessage());
            }
        }
    }

    private void removerPlanta() {
        PlantaCadastrada p = listaPlantas.getSelectedValue();
        if (p != null) {
            modeloLista.removeElement(p);
        }
    }

    private void regarPlanta() {
        PlantaCadastrada p = listaPlantas.getSelectedValue();
        if (p != null) {
            p.regar();
            JOptionPane.showMessageDialog(this, "Planta '" + p.nome + "' regada com sucesso!");
        }
    }

    private void sugestaoPlanta() {
        String tipo = JOptionPane.showInputDialog(this, "Tipo da planta:");
        if (tipo != null && !tipo.isEmpty()) {
            String info = PerenualService.buscarInformacoesPlanta(tipo);

            JTextArea area = new JTextArea(info);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(new Font("SansSerif", Font.PLAIN, 14));
            area.setBackground(new Color(245, 255, 245));

            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "Sugestão de Planta", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void monitorarPlantas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < modeloLista.getSize(); i++) {
            PlantaCadastrada planta = modeloLista.get(i);
            sb.append("\n🔍 ").append(planta.nome).append("\n");

            try {
                double temp = ClimaService.obterTemperaturaAtual(planta.cidade);
                sb.append("🌡 ").append(planta.cidade).append(": ").append(temp).append("°C\n");

                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
                java.lang.reflect.Type tipoLista = new TypeToken<List<PlantaJson>>() {}.getType();
                FileReader fr = new FileReader("plantas100.json");
                List<PlantaJson> lista = gson.fromJson(fr, tipoLista);

                boolean encontrada = false;
                for (PlantaJson pj : lista) {
                    if (pj.nome_popular.equalsIgnoreCase(planta.tipo)) {
                        encontrada = true;
                        double[] faixa = pj.getFaixaTemperatura();
                        if (temp >= faixa[0] && temp <= faixa[1]) {
                            sb.append("✅ Dentro da faixa ideal (").append(faixa[0]).append("°C - ").append(faixa[1]).append("°C)\n");
                        } else {
                            sb.append("⚠️ Fora da faixa ideal (").append(faixa[0]).append("°C - ").append(faixa[1]).append("°C)\n");
                        }
                        break;
                    }
                }

                if (!encontrada) {
                    sb.append("❌ Tipo não encontrado.\n");
                }
            } catch (Exception e) {
                sb.append("Erro ao monitorar planta: ").append(e.getMessage()).append("\n");
            }
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Monitoramento", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarRegistroRega() {
        PlantaCadastrada planta = listaPlantas.getSelectedValue();

        if (planta == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma planta primeiro.");
            return;
        }

        JFrame frame = new JFrame("Histórico de Regas - " + planta.nome);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);

        DefaultListModel<String> model = new DefaultListModel<>();
        List<LocalDate> historico = planta.historicoRega;
        
        if (historico.isEmpty()) {
            model.addElement("Nenhuma rega registrada.");
        } else {
            for (int i = 0; i < historico.size(); i++) {
                LocalDate data = historico.get(i);
                model.addElement("Rega #" + (i + 1) + " - " + data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }

        JList<String> lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);

        JLabel titulo = new JLabel("Histórico de Regas da Planta: " + planta.nome, JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.setLayout(new BorderLayout());
        frame.add(titulo, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIPlant::new);
    }
}
