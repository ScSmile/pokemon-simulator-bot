package PokemonLogic;

public class Stat {

  public final StatType name;
  private final boolean isHidden;

  private int baseValue;
  private int value;
  double battleValue;

  //stat modifier, it's between -6 and 6
  private int modifier = 0;

  public Stat() {
    this.name = StatType.HP;
    this.isHidden = false;
  }

  public Stat(StatType name) {
    this.name = name;
    this.isHidden = name == StatType.ACCURACY || name == StatType.EVASION;
    if (this.isHidden) {
      this.baseValue = this.value = 100;
    }
  }

  public void setBaseValue(int baseValue) {
    if (this.baseValue == 0) {
      this.baseValue = baseValue;
    } else {
      throw new IllegalCallerException("Base stat is already set");
    }
  }

  public int getBaseValue() {
    return baseValue;
  }

  public void resetBValue() {
    this.battleValue = this.value;
  } // battleValue

  public double getBattleValue() {
    return this.battleValue;
  }

  public void resetModifier() {
    this.modifier = 0;
  }

  public int getValue() {
    return value;
  }

  public void setStatByLevel(int level) {
    if (this.isHidden) {
      throw new IllegalCallerException("Accuracy and Evasion are constant");
    }
    this.value = (this.name == StatType.HP)
        ? (int) (0.01 * (2 * this.baseValue) * level) + level + 10
        : (int) (0.01 * (2 * this.baseValue) * level) + 5;
  }

  public void applyModifier(int mod) {
    if (this.name == StatType.HP) {
      throw new IllegalArgumentException("Modifiers don't apply to HP PokemonLogic.Stat");
    }
    this.modifier = (Math.abs(this.modifier + mod) > 6)
        ? (int) Math.signum(this.modifier + mod) * 6
        : this.modifier + mod;
    this.battleValue = (this.isHidden)
        ? this.value * Math.pow((double) (Math.abs(this.modifier) + 3) / 3,
        Math.signum(this.modifier))
        : this.value * Math.pow((double) (Math.abs(this.modifier) + 2) / 2,
            Math.signum(this.modifier));
  }
}
