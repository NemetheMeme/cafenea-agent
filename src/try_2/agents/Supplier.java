package try_2.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import try_2.products.Inventory;
import try_2.products.ProductType;
import try_2.tracking.Logger;
import try_2.tracking.SupplyRequestQueue;

public class Supplier extends Agent {
    private static boolean isRestocking = false;
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.REQUEST && msg.getContent().equals("SUPPLY_REQUEST")) {
                    Logger.logSupply(getLocalName() + ": Received supply request from " + msg.getSender().getLocalName());

                    addBehaviour(new OneShotBehaviour() {
                        @Override
                        public void action() {
                            int quantity = 2;
                            for (ProductType type : SupplyRequestQueue.getAndClearRequestedProducts()) {
                                Inventory.getInstance().supply(type, quantity);
                                Logger.logSupply(getLocalName() + ": Restocked " + type);
                                SupplyRequestQueue.markRestockComplete(type);
                            }
                            ACLMessage confirm = msg.createReply();
                            confirm.setPerformative(ACLMessage.CONFIRM);
                            confirm.setContent("SUPPLY_DONE");
                            send(confirm);
                        }
                    });
                } else {
                    block();
                }
            }
        });
    }

    public static boolean isRestocking(){
        return isRestocking;
    }
}

