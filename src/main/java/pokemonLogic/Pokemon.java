package pokemonLogic;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

public class Pokemon {
  @Getter
  private final String name;
  @Getter
  private final int id;
  @Getter
  public final Stats stats;
  @Getter
  private final Type[] type;
  @Getter
  private int level;
  @Getter
  private Move[] moves = new Move[4];
  @Getter
  private int movesCount;

  public Pokemon() {
    this.name = "MISSINGNO";
    this.id = -1;
    this.stats = new Stats();
    this.type = new Type[]{Type.NONE};
    this.level = 1;
    this.learnMove(Move.getStruggle());
  }

  public void setLevel(int level) {
    this.level = level;
    this.stats.setBattleStats(level);
  }

  public void setMoves() {
    Move[] moveSet1;
    Move[] moveSet2 = new Move[]{};

    if (this.type.length == 1) {
      moveSet1 = PokemonBuilder.getMoves(this.type[0], 4);
      assert moveSet1 != null;

      if (moveSet1.length < 4) {
        moveSet2 = PokemonBuilder.getMoves(Type.NORMAL, 4 - moveSet1.length);
      }
    } else {
      moveSet1 = PokemonBuilder.getMoves(this.type[0], 2);
      moveSet2 = PokemonBuilder.getMoves(this.type[1], 2);
    }
    this.moves = ArrayUtils.addAll(moveSet1, moveSet2);
    this.movesCount = this.moves.length;
  }

  public void learnMove(Move newMove) {
    if (this.movesCount < 4) {
      moves[this.movesCount] = newMove;
      this.movesCount++;
    }
    this.moves[3] = newMove;
  }

  public void forgetMove(Move move){
    if (!ArrayUtils.contains(this.moves, move)){
      throw new IllegalArgumentException(
          String.format("%s doesn't know %s", this.name, move.getName()));
    }

    this.movesCount--;
    this.moves = ArrayUtils.removeAllOccurrences(this.moves, move);
  }

  public boolean hasPPLeft(){
    int totalPP = 0;
    for (Move move:
    this.moves) {
      totalPP += move.getCurrentPP();
    }
    return totalPP > 0;
  }
}