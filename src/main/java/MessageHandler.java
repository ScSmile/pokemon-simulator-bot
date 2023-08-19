import PokemonLogic.Pokemon;
import PokemonLogic.PokemonBuilder;
import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters2;
import java.util.Arrays;

public class MessageHandler {

  private final StateMachine<State, Command> stateMachine;

  public State getState() {
    return stateMachine.getState();
  }

  MessageHandler() {
    stateMachine = new StateMachine<>(State.INIT, BotFSM.createConfig());
  }

  MessageHandler(State state) {
    stateMachine = new StateMachine<>(state, BotFSM.createConfig());
  }

  public void operate(ParsedMessage message) {
    Command command = message.command;
    if (!stateMachine.canFire(command)) {
      command = Command.NON_COMMAND;
    }
    if (stateMachine.getState() == State.PKMN_INPUT
        && message.command != Command.BACK) {
      String[] pkmnInputs = message.text.split(" ");
      if (pkmnInputs.length != 2) {
        command = Command.NON_COMMAND;
        stateMachine.fire(command);
        return;
      }
      Pokemon[] pokemon = new Pokemon[2];

      for (String pkmnInput :
          pkmnInputs) {
        pokemon[Arrays.asList(pkmnInputs).indexOf(pkmnInput)]
            = PokemonBuilder.getPokemon(pkmnInput, 50);
      }
      if (Arrays.asList(pokemon).contains(null)) {
        command = Command.NON_COMMAND;
      } else {
        TriggerWithParameters2<Pokemon, Pokemon, Command> twp2 = new TriggerWithParameters2<>(
            Command.CORRECT_INPUT, Pokemon.class, Pokemon.class);
        stateMachine.fire(twp2, pokemon[0], pokemon[1]);
        return;
      }
    }
    stateMachine.fire(command);
  }
}
