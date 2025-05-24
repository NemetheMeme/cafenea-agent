package try_2.agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
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
//        addBehaviour(new ReceiveBaristaDoneBehaviour());
//        addBehaviour(new ReceiveBaristaInfoBehaviour());
    }


//    @Override
//    protected void setup() {
//        System.out.println(getLocalName() + " started.");
//
//        // Începem comportamentul care verifică coada O2A pentru comenzi noi trimise din GUI
//        addBehaviour(new WakerBehaviour(this, 1000) {
//            protected void onWake() {
//                System.out.println("OrderManager spune Baristai să înceapă");
//                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                msg.addReceiver(barista);
//                msg.setContent("START");
//                send(msg);
//            }
//        });
//
//        // Primim mesaj de la Barista că e gata
//        addBehaviour(new ReceiveBaristaDoneBehaviour());
//
//        // Comportament pentru a primi mesajul BARISTA_READY
//        addBehaviour(new ReceiveBaristaInfoBehaviour());
//    }

//@Override
//protected void setup() {
//    addBehaviour(new OneShotBehaviour() {
//        @Override
//        public void action() {
//            // Simulate an order — replace with GUI/input logic
//            OrderQueue.addOrder("Caramel Latte");
//            OrderQueue.addOrder("Espresso Macchiato");
//            OrderQueue.addOrder("Vanilla Latte");
//            OrderQueue.addOrder("Caramel Latte");
//            OrderQueue.addOrder("Espresso Macchiato");
//            OrderQueue.addOrder("Vanilla Latte");
//            OrderQueue.addOrder("Caramel Latte");
//            OrderQueue.addOrder("Espresso Macchiato");
//            OrderQueue.addOrder("Vanilla Latte");
//
//            System.out.println("OrderManager: Order placed for Caramel Latte");
//        }
//    });
//}

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

//    private class ReceiveBaristaInfoBehaviour extends CyclicBehaviour {
//        @Override
//        public void action() {
//            ACLMessage msg = myAgent.receive();
//            if (msg != null && msg.getPerformative() == ACLMessage.INFORM
//                    && msg.getContent().equals("BARISTA_READY")) {
//                barista = msg.getSender();
//                System.out.println("OrderManager a primit referinta catre Barista: " + barista.getLocalName());
//                processOrders();
//            } else {
//                block();
//            }
//        }
//    }
//
//    private class ReceiveBaristaDoneBehaviour extends CyclicBehaviour {
//        @Override
//        public void action() {
//            ACLMessage msg = myAgent.receive();
//            if (msg != null && msg.getPerformative() == ACLMessage.INFORM
//                    && msg.getContent().startsWith("DONE:")) {
//                System.out.println("OrderManager: Barista a terminat comanda: " + msg.getContent().substring(5));
//                baristaBusy = false;
//                processOrders();
//            } else {
//                block();
//            }
//        }
//    }
}
