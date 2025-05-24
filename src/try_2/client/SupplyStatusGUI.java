package try_2.client;

import javax.swing.*;
import java.awt.*;

public class SupplyStatusGUI {
    private static final SupplyStatusGUI instance = new SupplyStatusGUI();
    private final JTextArea statusArea;

    private SupplyStatusGUI() {
        JFrame frame = new JFrame("Supply & Barista Status");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        frame.add(new JScrollPane(statusArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static SupplyStatusGUI getInstance() {
        return instance;
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            statusArea.append(message + "\n");
        });
    }
}
