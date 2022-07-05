package net.enovea.pokemon.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.enovea.pokemon.api.dto.*;
import net.enovea.pokemon.domain.Generation;
import net.enovea.pokemon.domain.Pokemon;
import net.enovea.pokemon.domain.PokemonAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokemonExternalAPI implements PokemonAPI {
    private final ObjectMapper objectMapper;

    public PokemonExternalAPI(ObjectMapper objectMapper) {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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

    public Stream<PokemonFormId> retrievePokemonsDetails(List<PokemonUrl> pokemonUrl) {
        return pokemonUrl.parallelStream()
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
                        var innerResult = objectMapper.readValue(resultBuilder.toString(), PokemonFormId.class);
                        innerResult.setName(innerResult.getPokemon().getName());
                        return innerResult;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                });
    }

    public Pokemon setPokemon(PokemonFormId pokemonFormId) {
        Pokemon pokemon = new Pokemon();

        if (pokemonFormId != null) {
            pokemon.setAvatars(getFrontDefaultPicture(pokemonFormId));
            pokemon.setId(pokemonFormId.getId());
            pokemon.setName(pokemonFormId.getName());
            pokemon.setTypes(getTypeOfPokemon(pokemonFormId));
            pokemon.setAttack(10);
            pokemon.setHp(10);
        }
        return pokemon;
    }

    public List<Pokemon> setPokemons(List<PokemonFormId> pokemonFormId) {
        return pokemonFormId.parallelStream()
                .map(this::setPokemon)
                .collect(Collectors.toList());
    }

    private String[] getTypeOfPokemon(PokemonFormId pokemonFormId) {
    return Arrays.stream(pokemonFormId.getTypes()).map(Types::getType).map(Type::getName).toArray(String[]::new);
    }

    private String getFrontDefaultPicture(PokemonFormId pokemonFormId) {
        return pokemonFormId.getSprites().getFront_default();
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

    public List<GenerationId> retrieveGenerationsDetails(List<PokemonUrl> generationUrl) {
        return generationUrl.parallelStream()
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

    public Generation setGeneration(GenerationId generationId) {

        Generation generation = new Generation();

        generation.setId(generationId.getId());
        generation.setName(generationId.getName());
        generation.setPokemons(getPokemonsNameByGeneration(generationId));

        return generation;
    }

    public List<Generation> setGenerations(List<GenerationId> generationId) {
        return generationId.parallelStream()
                .map(this::setGeneration)
                .collect(Collectors.toList());
    }

    private List<String> getPokemonsNameByGeneration(GenerationId generationId) {
        return generationId.getPokemon_species().stream().map(PokemonSpecies::getName).toList();

    }

    private static void defineGenerationToItsPokemons(List<Pokemon> result, Generation generation) {
        generation.getPokemons()
                .forEach(pokeSpecies -> definePokemonGeneration(result, generation, pokeSpecies));
    }

    private static void definePokemonGeneration(List<Pokemon> result, Generation generationId, String pokeSpecies) {
        result.stream()
                .filter(poke -> {
                    return poke.getName().equals(pokeSpecies);
                })
                .forEach(poke -> poke.setGeneration_id(generationId.getId()));
    }

    public Pokemon findPokemonGeneration(Pokemon pokemon, List<Generation> generationIdList) {
        for (Generation generationId : generationIdList) {
            for ( String pokemonSpecies : generationId.getPokemons()) {
                if (pokemonSpecies.equals(pokemon.getName())) {
                    pokemon.setGeneration_id(generationId.getId());
                    return pokemon;
                }
            }
        }
        return pokemon;
    }

    public List<Pokemon> getPokemons(List<Generation> generationIds) throws IOException {

        List<PokemonUrl> pokemonUrl = getPokemonsUrl();
        Stream<PokemonFormId> pokemonFormId = retrievePokemonsDetails(pokemonUrl);
        List<Pokemon> pokemons = setPokemons(pokemonFormId.toList())
                .stream()
                .map(pokemon -> findPokemonGeneration(pokemon, generationIds)).toList();
        generationIds.parallelStream()
                .forEach(generation -> defineGenerationToItsPokemons(pokemons, generation));
        return pokemons;
    }

    @Override
    public List<Generation> getGenerations() throws IOException {

        List<PokemonUrl> generationInfos = getGenerationsUrl();
        List<GenerationId> generationId = retrieveGenerationsDetails(generationInfos);
        return setGenerations(generationId);
    }
}
