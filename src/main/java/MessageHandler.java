import pokemonLogic.Pokemon;
import pokemonLogic.PokemonBuilder;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters2;
import java.util.Arrays;
import java.util.Locale;

public class MessageHandler {

  private final StateMachine<State, Command> stateMachine;

  public MessageHandler() {
    stateMachine = new StateMachine<>(State.INIT, BotFSM.createConfig());
  }

  public void operate(ParsedMessage message) {
    Command command = Command.NON_COMMAND;

    if (stateMachine.canFire(message.getCommand())) {
      command = message.getCommand();
    }
    if (stateMachine.getState() == State.PKMN_INPUT
        && message.getCommand() != Command.BACK) {
      String[] pkmnInputs = message.getText().split(" ");

      if (pkmnInputs.length == 2) {
        Pokemon[] pokemon = new Pokemon[2];

        for (int i = 0; i < 2; i++) {
          pokemon[i] = PokemonBuilder.getPokemon(
              pkmnInputs[i].toLowerCase(Locale.ROOT), 50);
        }

        if (!Arrays.asList(pokemon).contains(null)) {
          TriggerWithParameters2<Pokemon, Pokemon, Command> twp2 = new TriggerWithParameters2<>(
              Command.CORRECT_INPUT, Pokemon.class, Pokemon.class);
          stateMachine.fire(twp2, pokemon[0], pokemon[1]);
          return;
        }
      }
    }
    stateMachine.fire(command);
  }
}
