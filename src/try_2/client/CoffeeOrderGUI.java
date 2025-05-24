package try_2.client;

import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.awt.*;

public class CoffeeOrderGUI extends JFrame {

    private final AgentController orderManagerAgent;

    public CoffeeOrderGUI(AgentController orderManagerAgent) {
        this.orderManagerAgent = orderManagerAgent;

        setTitle("Coffee Shop Order Panel");
        setSize(700, 170);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        addButton("Caramel Latte");
        addButton("Vanilla Latte");
        addButton("Espresso Macchiato");

        setVisible(true);
    }

    private void addButton(String coffeeName) {
        JButton button = new JButton(coffeeName);
        button.addActionListener(e -> {
            try {
                orderManagerAgent.putO2AObject(coffeeName, AgentController.ASYNC);
                System.out.println("GUI: Sent order -> " + coffeeName);
            } catch (StaleProxyException ex) {
                ex.printStackTrace();
            }
        });
        add(button);
    }
}