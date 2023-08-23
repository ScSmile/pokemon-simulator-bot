import pokemonLogic.Calculator;
import pokemonLogic.Move;
import pokemonLogic.Pokemon;
import pokemonLogic.PokemonBuilder;
import pokemonLogic.Stat;
import pokemonLogic.Type;
import java.util.ArrayList;
import java.util.Random;

public class BattleEngine {

  public void battle(Pokemon p1, Pokemon p2) {
    p1.stats.setBattleStats(p1.getLevel());
    p2.stats.setBattleStats(p2.getLevel());
    p1.setMoves();
    p2.setMoves();

    SendHandler.addMessage(
        String.format("Battle between <b>%s</b> and <b>%s</b> starts!",
            p1.getName(), p2.getName()));

    Random r = new Random();
    int turnCount = 0;
    boolean isFainted = false;
    do {
      turnCount++;
      SendHandler.addMessage(String.format("Turn %s:", turnCount));

      double speed1 = p1.stats.get(Stat.SPEED);
      double speed2 = p2.stats.get(Stat.SPEED);

      Pokemon[] order = (speed1 > speed2) ? new Pokemon[]{p1, p2} :
          (speed2 > speed1) ? new Pokemon[]{p2, p1} :
              (r.nextDouble() >= 0.5) ? new Pokemon[]{p1, p2} : new Pokemon[]{p2, p1};

      for (int i = 0; i < 2; i++) {
        Pokemon user = order[i];
        Pokemon target = order[Math.abs(i - 1)];

        SendHandler.addMessage(String.join("",
            useMove(user,
                selectRandomMove(user, r),
                target)));
        if (isFainted(user, target)) {
          isFainted = true;
          break;
        }
      }
    } while (!isFainted);
  }

  public void randomBattle() {
    Random rnd = new Random();
    Pokemon p1 = PokemonBuilder.getPokemon(Integer.toString(rnd.nextInt(809) + 1), 50);
    Pokemon p2 = PokemonBuilder.getPokemon(Integer.toString(rnd.nextInt(809) + 1), 50);

    battle(p1, p2);
  }

  private boolean isFainted(Pokemon p1, Pokemon p2) {
    double hp1 = p1.stats.get(Stat.HP);
    double hp2 = p2.stats.get(Stat.HP);
    String faintedName;

    if (hp1 > 0 && hp2 > 0) {
      return false;
    } else if (hp1 <= 0 && hp2 <= 0) {
      SendHandler.addMessage("It's double KO!");
    } else {
      faintedName = hp1 <= 0
          ? p1.getName()
          : p2.getName();
      SendHandler.addMessage(String.format("<b>%s</b> is fainted!", faintedName));
    }
    return true;
  }

  private Move selectRandomMove(Pokemon user, Random r){
    if (!user.hasPPLeft()) {
      SendHandler.addMessage(
          String.format("%s have no PP's left!", user.getName()));
      return Move.getStruggle();
    }

    Move move;
    do {
      move = user.getMoves()[r.nextInt(user.getMovesCount())];
      if (move.getCurrentPP() == 0) {
        user.forgetMove(move);
        move = null;
      }
    } while (move == null);

    return move;
  }

  public String[] useMove(Pokemon user, Move move, Pokemon target) {
    ArrayList<String> message = new ArrayList<>();

    message.add(String.format("<b>%s</b> uses <i>%s</i>!\n", user.getName(), move.getName()));

    Calculator calc = new Calculator(user, move, target);

    if (!calc.accuracyCheck()) {
      message.add(String.format("<b>%s</b>'s attack missed!\n", user.getName()));
      return message.toArray(new String[0]);
    }

    double efficiency = Type.getEfficiency(move.getType(), target.getType());

    if (efficiency > 0) {
      int hitCounter = 0;
      for (int i = 0; i < move.getHitCounter(); i++) {
        hitCounter++;
        double crit = calc.getCrit();
        int dmg = (int) (calc.getDamage() * efficiency * crit);
        target.stats.applyDamage(dmg);
        message.add(String.format("<b>%s</b> was damaged by %s HP\n", target.getName(), dmg));
        if (crit > 1){
          message.add("Critical hit!\n");
        }
        if (target.getStats().get(Stat.HP) <= 0) break;
      }
      move.decreasePP();

      if (hitCounter > 1) {
        message.add(String.format("<b>%s</b> was hit %d times!\n", target.getName(), move.getHitCounter()));
      }
    }

    message.add(
        (efficiency == 0) ? String.format("It doesn't affect <b>%s</b>\n", target.getName()) :
            (efficiency < 1) ? "It's not very effective...\n" :
                (efficiency > 1) ? "It's super effective!\n" : "");

    if (move.getRecoil() > 0) {
      int rec = calc.recoilDamageCalc();
      user.stats.applyDamage(rec);
      message.add(String.format("<b>%s</b> was hit by recoil by %s HP\n", user.getName(), rec));
    }

    return message.toArray(new String[0]);
  }
}