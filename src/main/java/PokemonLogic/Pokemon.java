package PokemonLogic;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pokemon {

  private final Logger log = LogManager.getLogger();

  private final int id;
  private final String name;

  public final Stats stats;

  private final Type[] type = new Type[]{Type.NONE};
  private int level;

  private Move[] moves = new Move[4];
  private int movepoolSize = 0;

  public Pokemon() {
    this.name = "MISSINGNO";
    this.id = -1;
    this.stats = new Stats();
    this.learnMove(new DamageMove());
  }

  public String getName() {
    return this.name;
  }

  public int getID() {
    return this.id;
  }

  public int getLevel() {
    return this.level;
  }

  public void updateLevel(int level) {
    this.level = level;
    this.stats.setStatsByLevel(level);
  }

  public Type[] getType() {
    return this.type;
  }

  public Stats getStats() {
    return this.stats;
  }

  public Move[] getMoves() {
    return this.moves;
  }

  public void setMoves() {
    Move[] moveSet1;
    Move[] moveSet2 = new Move[]{};
    if (this.type.length == 1) {
      moveSet1 = PokemonBuilder.getMoveSet(this.type[0], 4);
      if (moveSet1.length < 4) {
        moveSet2 = PokemonBuilder.getMoveSet(Type.NORMAL, 4 - moveSet1.length);
      }
    } else {
      moveSet1 = PokemonBuilder.getMoveSet(this.type[0], 2);
      moveSet2 = PokemonBuilder.getMoveSet(this.type[1], 2);
    }
    this.moves = ArrayUtils.addAll(moveSet1, moveSet2);
  }

  private void learnMove(Move newMove) {
    if (movepoolSize < 4) {
      moves[movepoolSize] = newMove;
      movepoolSize++;
    }
    moves[3] = newMove;
  }

  public String[] useMove(int moveNum, Pokemon target) {
    if (moveNum < 0 || moveNum >= 4) {
      log.error("There are 4 moves max");
    }
    Move move = this.moves[moveNum];
    if (move.currentPP > 0) {
      return move.useMove(this, target);
    }
    //SendQueue.enqueue(String.format("%s have no PP's left!\n", move.getName()));
    return ArrayUtils.addAll(
        new String[]{String.format("%s have no PP's left!\n", this.name)},
        new DamageMove().useMove(this, target));
  }
}