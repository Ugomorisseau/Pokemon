package net.enovea.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class PokemonExternalAPI {
    private final ObjectMapper objectMapper;

    public PokemonExternalAPI(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<PokemonUrl> getPokemonsUrl() throws IOException {
        URL url = new URL("https://pokeapi.co/api/v2/pokemon-form/?limit=500");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();

        //Verifier la connection
        int responseCode = con.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("Erreur lors de la connection " + responseCode);
        }
        StringBuilder informationString = new StringBuilder();
        try (Scanner scanner = new Scanner(url.openStream())) {
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
        }
        PokemonForm formResult = objectMapper.readValue(informationString.toString(), PokemonForm.class);
        System.out.println(formResult + "formResult");
        return formResult.getResults();
    }

    public Stream<PokemonFormId> retrievePokemonsDetails(List<PokemonUrl> pokemonInfos) {
        return pokemonInfos.parallelStream()
                .map(result -> {
                    try {
                        var resultBuilder = new StringBuilder();

                        URL urlPicture = new URL(result.getUrl());
                        HttpURLConnection conn = (HttpURLConnection) urlPicture.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();

                        var scannerTmp = new Scanner(urlPicture.openStream());
                        while (scannerTmp.hasNext()) {
                            resultBuilder.append(scannerTmp.nextLine());
                        }

                        scannerTmp.close();
                        return objectMapper.readValue(resultBuilder.toString(), PokemonFormId.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    public GenerationsResult retrieveGenerations() throws IOException {
        URL urlGeneration = new URL("https://pokeapi.co/api/v2/generation/");
        HttpURLConnection conn = (HttpURLConnection) urlGeneration.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        StringBuilder informationGenerationString = new StringBuilder();
        Scanner scannerGeneration = new Scanner(urlGeneration.openStream());

        while (scannerGeneration.hasNext()) {
            informationGenerationString.append(scannerGeneration.nextLine());
        }
        scannerGeneration.close();

        return objectMapper.readValue(informationGenerationString.toString(), GenerationsResult.class);
    }
}
