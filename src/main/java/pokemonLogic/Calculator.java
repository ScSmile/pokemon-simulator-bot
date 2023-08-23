package pokemonLogic;

import pokemonLogic.Move.DamageType;
import java.util.Arrays;
import java.util.Random;

public class Calculator {

  private final Pokemon user;
  private final Pokemon target;
  private final Move move;

  public Calculator(Pokemon user, Move move, Pokemon target){
    this.user = user;
    this.target = target;
    this.move = move;
  }

  public double getCrit() {
    return  (new Random().nextInt(23) > 0)
        ? 1
        : 1.5;
  }

  public double[] getAtkAndDef() {
    double userAtk;
    double targetDef;
    if (move.getDamageType() == DamageType.PHYSICAL) {
      userAtk = user.stats.get(Stat.ATTACK);
      targetDef = target.stats.get(Stat.DEFENSE);
    } else {
      userAtk = user.stats.get(Stat.SPECIAL_ATTACK);
      targetDef = target.stats.get(Stat.SPECIAL_DEFENSE);
    }
    return new double[]{userAtk, targetDef};
  }

  public int getDamage() {
    double[] AtkAndDef = getAtkAndDef();
    double userAtk = AtkAndDef[0];
    double targetDef = AtkAndDef[1];

    double STAB = (Arrays.asList(user.getType()).contains(move.getType()))
        ? 1.5
        : 1;

    int damage = (int)
        (((2 * (double) user.getLevel() / 5 + 2) * move.getPower() * userAtk / targetDef / 50 + 2)
            * STAB * (0.85 + 0.01 * new Random().nextInt(16)));
    damage = (damage > 0) ? damage : 1;
    return damage;
  }

  public int recoilDamageCalc() {
    int damage = (int) (0.01 * move.getRecoil() * user.stats.get(Stat.HP));
    damage = (damage > 0) ? damage : 1;
    return damage;
  }

  public boolean accuracyCheck() {
    if (move.accuracy == -1) {
      return true;
    }
    return new Random().nextDouble() * 100
        < 1.0 * user.stats.get(Stat.ACCURACY) / user.stats.get(Stat.EVASION) * move.accuracy;
  }
}
