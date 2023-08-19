package PokemonLogic;

import java.util.Arrays;

public enum Type {
  NORMAL,
  GRASS,
  ELECTRIC,
  WATER,
  FIRE,
  BUG,
  GHOST,
  PSYCHIC,
  STEEL,
  DARK,
  FLYING,
  ICE,
  POISON,
  GROUND,
  ROCK,
  DRAGON,
  FIGHTING,
  FAIRY,
  NONE;

  public Type[] weaknesses, resistances, immunities;

  public static double getEfficiency(Type attack, Type defender) {
    return Arrays.asList(defender.weaknesses).contains(attack)
        ? 2 : Arrays.asList(defender.resistances).contains(attack)
        ? 0.5 : Arrays.asList(defender.immunities).contains(attack)
        ? 0
        : 1;
  }

  public static double getEfficiency(Type attack, Type[] defender) {
    double efficiency = 1;
    for (Type defType : defender) {
      efficiency *= getEfficiency(attack, defType);
    }
    return efficiency;
  }

  static {
    //Normal
    NORMAL.weaknesses = new Type[]{FIGHTING};
    NORMAL.resistances = new Type[]{};
    NORMAL.immunities = new Type[]{GHOST};
    //Grass
    GRASS.weaknesses = new Type[]{FLYING, POISON, BUG, FIRE, ICE};
    GRASS.resistances = new Type[]{GROUND, WATER, GRASS, ELECTRIC};
    GRASS.immunities = new Type[]{};
    //Electric
    ELECTRIC.weaknesses = new Type[]{GROUND};
    ELECTRIC.resistances = new Type[]{FLYING, STEEL, ELECTRIC};
    ELECTRIC.immunities = new Type[]{};
    //Water
    WATER.weaknesses = new Type[]{GRASS, ELECTRIC};
    WATER.resistances = new Type[]{STEEL, FIRE, WATER, ICE};
    WATER.immunities = new Type[]{};
    //Fire
    FIRE.weaknesses = new Type[]{GROUND, ROCK, WATER};
    FIRE.resistances = new Type[]{BUG, STEEL, FIRE, GRASS, ICE, FAIRY};
    FIRE.immunities = new Type[]{};
    //Bug
    BUG.weaknesses = new Type[]{FLYING, ROCK, FIRE};
    BUG.resistances = new Type[]{FIGHTING, GROUND, GRASS};
    BUG.immunities = new Type[]{};
    //Ghost
    GHOST.weaknesses = new Type[]{GHOST, DARK};
    GHOST.resistances = new Type[]{POISON, BUG};
    GHOST.immunities = new Type[]{NORMAL, FIGHTING};
    //Psychic
    PSYCHIC.weaknesses = new Type[]{BUG, GHOST, DARK};
    PSYCHIC.resistances = new Type[]{FIGHTING, PSYCHIC};
    PSYCHIC.immunities = new Type[]{};
    //Steel
    STEEL.weaknesses = new Type[]{FIGHTING, GROUND, FIRE};
    STEEL.resistances = new Type[]{NORMAL, FLYING, ROCK, BUG, STEEL, GRASS, PSYCHIC, ICE, DRAGON,
        FAIRY};
    STEEL.immunities = new Type[]{POISON};
    //Dark
    DARK.weaknesses = new Type[]{FIGHTING, BUG, FAIRY};
    DARK.resistances = new Type[]{GHOST, DARK};
    DARK.immunities = new Type[]{PSYCHIC};
    //Flying
    FLYING.weaknesses = new Type[]{ROCK, ELECTRIC, ICE};
    FLYING.resistances = new Type[]{FIGHTING, BUG, GRASS};
    FLYING.immunities = new Type[]{GROUND};
    //Ice
    ICE.weaknesses = new Type[]{FIGHTING, ROCK, STEEL, FIRE};
    ICE.resistances = new Type[]{ICE};
    ICE.immunities = new Type[]{};
    //Poison
    POISON.weaknesses = new Type[]{GROUND, PSYCHIC};
    POISON.resistances = new Type[]{FIGHTING, POISON, GRASS, FAIRY};
    POISON.immunities = new Type[]{};
    //Ground
    GROUND.weaknesses = new Type[]{WATER, GRASS, ICE};
    GROUND.resistances = new Type[]{POISON, ROCK};
    GROUND.immunities = new Type[]{ELECTRIC};
    //Rock
    ROCK.weaknesses = new Type[]{FIGHTING, GROUND, STEEL, WATER, GRASS};
    ROCK.resistances = new Type[]{NORMAL, FLYING, POISON, FIRE};
    ROCK.immunities = new Type[]{};
    //Dragon
    DRAGON.weaknesses = new Type[]{ICE, DRAGON, FAIRY};
    DRAGON.resistances = new Type[]{FIRE, WATER, GRASS, ELECTRIC};
    DRAGON.immunities = new Type[]{};
    //Fighting
    FIGHTING.weaknesses = new Type[]{FLYING, PSYCHIC, FAIRY};
    FIGHTING.resistances = new Type[]{ROCK, BUG, DARK};
    FIGHTING.immunities = new Type[]{};
    //Fairy
    FAIRY.weaknesses = new Type[]{POISON, STEEL};
    FAIRY.resistances = new Type[]{FIGHTING, BUG, DARK};
    FAIRY.immunities = new Type[]{DRAGON};

    NONE.weaknesses = new Type[]{};
    NONE.resistances = new Type[]{};
    NONE.immunities = new Type[]{};
  }
}
