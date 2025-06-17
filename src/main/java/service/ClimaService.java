package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClimaService {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static double obterTemperaturaAtual(String cidade) throws Exception {
        String apiKey = "a3b12725b9d74ec5ad9190839250806";
        String endpoint = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + cidade;
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            JsonNode root = mapper.readTree(reader);
            return root.path("current").path("temp_c").asDouble();
        }
    }
}