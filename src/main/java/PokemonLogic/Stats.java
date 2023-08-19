package PokemonLogic;

public class Stats {

  public final Stat HP;
  public final Stat ATTACK;
  public final Stat DEFENSE;
  public final Stat SPECIAL_ATTACK;
  public final Stat SPECIAL_DEFENSE;
  public final Stat SPEED;
  public final Stat ACCURACY;
  public final Stat EVASION;

  public Stats() {
    HP = new Stat(StatType.HP);
    ATTACK = new Stat(StatType.ATTACK);
    DEFENSE = new Stat(StatType.DEFENSE);
    SPECIAL_ATTACK = new Stat(StatType.SPECIAL_ATTACK);
    SPECIAL_DEFENSE = new Stat(StatType.SPECIAL_DEFENSE);
    SPEED = new Stat(StatType.SPEED);
    ACCURACY = new Stat(StatType.ACCURACY);
    EVASION = new Stat(StatType.EVASION);
  }

  public void setBaseValues(int hp, int atk, int def, int spAtk, int spDef, int spe) {
    this.HP.setBaseValue(hp);
    this.ATTACK.setBaseValue(atk);
    this.DEFENSE.setBaseValue(def);
    this.SPECIAL_ATTACK.setBaseValue(spAtk);
    this.SPECIAL_DEFENSE.setBaseValue(spDef);
    this.SPEED.setBaseValue(spe);
  }

  public void setStatsByLevel(int level) {
    this.HP.setStatByLevel(level);
    this.ATTACK.setStatByLevel(level);
    this.DEFENSE.setStatByLevel(level);
    this.SPECIAL_ATTACK.setStatByLevel(level);
    this.SPECIAL_DEFENSE.setStatByLevel(level);
    this.SPEED.setStatByLevel(level);
  }

  public void resetBValues() {
    this.HP.resetBValue();
    this.ATTACK.resetBValue();
    this.DEFENSE.resetBValue();
    this.SPECIAL_ATTACK.resetBValue();
    this.SPECIAL_DEFENSE.resetBValue();
    this.SPEED.resetBValue();
    this.ACCURACY.resetBValue();
    this.EVASION.resetBValue();
  }

  public void modifyHP(int deltaHP) {
    HP.battleValue -= deltaHP;
  }
}