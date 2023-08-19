import java.util.ArrayList;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class SendQueue {

  private static final ArrayList<String> messageQueue = new ArrayList<>();

  private static ReplyKeyboard keyboard;

  public static ReplyKeyboard popKeyboard() {
    ReplyKeyboard rk = keyboard;
    keyboard = null;
    return rk;
  }

  public static void setKeyboard(ReplyKeyboard rk) {
    keyboard = rk;
  }

  public static boolean hasKeyboard() {
    return keyboard != null;
  }

  public static boolean isEmpty() {
    return messageQueue.isEmpty();
  }

  public static String dequeue() {
    if (messageQueue.isEmpty()) {
      System.out.println("Underflow, messageQueue is empty");
      System.exit(-1);
    }

    return messageQueue.remove(0);
  }

  public static void enqueue(String message) {
    messageQueue.add(message);
  }

}
