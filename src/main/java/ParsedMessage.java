import java.util.HashMap;
import java.util.Locale;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ParsedMessage {

  public static final HashMap<String, Command> commandsMap = new HashMap<>() {{
    put("/start", Command.START);
    put("/help", Command.HELP);
    put("yes", Command.YES);
    put("no", Command.NO);
    put("manual", Command.MANUAL);
    put("random", Command.RANDOM);
    put("restart", Command.RESTART);
    put("back", Command.BACK);
  }};

  @Getter
  private final String text;
  @Getter
  private final Command command;
  @Getter
  private final String chatID;

  ParsedMessage(Message message) {
    this.text = message.getText();
    this.command = commandsMap.getOrDefault(
        this.text.toLowerCase(Locale.ROOT).trim(),
        Command.NON_COMMAND);
    this.chatID = Long.toString(message.getChatId());
  }
}