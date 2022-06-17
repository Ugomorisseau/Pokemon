package net.enovea.pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonExternalAPI {
    private final ObjectMapper objectMapper;

    public PokemonExternalAPI(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<PokemonUrl> getPokemonsUrl() throws IOException {
        URL url = new URL("https://pokeapi.co/api/v2/pokemon-form/?limit=898");
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

    public List<PokemonUrl> getGenerationsUrl() throws IOException {
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

        GenerationsResult generationsResult = objectMapper.readValue(informationGenerationString.toString(), GenerationsResult.class);
        return generationsResult.getResults();
    }

    public List<GenerationId> retrieveGenerationsDetails(List<PokemonUrl> generationInfos){
        return generationInfos.parallelStream()
                .map(result -> {
                    try {
                        var resultBuilderGeneration = new StringBuilder();

                        URL urlGeneration = new URL(result.getUrl());
                        HttpURLConnection conn = (HttpURLConnection) urlGeneration.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();

                        var scannerTmp = new Scanner(urlGeneration.openStream());
                        while (scannerTmp.hasNext()) {
                            resultBuilderGeneration.append(scannerTmp.nextLine());
                        }

                        scannerTmp.close();
                        return objectMapper.readValue(resultBuilderGeneration.toString(), GenerationId.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

    }
}
