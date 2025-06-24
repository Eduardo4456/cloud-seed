package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PerenualService {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String buscarInformacoesPlanta(String tipo) {
        try {
            String chave = "sk-y1S66824d58e0fa9d10452";
            String q = URLEncoder.encode(tipo, StandardCharsets.UTF_8);

            // 1. Buscar planta por nome
            URL url = new URL("https://perenual.com/api/v2/species-list?key=" + chave + "&q=" + q);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");

            if (c.getResponseCode() != 200) {
                return "❌ Erro ao acessar API Perenual.";
            }

            JsonNode root;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                root = mapper.readTree(reader);
            }

            JsonNode planta = root.path("data").get(0);
            if (planta == null || planta.isMissingNode()) {
                return "❌ Planta não encontrada.";
            }

            int id = planta.path("id").asInt();
            String nome = planta.path("common_name").asText("");
            String nomeCientifico = planta.path("scientific_name").get(0).asText("");
            String ciclo = planta.path("cycle").asText("");
            String tipoPlanta = planta.path("type").asText("");

            StringBuilder sb = new StringBuilder();
            sb.append("🌿 Planta encontrada!\n\n");
            sb.append("📛 Nome comum: ").append(nome).append("\n");
            sb.append("🔬 Nome científico: ").append(nomeCientifico).append("\n");

            // 2. Buscar guia de cuidados
            URL urlCare = new URL("https://perenual.com/api/species-care-guide-list?key=" + chave + "&species_id=" + id);
            HttpURLConnection connCare = (HttpURLConnection) urlCare.openConnection();
            connCare.setRequestMethod("GET");

            if (connCare.getResponseCode() != 200) {
                sb.append("⚠️ Não foi possível carregar guia de cuidados.");
                return sb.toString();
            }

            JsonNode careRoot;
            try (BufferedReader reader2 = new BufferedReader(new InputStreamReader(connCare.getInputStream()))) {
                careRoot = mapper.readTree(reader2);
            }

            JsonNode careData = careRoot.path("data").get(0);
            if (careData != null) {
                JsonNode sections = careData.path("section");
                sb.append("🛠 Guia de Cuidados:\n");
                for (JsonNode s : sections) {
                    String title = s.path("type").asText();
                    String desc = s.path("description").asText();
                    sb.append("• ").append(capitalize(title)).append(": ").append(desc).append("\n");
                }
            }

            return sb.toString();

        } catch (Exception e) {
            return "Erro ao buscar informações: " + e.getMessage();
        }
    }

    private static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
