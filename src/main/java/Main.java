import model.PlantaCadastrada;
import service.ClimaService;
import service.PerenualService;
import util.Serializador;

import java.time.*;
import java.util.*;

public class Main {
    static final String ARQ = "minhas_plantas.dat";
    static List<PlantaCadastrada> minhasPlantas = new ArrayList<>();

    public static void main(String[] args) {
    	Object obj = Serializador.carregar(ARQ);
    	if (obj instanceof List<?>) {
    	    List<?> listaBruta = (List<?>) obj;
    	    List<PlantaCadastrada> listaConvertida = new ArrayList<>();
    	    for (Object o : listaBruta) {
    	        if (o instanceof PlantaCadastrada p) {
    	            listaConvertida.add(p);
    	        }
    	    }
    	    minhasPlantas = listaConvertida;
    	} else {
    	    minhasPlantas = new ArrayList<>();
    	}

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Cadastrar planta\n2. Listar\n3. Rega\n4. Sugestões\n5. Monitorar\n6. Sair");
            switch (sc.nextLine()) {
                case "1" -> cadastrar(sc);
                case "2" -> minhasPlantas.forEach(System.out::println);
                case "3" -> regar(sc);
                case "4" -> sugestao(sc);
                case "5" -> monitorar();
                case "6" -> {
                    Serializador.salvar(ARQ, minhasPlantas);
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    static void cadastrar(Scanner sc) {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Tipo: "); String tipo = sc.nextLine();
        System.out.print("Cidade: "); String cidade = sc.nextLine();
        System.out.print("Regas semanais: "); int freq = Integer.parseInt(sc.nextLine());
        Set<DayOfWeek> dias = EnumSet.noneOf(DayOfWeek.class);
        System.out.print("Dias (ex: 1,3,5): ");
        for (String d : sc.nextLine().split(",")) dias.add(DayOfWeek.of(Integer.parseInt(d.trim())));
        minhasPlantas.add(new PlantaCadastrada(nome, tipo, cidade, freq, dias));
    }

    static void regar(Scanner sc) {
        for (int i = 0; i < minhasPlantas.size(); i++)
            System.out.println((i+1) + ". " + minhasPlantas.get(i).nome);
        System.out.print("Qual regar: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        minhasPlantas.get(idx).regar();
    }

    static void sugestao(Scanner sc) {
        System.out.print("Tipo da planta: ");
        PerenualService.buscarInformacoesPlanta(sc.nextLine());
    }

    static void monitorar() {
        for (PlantaCadastrada p : minhasPlantas) {
            System.out.println(p);
            try {
                double temp = ClimaService.obterTemperaturaAtual(p.cidade);
                System.out.printf("🌡 Temperatura atual em %s: %.1fºC\n", p.cidade, temp);
            } catch (Exception e) {
                System.out.println("Erro ao obter clima: " + e.getMessage());
            }
        }
    }
}
