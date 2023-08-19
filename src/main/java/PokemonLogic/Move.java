package PokemonLogic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public abstract class Move {

  public enum Category {
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
  protected final int maxPP;
  protected int currentPP;
  @JsonProperty("category")
  protected Category category;

  public Move(String name, Type type, int accuracy, int maxPP) {
    this.name = name;
    this.type = type;
    this.accuracy = accuracy;
    this.priority = 0;
    this.maxPP = this.currentPP = 5;
  }

  public abstract String[] useMove(Pokemon user, Pokemon target);
}