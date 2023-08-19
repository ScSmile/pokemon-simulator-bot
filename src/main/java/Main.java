import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

  private final static Logger log = LogManager.getLogger();

  public static void main(String[] args) {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(new Bot("@PokesimBot",
          "2081195084:AAGsuYhFm7wO5BRF4pwc_yc0ZS2lVWHHJTY"));
    } catch (TelegramApiException e) {
      log.error("Error occured: " + e.getMessage());
    }
  }
}
