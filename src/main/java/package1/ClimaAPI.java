package package1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClimaAPI {
    // Faixa ideal de temperatura/umidade
    static class FaixaIdeal implements Serializable {
        double tempMin, tempMax;
        int umidMin, umidMax;
        FaixaIdeal(double tmin, double tmax, int umin, int umax) {
            tempMin = tmin; tempMax = tmax; umidMin = umin; umidMax = umax;
        }
    }

    // Classe de entidade planta cadastrada
    static class PlantaCadastrada implements Serializable {
        String nome, tipo, cidade;
        List<LocalDate> historicoRega = new ArrayList<>();
        int freqRegaSemanal;
        Set<DayOfWeek> diasRega;
        PlantaCadastrada(String nome, String tipo, String cidade, int freq, Set<DayOfWeek> dias) {
            this.nome = nome; this.tipo = tipo; this.cidade = cidade;
            this.freqRegaSemanal = freq; this.diasRega = dias;
        }
        void regar() { historicoRega.add(LocalDate.now()); }
        LocalDate getUltimaRega() {
            return historicoRega.isEmpty() ? null : historicoRega.get(historicoRega.size()-1);
        }
        boolean precisaRegar() {
            LocalDate hoje = LocalDate.now();
            if (getUltimaRega() != null && getUltimaRega().isEqual(hoje)) return false;
            long nasUltimas7 = historicoRega.stream()
                .filter(d -> d.isAfter(hoje.minusDays(7))).count();
            return nasUltimas7 < freqRegaSemanal;
        }
        boolean hojeEDiaDeRega() {
            return diasRega.contains(LocalDate.now().getDayOfWeek());
        }
        int diasAteProximaRega() {
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

    static Map<String,FaixaIdeal> plantasBase = new HashMap<>();
    static List<PlantaCadastrada> minhasPlantas = new ArrayList<>();
    static final String ARQ = "minhas_plantas.dat";
    
    public static void main(String[] args) {
        inicializarPlantasBase();
        carregarPlantas();
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;
        while (!sair) {
            List<PlantaCadastrada> praRegar = minhasPlantas.stream()
                .filter(p -> p.hojeEDiaDeRega() && p.precisaRegar()).collect(Collectors.toList());
            if (!praRegar.isEmpty()) {
                System.out.println("\n⚠️ Estas plantas precisam ser regadas hoje:");
                praRegar.forEach(p -> System.out.println("- " + p.nome + " ("+p.tipo+")"));
            }
            System.out.println("""
                \n🌿 Monitoramento de Plantas 🌿
                1. Cadastrar planta
                2. Listar plantas
                3. Remover planta
                4. Monitorar todas
                5. Buscar sugestões Perenual
                6. Registrar rega
                7. Salvar e sair
                Escolha:""");
            String opc = scanner.nextLine().trim();
            switch (opc) {
                case "1" -> cadastrarPlanta(scanner);
                case "2" -> listarPlantas();
                case "3" -> removerPlanta(scanner);
                case "4" -> monitorarTodas();
                case "5" -> buscarSugestoesMenu(scanner);
                case "6" -> regarPlanta(scanner);
                case "7" -> { salvarPlantas(); sair=true; System.out.println("✅ Salvo. Até logo!"); }
                default -> System.out.println("❌ Opção inválida.");
            }
        }
        scanner.close();
    }

    static void cadastrarPlanta(Scanner sc) {
        System.out.print("Nome da planta: "); String nome = sc.nextLine().trim();
        System.out.print("Tipo (ex: magnolia): "); String tipo = sc.nextLine().toLowerCase().trim();
       /* if (!plantasBase.containsKey(tipo)) {
            System.out.println("❌ Tipo desconhecido. Tipos: " + plantasBase.keySet());
            return;
        }*/
        System.out.print("Cidade: "); String cidade = sc.nextLine().trim();
        int freq;
        while (true) {
            System.out.print("Regas por semana (0–7): ");
            try { freq = Integer.parseInt(sc.nextLine()); if (freq>=0&&freq<=7) break; }
            catch(Exception e){}
            System.out.println("❌ Valor inválido.");
        }
        Set<DayOfWeek> dias = new HashSet<>();
        System.out.println("📅 Dias de rega (ex: segunda, quarta ou 1,3):");
        System.out.print("Dias: ");
        String in = sc.nextLine().toLowerCase().trim();
        for (String parte : in.split(",")) {
        	 parte = parte.trim();
             DayOfWeek dia = null;

             switch (parte) {
                 case "1":
                 case "segunda":
                     dia = DayOfWeek.MONDAY;
                     break;
                 case "2":
                 case "terça":
                     dia = DayOfWeek.TUESDAY;
                     break;
                 case "3":
                 case "quarta":
                     dia = DayOfWeek.WEDNESDAY;
                     break;
                 case "4":
                 case "quinta":
                     dia = DayOfWeek.THURSDAY;
                     break;
                 case "5":
                 case "sexta":
                     dia = DayOfWeek.FRIDAY;
                     break;
                 case "6":
                 case "sábado":
                     dia = DayOfWeek.SATURDAY;
                     break;
                 case "7":
                 case "domingo":
                     dia = DayOfWeek.SUNDAY;
                     break;
                 default:
                     System.out.println("❌ Dia inválido: " + parte);
             }

             if (dia != null) dias.add(dia);
        }
        minhasPlantas.add(new PlantaCadastrada(nome,tipo,cidade,freq,dias));
        System.out.println("✅ Planta cadastrada!");
    }

    static void listarPlantas() {
        if (minhasPlantas.isEmpty()) {
            System.out.println("📭 Nenhuma planta cadastrada.");
            return;
        }
        for (int i=0;i<minhasPlantas.size();i++) {
            System.out.println((i+1)+". "+minhasPlantas.get(i));
        }
    }

    static void removerPlanta(Scanner sc) {
        listarPlantas();
        if (minhasPlantas.isEmpty()) return;
        System.out.print("Número da planta a remover: ");
        try {
            int idx = Integer.parseInt(sc.nextLine())-1;
            PlantaCadastrada p = minhasPlantas.remove(idx);
            System.out.println("🗑 Removida: "+p.nome);
        } catch(Exception e){ System.out.println("❌ Índice inválido."); }
    }

    static void monitorarTodas() {
        if (minhasPlantas.isEmpty()) {
            System.out.println("⚠️ Sem plantas cadastradas.");
            return;
        }
        for (PlantaCadastrada p : minhasPlantas) {
            monitorarPlanta(p);
            System.out.println("-----------------------------------");
        }
    }

    static void monitorarPlanta(PlantaCadastrada p) {
        System.out.println(p);
        // ... aqui poderia chamar clima, validar faixas
        System.out.println("📅 Próxima rega em: "+p.diasAteProximaRega()+" dias");
    }

    static void buscarSugestoesMenu(Scanner sc) {
        listarPlantas();
        if (minhasPlantas.isEmpty()) return;
        System.out.print("Número da planta p/ sugestões: ");
        try {
            int idx = Integer.parseInt(sc.nextLine())-1;
            buscarSugestoesDaPerenual(minhasPlantas.get(idx).tipo);
        } catch(Exception e){ System.out.println("❌ Índice inválido."); }
    }

    static void buscarSugestoesDaPerenual(String tipo) {
        System.out.println("\n🔎 Buscando sugestões para: " + tipo);
        try {
            String chave = "sk-y1S66824d58e0fa9d10452"; // substitua
            String q = URLEncoder.encode(tipo, StandardCharsets.UTF_8);
            URL url = new URL("https://perenual.com/api/species-list?key=" + chave + "&q=" + q);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            if (c.getResponseCode() != 200) {
                System.out.println("❌ Erro ("+c.getResponseCode()+") ao consultar Perenual");
                return;
            }
            BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
            String json = r.lines().collect(Collectors.joining());
            r.close();
            String name = extrair(json, "\"common_name\":\"", "\"");
            String water = extrair(json, "\"watering\":\"", "\"");
            String sun = extrair(json, "\"sunlight\":[\"", "\":");
            System.out.println("📘 Perenual - Nome comum: " + name);
            System.out.println("💧 Rega: " + water);
            System.out.println("☀️ Luz solar: " + sun);
            System.out.println("Resposta da API: " + json);
        } catch(Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
    static String extrair(String t, String ini, String fim) {
        int i = t.indexOf(ini); if (i<0) return "—";
        int j = t.indexOf(fim, i + ini.length());
        return j<0 ? t.substring(i+ini.length()) : t.substring(i+ini.length(), j);
    }

    static void regarPlanta(Scanner sc) {
        listarPlantas();
        if (minhasPlantas.isEmpty()) return;
        System.out.print("Número da planta regada: ");
        try {
            int idx = Integer.parseInt(sc.nextLine())-1;
            PlantaCadastrada p = minhasPlantas.get(idx);
            p.regar();
            System.out.println("💧 Regada hoje: " + p.nome);
        } catch(Exception e){ System.out.println("❌ Índice inválido."); }
    }

    static void salvarPlantas() {
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            o.writeObject(minhasPlantas);
        } catch(Exception e) { System.out.println("❌ Falha ao salvar."); }
    }

    @SuppressWarnings("unchecked")
    static void carregarPlantas() {
        try(ObjectInputStream o = new ObjectInputStream(new FileInputStream(ARQ))) {
            minhasPlantas = (List<PlantaCadastrada>) o.readObject();
        } catch(Exception ignored) {}
    }

    static void inicializarPlantasBase() {
        plantasBase.put("cacto", new FaixaIdeal(15,28,20,40));
        plantasBase.put("babosa", new FaixaIdeal(18,26,30,50));
        plantasBase.put("bromélia", new FaixaIdeal(20,30,40,60));
        plantasBase.put("orquídea", new FaixaIdeal(18,28,50,70));
        plantasBase.put("samambaia", new FaixaIdeal(16,24,60,80));
        plantasBase.put("ficus", new FaixaIdeal(18,28,40,60));
    }
}
