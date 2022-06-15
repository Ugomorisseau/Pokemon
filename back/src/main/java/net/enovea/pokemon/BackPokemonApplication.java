package net.enovea.pokemon;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.ObjectInput;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//@SpringBootApplication BIm ca compile car c'est dans commentaire
public class BackPokemonApplication {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static Connection bddConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pokedb", "root", "");
    }

    static void deletePokemonTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String deleteSql = "DELETE FROM pokemons";
        statement.executeUpdate(deleteSql);
    }


    static List<Results> pokemonInfos() throws IOException {
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
            try(Scanner scanner = new Scanner(url.openStream())) {
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
            }
            PokemonForm formResult = OBJECT_MAPPER.readValue(informationString.toString(), PokemonForm.class);
            System.out.println(formResult + "formResult");
            return formResult.getResults();
    }

    static void pokemonPictureAndName() {
        try {

            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append("INSERT INTO pokemons (name, front_default, back_default, nickname, id, type1, type2) VALUES");
            var values = Arrays.asList(formResult.getResults()).parallelStream()
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
                            return OBJECT_MAPPER.readValue(resultBuilder.toString(), PokemonFormId.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }).map(pokemon -> "('" + pokemon.getName() + "', '" +
                            pokemon.getSprites().getFront_default() + "', '" +
                            pokemon.getSprites().getBack_default() + "', '" +
                            pokemon.getName() + "', '" +
                            pokemon.getId() + "', '" +
                            pokemon.getTypes()[0].getType().getName() + "', '" +
                            (pokemon.getTypes().length > 1 ? pokemon.getTypes()[1].getType().getName() : "") + "')")
                    .collect(Collectors.joining(",\n"));

            requestBuilder.append(values);
            System.out.println(requestBuilder.toString());
            statement.executeUpdate(requestBuilder.toString());
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    public static void main(String[] args) {

        try {
            Connection bddConnection = bddConnect();
            deletePokemonTable(bddConnection);
            List<Results> pokemonInfos();

            pokemonPictureAndName();


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

            Generation generation = OBJECT_MAPPER.readValue(informationGenerationString.toString(), Generation.class);
            System.out.println("Liste des générations : " + generation);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
