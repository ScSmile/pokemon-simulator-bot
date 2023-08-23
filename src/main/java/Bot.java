import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

  private static final Logger log = LogManager.getLogger();

  private final String botUsername;
  private final String token;

  public Bot(String botUsername, String token) {
    this.botUsername = botUsername;
    this.token = token;
  }

  private final HashMap<String, MessageHandler> handlerByIDList = new HashMap<>();

  @Override
  public void onUpdateReceived(Update update) {
    log.info("Recieved an update, ID: " + update.getUpdateId());
    if (update.hasMessage() && update.getMessage().hasText()) {
      ParsedMessage msg = new ParsedMessage(update.getMessage());
      log.info("Recieved a message " + msg.getText());

      handlerByIDList.putIfAbsent(msg.getChatID(), new MessageHandler());
      MessageHandler currentHandler = handlerByIDList.get(msg.getChatID());

      currentHandler.operate(msg);
      SendMessage sendMsg = setEmptyMessage(msg.getChatID());
      if (SendHandler.hasKeyboard()) {
        sendMsg.setReplyMarkup(SendHandler.popKeyboard());
      }
      while (!SendHandler.isEmpty()) {
        sendMsg.setText(SendHandler.popMessage());
        sendMessage(sendMsg);
      }
    }
  }

  public void sendMessage(SendMessage sendMsg) {
    try {
      execute(sendMsg);
    } catch (TelegramApiException e) {
      log.error("Sending message error: " + e);
    }
  }

  private SendMessage setEmptyMessage(String chat_id) {
    SendMessage sendMsg = new SendMessage();
    sendMsg.setChatId(chat_id);
    sendMsg.setParseMode("HTML");

    return sendMsg;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public String getBotUsername() {
    return botUsername;
  }
}

