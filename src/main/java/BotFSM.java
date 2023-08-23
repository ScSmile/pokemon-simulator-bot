import pokemonLogic.Pokemon;
import com.github.oxo42.stateless4j.StateMachineConfig;

public class BotFSM {

  public static StateMachineConfig<State, Command> createConfig() {
    StateMachineConfig<State, Command> config = new StateMachineConfig<>();

    config.configure(State.INIT)
        .permit(Command.START, State.START)
        .permit(Command.HELP, State.HELP)
        .onEntry(Response::init)
        .permitReentry(Command.NON_COMMAND);
    config.configure(State.START)
        .permit(Command.RANDOM, State.BATTLE)
        .permit(Command.MANUAL, State.PKMN_INPUT)
        .permit(Command.BACK, State.INIT)
        .onEntryFrom(Command.NON_COMMAND, Response::wrongInput)
        .onEntry(Response::start);
    config.configure(State.HELP)
        .permit(Command.BACK, State.INIT)
        .onEntryFrom(Command.HELP, Response::help);
    config.configure(State.PKMN_INPUT)
        .permit(Command.BACK, State.START)
        .permitReentry(Command.NON_COMMAND)
        .permit(Command.CORRECT_INPUT, State.BATTLE)
        .onEntryFrom(Command.NON_COMMAND, Response::wrongNameInput)
        .onEntryFrom(Command.MANUAL, Response::input);
    config.configure(State.BATTLE)
        .permit(Command.RESTART, State.START)
        .onEntryFrom(Command.RANDOM, Response::randomBattle)
        .onEntryFrom(
            config.setTriggerParameters(Command.CORRECT_INPUT, Pokemon.class, Pokemon.class),
            Response::Battle)
        .onEntry(Response::restart);

    for (State state :
        State.values()) {
      if (config.getRepresentation(state) != null &&
          config.getRepresentation(state).canHandle(Command.NON_COMMAND)) {
        continue;
      }
      config.configure(state)
          .permitReentry(Command.NON_COMMAND)
          .onEntryFrom(Command.NON_COMMAND, Response::wrongInput);
    }

    return config;
  }
}
