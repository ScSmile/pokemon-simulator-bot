import PokemonLogic.Pokemon;
import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

class Response {

  private static final BattleEngine engine = new BattleEngine();

  public static void init() {
    SendQueue.enqueue("""
        /start - Start the bot
        /help - Info""");
    SendQueue.setKeyboard(
        initKeyboard(new String[]{
            "/start", "/help"
        }));
  }

  public static void start() {
    SendQueue.enqueue("""
        Choose battle mode:
        random - Start battle between two randomly generated Pokemon.
        manual - Manually choose which Pokemon will battle.""");

    SendQueue.setKeyboard(
        initKeyboard(new String[]{
            "random", "manual", "back"
        }));
  }

  public static void help() {
    SendQueue.enqueue("""
        Hello, this is Pokemon Battle Simaulator!
        It's still in progress, so stay tuned for more updates!
                
        by ScSmile
        ver. 1.0.0""");

    SendQueue.setKeyboard(
        initKeyboard(new String[]{
            "back"
        }));
  }

  public static void randomBattle() {
    engine.randomBattle();
    SendQueue.enqueue("Press <b>restart</b> button to restart the bot");
  }

  public static void Battle(Pokemon pkmn1, Pokemon pkmn2) {
    engine.battle(pkmn1, pkmn2);
    SendQueue.enqueue("Press <b>restart</b> button to restart the bot");
  }

  public static void choice() {
    SendQueue.enqueue("Are you sure? yes/no");
  }

  public static void input() {
    SendQueue.enqueue("""
        Enter Pokemon names or IDs with space in between
        Example: Pikachu Eevee, 123 321, Charizard 666""");

    SendQueue.setKeyboard(
        initKeyboard(new String[]{
            "back"
        }));
  }

  public static void wrongNameInput() {
    SendQueue.enqueue("Incorrect Pokemon name/ID input. Please try again.");
  }

  public static void wrongInput() {
    SendQueue.enqueue("Wrong input. Please try again.");
  }

  public static void restart() {
    SendQueue.setKeyboard(
        initKeyboard(new String[]{
            "restart"
        }));
  }

  public static ReplyKeyboard initKeyboard(String[] buttonsTexts) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setResizeKeyboard(true);
    //replyKeyboardMarkup.setOneTimeKeyboard(true);

    ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
    KeyboardRow keyboardRow = new KeyboardRow();
    keyboardRows.add(keyboardRow);

    for (String buttonText :
        buttonsTexts) {
      keyboardRow.add(new KeyboardButton(buttonText));
    }

    replyKeyboardMarkup.setKeyboard(keyboardRows);
    return replyKeyboardMarkup;
  }

  public static ReplyKeyboard removeKeyboard() {
    ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
    replyKeyboardRemove.setRemoveKeyboard(true);

    return replyKeyboardRemove;
  }
}