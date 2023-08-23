package pokemonLogic;

public enum Stat {
  HP(false),
  ATTACK(false),
  DEFENSE(false),
  SPECIAL_ATTACK(false),
  SPECIAL_DEFENSE(false),
  SPEED(false),
  ACCURACY(true),
  EVASION(true);

  public final boolean isHidden;

  Stat(boolean isHidden){
    this.isHidden = isHidden;
  }
}


