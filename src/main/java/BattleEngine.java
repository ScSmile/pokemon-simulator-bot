import PokemonLogic.Pokemon;
import PokemonLogic.PokemonBuilder;
import java.util.Random;

public class BattleEngine {

  public void battle(Pokemon p1, Pokemon p2) {
    p1.stats.resetBValues();
    p2.stats.resetBValues();
    p1.setMoves();
    p2.setMoves();

    SendQueue.enqueue(
        String.format("Battle between <b>%s</b> and <b>%s</b> starts!",
            p1.getName(), p2.getName()));

    int turnCount = 0;
    while (true) {
      Random r = new Random();
      turnCount++;
      SendQueue.enqueue(String.format("Turn %s:", turnCount));

      double speed1 = p1.stats.SPEED.getBattleValue();
      double speed2 = p2.stats.SPEED.getBattleValue();

      Pokemon[] order = (speed1 > speed2) ? new Pokemon[]{p1, p2} :
          (speed2 > speed1) ? new Pokemon[]{p2, p1} :
              (r.nextDouble() >= 0.5) ? new Pokemon[]{p1, p2} : new Pokemon[]{p2, p1};

      Pokemon first = order[0];
      Pokemon second = order[1];

      SendQueue.enqueue(String.join("",
          first.useMove(r.nextInt(4), second)));
      if (isFainted(p1, p2)) {
        break;
      }

      SendQueue.enqueue(String.join("",
          second.useMove(r.nextInt(4), first)));
      if (isFainted(p1, p2)) {
        break;
      }
    }
  }


  private boolean isFainted(Pokemon p1, Pokemon p2) {
    double hp1 = p1.stats.HP.getBattleValue();
    double hp2 = p2.stats.HP.getBattleValue();
    String faintedName;
    if (hp1 > 0 && hp2 > 0) {
      return false;
    } else if (hp1 <= 0 && hp2 <= 0) {
      SendQueue.enqueue("It's double KO!");
    } else {
      faintedName = (hp1 <= 0) ? p1.getName() :
          (hp2 <= 0) ? p2.getName() : "";
      SendQueue.enqueue(String.format("<b>%s</b> is fainted!", faintedName));
    }
    return true;
  }

  public void randomBattle() {
    Random rnd = new Random();
    Pokemon p1 = PokemonBuilder.getPokemon(Integer.toString(rnd.nextInt(809) + 1), 50);
    Pokemon p2 = PokemonBuilder.getPokemon(Integer.toString(rnd.nextInt(809) + 1), 50);

    battle(p1, p2);
  }
}