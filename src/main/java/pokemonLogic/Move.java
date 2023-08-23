package pokemonLogic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class Move {

  public enum DamageType {
    PHYSICAL,
    SPECIAL,
  }

  @Getter
  protected final String name;
  @Getter
  protected final Type type;
  @Getter
  protected final int accuracy;
  @Getter
  protected final int priority;
  @Getter
  private final int power;
  @Getter
  private final int hitCounter;
  @Getter
  private final int critRate;
  @Getter
  @JsonProperty("recoil")
  private final int recoil;
  @Getter
  @JsonProperty("category")
  private final DamageType damageType;
  @Getter
  private int currentPP;

  public Move(String name, Type type, int power, int recoil,
      int accuracy, int hitCounter, int critRate, int maxPP, DamageType damageType) {
    this.name = name;
    this.type = type;
    this.accuracy = accuracy;
    this.priority = 0;
    this.currentPP = maxPP;
    this.power = power;
    this.recoil = recoil;
    this.critRate = critRate;
    this.hitCounter = hitCounter;
    this.damageType = damageType;
  }

  public Move(){
    this("Tackle", Type.NORMAL, 40, 0,
        100, 1, 1, 5, DamageType.PHYSICAL);
  }

  public static Move getStruggle() {
    return new Move("Struggle", Type.NONE, 50, 25,
        -1, 1, 0, -1, DamageType.PHYSICAL);
  }

  public void decreasePP(){
    currentPP--;
  }
}