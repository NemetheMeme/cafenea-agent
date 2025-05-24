package try_2.tracking;

import try_2.client.MessageLoggerGUI;
import try_2.client.SupplyStatusGUI;

public class Logger {
    public static void logMessage(String msg) {
        MessageLoggerGUI.getInstance().log(msg);
    }

    public static void logSupply(String msg) {
        SupplyStatusGUI.getInstance().log(msg);
    }
}
