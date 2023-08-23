package pokemonLogic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {

  @JsonProperty("baseStats")
  private final int[] baseStats;
  private final int[] battleStats;
  private final int size = Stat.values().length;

  public Stats(){
    baseStats = new int[]{0,0,0,0,0,0};
    battleStats = new int[size];
  }

  public void setBattleStats(int level) {
    Stat[] statValues = Stat.values();
    for (int i = 0; i < size; i++) {
      if (statValues[i].isHidden) {
        battleStats[i] = 100;
        continue;
      }
      battleStats[i] = (statValues[i] == Stat.HP)
          ? (int) (0.01 * (2 * baseStats[i]) * level) + level + 10
          : (int) (0.01 * (2 * baseStats[i]) * level) + 5;
    }
  }

  public int get(Stat type){
      return battleStats[type.ordinal()];
  }

  public void applyDamage(int damage){
    battleStats[0] -= damage;
  }
}