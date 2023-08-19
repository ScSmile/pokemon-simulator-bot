package PokemonLogic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PokemonBuilder {

  private static final Logger log = LogManager.getLogger();

  public static Pokemon getPokemon(String input, int level) {
    final File directory = new File("src/main/resources/PokeDex");

    Pokemon pokemon = null;
    final ObjectMapper objectMapper = new ObjectMapper();
    File pokemonDataFile = getPokemonFile(directory, input);

    try (FileReader fr = new FileReader(pokemonDataFile)) {
      pokemon = objectMapper.readValue(fr, Pokemon.class);
      pokemon.updateLevel(level);
    } catch (Exception e) {
      log.error("Data parsing error: " + e.getMessage());
    }

    return pokemon;
  }

  private static File getPokemonFile(File directory, String input) {
    FilenameFilter filter;
    if (input.matches("\\d+")) {
      filter = (dir, fileName) -> fileName.startsWith(input);
    } else {
      filter = (dir, fileName) -> fileName.matches(".*" + input + "\\.json");
    }
    return getPokemonFile(filter, directory);
  }

  private static File getPokemonFile(FilenameFilter filter, File directory) {
    File[] files = directory.listFiles(filter);
    if (files == null) {
      log.error("Wrong PokemonLogic.Pokemon files directory name/IOException");
      return null;
    } else if (files.length == 0) {
      log.error("Incorrect PokemonLogic.Pokemon name/ID.");
      return null;
    }
    return files[0];
  }

  public static DamageMove[] getMoveSet(Type type, int num) {
    if (num > 4 || num < 1) {
      throw new IllegalArgumentException("Incorrect number of moves");
    }

    final ObjectMapper objectMapper = new ObjectMapper();
    final File directory = new File(String.format("src/main/resources/MoveDex/%s", type.name()));
    File[] files = directory.listFiles();

    if (files == null) {
      throw new IllegalArgumentException(
          "Move files getting error.(Wrong directory name/IOException)");
    }

    DamageMove[] moveSet = new DamageMove[Math.min(files.length, num)];

    HashSet<Integer> indexes = new HashSet<>();
    Random r = new Random();
    while (indexes.size() < Math.min(files.length, num)) {
      indexes.add(r.nextInt(files.length));
    }
    int len = indexes.size();
    Integer[] idxArr = indexes.toArray(new Integer[len]);

    for (int i = 0; i < len; i++) {
      try (FileReader fr = new FileReader(files[idxArr[i]])) {
        moveSet[i] = objectMapper.readValue(fr, DamageMove.class);
      } catch (Exception e) {
        log.error("Parsing error, try again. Error: " + e);
        return null;
      }
    }
    return moveSet;
  }
}