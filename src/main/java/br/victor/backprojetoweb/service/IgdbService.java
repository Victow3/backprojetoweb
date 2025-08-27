package br.victor.backprojetoweb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.victor.backprojetoweb.dto.GameDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class IgdbService {

    @Value("${igdb.client-id}")
    private String clientId;

    @Value("${igdb.client-secret}")
    private String clientSecret;

    private String accessToken;

    private void autenticar() throws Exception {
        String urlStr = "https://id.twitch.tv/oauth2/token"
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) response.append(inputLine);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.toString());
            this.accessToken = json.get("access_token").asText();
        }
    }

    private List<GameDTO> chamarIGDB(String query) throws Exception {
        if (accessToken == null) autenticar();

        URL url = new URL("https://api.igdb.com/v4/games");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Client-ID", clientId);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(query.getBytes(StandardCharsets.UTF_8));
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) response.append(line.trim());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode array = mapper.readTree(response.toString());

        List<GameDTO> jogos = new ArrayList<>();
        for (JsonNode node : array) {
            String name = node.has("name") ? node.get("name").asText() : "Sem nome";
            String coverUrl = null;
            if (node.has("cover") && node.get("cover").has("url")) {
                coverUrl = "https:" + node.get("cover").get("url").asText();
            }
            LocalDate releaseDate = null;
            if (node.has("first_release_date")) {
                long epoch = node.get("first_release_date").asLong();
                releaseDate = Instant.ofEpochSecond(epoch).atZone(ZoneId.systemDefault()).toLocalDate();
            }
            jogos.add(new GameDTO(name, coverUrl, releaseDate));
        }
        return jogos;
    }

    // Lista base (com paginação)
    public List<GameDTO> buscarJogos(int limit, int offset) {
        try {
            String query = String.format(
                    "fields name, first_release_date, cover.url;" +
                            " sort first_release_date desc;" +
                            " limit %d; offset %d;", limit, offset
            );
            return chamarIGDB(query);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Busca por nome (com paginação) — usa a query `search "..."` da IGDB
    public List<GameDTO> buscarJogosPorNome(String nome, int limit, int offset) {
        try {
            // Escapa aspas para não quebrar a query
            String termo = nome.replace("\"", "\\\"");
            String query = String.format(
                    "search \"%s\";" +
                            " fields name, first_release_date, cover.url;" +
                            " limit %d; offset %d;", termo, limit, offset
            );
            return chamarIGDB(query);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
