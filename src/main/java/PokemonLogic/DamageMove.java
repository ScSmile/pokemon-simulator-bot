package PokemonLogic;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import lombok.Getter;

public class DamageMove extends Move {

  @Getter
  private final int power;
  @Getter
  private int hitCounter = 1;

  @Getter
  @JsonProperty("recoil")
  private final double recoil;

  private ArrayList<String> message;

  public DamageMove(String name, Type type, int power, int recoil,
      int accuracy, int hitCounter, int maxPP, Category category) {
    super(name, type, accuracy, maxPP);
    this.power = power;
    this.recoil = recoil;
    this.hitCounter = hitCounter;
    this.category = category;
  }

  public DamageMove() {
    super("Struggle", Type.NONE, -1, -1);
    this.category = Category.PHYSICAL;
    this.power = 50;
    this.recoil = 0.25;
  }

  public double getCritical() {
    double critical = (new Random().nextInt(23) > 0)
        ? 1
        : 1.5;
    if (critical == 1.5) {
      message.add("Critical hit!\n");
    }

    return critical;
  }

  private double[] getAtkAndDef(Stats userStats, Stats targetStats) {
    double userAtk;
    double targetDef;
    if (this.category == Category.PHYSICAL) {
      userAtk = userStats.ATTACK.getBattleValue();
      targetDef = targetStats.DEFENSE.getBattleValue();
    } else {
      userAtk = userStats.SPECIAL_ATTACK.getBattleValue();
      targetDef = targetStats.SPECIAL_DEFENSE.getBattleValue();
    }
    return new double[]{userAtk, targetDef};
  }

  private int damageCalc(Pokemon user, Pokemon target) {
    double[] AtkAndDef = getAtkAndDef(user.stats, target.stats);
    double userAtk = AtkAndDef[0];
    double targetDef = AtkAndDef[1];

    double STAB = (Arrays.asList(user.getType()).contains(this.getType()))
        ? 1.5
        : 1;

    int damage = (int) Math.floor(
        ((2 * (double) user.getLevel() / 5 + 2) * this.power * userAtk / targetDef / 50 + 2)
            * STAB * this.getCritical() * (0.85 + 0.01 * new Random().nextInt(16)));
    damage = (damage > 0) ? damage : 1;
    return damage;
  }

  private int recoilDamageCalc(Pokemon user) {
    int damage = (int) Math.floor(this.recoil * user.stats.HP.getValue());
    damage = (damage > 0) ? damage : 1;
    return damage;
  }

  private boolean accuracyCheck(double userAccuracy, double targetEvasion) {
    if (this.accuracy == -1) {
      return true;
    }
    return new Random().nextDouble() * 100 < userAccuracy / targetEvasion * this.accuracy;
  }

  public int getCurrentPP() {
    return this.currentPP;
  }

  public String[] useMove(Pokemon user, Pokemon target) {
    message = new ArrayList<>();
    message.add(String.format("<b>%s</b> uses <i>%s</i>!\n", user.getName(), this.name));

    if (!accuracyCheck(
        user.stats.ACCURACY.getBattleValue(),
        target.stats.EVASION.getBattleValue())) {
      message.add(String.format("<b>%s</b>'s attack missed!\n", user.getName()));
      return message.toArray(new String[0]);
    }

    double efficiency = Type.getEfficiency(this.type, target.getType());

    if (efficiency > 0) {
      for (int i = 0; i < this.hitCounter; i++) {
        int dmg = (int) Math.floor(this.damageCalc(user, target) * efficiency);
        target.stats.modifyHP(dmg);
        message.add(String.format("<b>%s</b> was damaged by %s HP\n", target.getName(), dmg));
      }
      this.currentPP--;

      if (hitCounter > 1) {
        message.add(String.format("<b>%s</b> was hit %d times!\n", target.getName(), hitCounter));
      }
    }

    message.add(
        (efficiency == 0) ? String.format("It doesn't affect <b>%s</b>\n", target.getName()) :
            (efficiency < 1) ? "It's not very effective...\n" :
                (efficiency > 1) ? "It's super effective!\n" : "");

    if (recoil > 0) {
      int rec = recoilDamageCalc(user);
      user.stats.modifyHP(rec);
      message.add(String.format("<b>%s</b> was hit by recoil by %s HP\n", user.getName(), rec));
    }

    return message.toArray(new String[0]);
  }
}
