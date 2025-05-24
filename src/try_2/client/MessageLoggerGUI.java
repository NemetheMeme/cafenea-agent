package try_2.client;

import javax.swing.*;
import java.awt.*;

public class MessageLoggerGUI {
    private static final MessageLoggerGUI instance = new MessageLoggerGUI();
    private final JTextArea logArea;

    private MessageLoggerGUI() {
        JFrame frame = new JFrame("System Message Log");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        frame.add(new JScrollPane(logArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static MessageLoggerGUI getInstance() {
        return instance;
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
        });
    }
}
