package try_2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import try_2.client.CoffeeOrderGUI;
import try_2.client.MenuGUI;
import try_2.client.SupplyStatusGUI;
import try_2.client.MessageLoggerGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);

        try {
            AgentController orderManager = cc.createNewAgent("orderManager", "try_2.agents.OrderManager", null);
            orderManager.start();

            cc.createNewAgent("barista1", "try_2.agents.Barista", null).start();
            cc.createNewAgent("barista2", "try_2.agents.Barista", null).start();
            cc.createNewAgent("supplier", "try_2.agents.Supplier", null).start();
            cc.createNewAgent("menuAgent", "try_2.agents.MenuAgent", null).start();

            SwingUtilities.invokeLater(() -> {
                new CoffeeOrderGUI(orderManager);
                MessageLoggerGUI.getInstance();
                SupplyStatusGUI.getInstance();
                JFrame orderFrame = (JFrame) JFrame.getFrames()[0];
                JFrame messageFrame = (JFrame) JFrame.getFrames()[1];
                JFrame supplyFrame = (JFrame) JFrame.getFrames()[2];
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}