import pokemonLogic.Pokemon;
import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

class Response {

  private static final BattleEngine engine = new BattleEngine();

  public static void init() {
    SendHandler.addMessage("""
        /start - Start the bot
        /help - Info""");
    SendHandler.setKeyboard(
        initKeyboard(new String[]{
            "/start", "/help"
        }));
  }

  public static void start() {
    SendHandler.addMessage("""
        Choose battle mode:
        random - Start battle between two randomly generated Pokemon.
        manual - Manually choose which Pokemon will battle.""");

    SendHandler.setKeyboard(
        initKeyboard(new String[]{
            "random", "manual", "back"
        }));
  }

  public static void help() {
    SendHandler.addMessage("""
        Hello, this is Pokemon Battle Simaulator!
        It allows you to have automated Pokemon battles with more than 800 pocket monsters for you to choose from!
        It's still in progress, so stay tuned for more updates!
                
        by ScSmile
        ver. 1.0.1""");

    SendHandler.setKeyboard(
        initKeyboard(new String[]{
            "back"
        }));
  }

  public static void randomBattle() {
    engine.randomBattle();
    SendHandler.addMessage("Press <b>restart</b> button to restart the bot");
  }

  public static void Battle(Pokemon pkmn1, Pokemon pkmn2) {
    engine.battle(pkmn1, pkmn2);
    SendHandler.addMessage("Press <b>restart</b> button to restart the bot");
  }

  public static void input() {
    SendHandler.addMessage("""
        Enter Pokemon names or IDs with space in between
        Example: Pikachu Eevee, 123 321, Charizard 666""");

    SendHandler.setKeyboard(
        initKeyboard(new String[]{
            "back"
        }));
  }

  public static void wrongNameInput() {
    SendHandler.addMessage("Incorrect Pokemon name/ID input. Please try again.");
  }

  public static void wrongInput() {
    SendHandler.addMessage("Wrong input. Please try again.");
  }

  public static void restart() {
    SendHandler.setKeyboard(
        initKeyboard(new String[]{
            "restart"
        }));
  }

  public static ReplyKeyboard initKeyboard(String[] buttonsTexts) {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setResizeKeyboard(true);

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
}