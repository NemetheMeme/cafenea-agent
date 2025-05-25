package try_2.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import try_2.tracking.OrderQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderManager extends Agent {

    private Queue<String> orderQueue = new ConcurrentLinkedQueue<>();
    private boolean baristaBusy = false;
    private AID barista;

    @Override
    protected void setup() {
        setEnabledO2ACommunication(true, 0);  // Enable O2A
        System.out.println(getLocalName() + " started.");

        addBehaviour(new CheckOrdersFromGUI(this, 1000));

    }

    private class CheckOrdersFromGUI extends TickerBehaviour {
        public CheckOrdersFromGUI(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            Object obj = myAgent.getO2AObject();
            if (obj != null && obj instanceof String) {
                String order = (String) obj;
                System.out.println("OrderManager a primit comanda via O2A: " + order);
                processOrders(order);
            }
        }
    }

    private void processOrders(String orderName) {
        System.out.println("OrderManager trimite comanda la Barista: " + orderName);
        ACLMessage orderMsg = new ACLMessage(ACLMessage.REQUEST);
        orderMsg.addReceiver(barista);
        orderMsg.setContent(orderName);
        send(orderMsg);
        OrderQueue.addOrder(orderName);
    }
}
