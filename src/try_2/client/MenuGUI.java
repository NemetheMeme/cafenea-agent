package try_2.client;

import jade.wrapper.AgentController;
import try_2.utils.ProductsRetriever;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuGUI extends JFrame {
    private JTextArea chatArea;
    private AgentController menuAgentController;
    private ProductsRetriever productsRetriever = new ProductsRetriever();

    public MenuGUI() {
        int buttonViewHeight = 50 * productsRetriever.getTotalProductsCount() / 2;
        int chatViewHeight = 200;
        setTitle("Coffee Menu");
        setSize(400, buttonViewHeight + chatViewHeight);
        setLocation(750,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        List<String> options = productsRetriever.getProductsNames();
        for (String drink : options) {
            JButton button = new JButton(drink);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    appendMessage("You: " + drink);
                    sendToMenuAgent(drink);
                }
            });
            buttonPanel.add(button);
        }

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setMenuAgentController(AgentController controller) {
        this.menuAgentController = controller;
    }

    public void showResponse(String message) {
        SwingUtilities.invokeLater(() -> chatArea.append("MenuAgent: " + message + "\n"));
    }

    private void appendMessage(String msg) {
        chatArea.append(msg + "\n");
    }

    private void sendToMenuAgent(String msgText) {
        try {
            if (menuAgentController != null) {
                menuAgentController.putO2AObject(msgText, AgentController.ASYNC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
