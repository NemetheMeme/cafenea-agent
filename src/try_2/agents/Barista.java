package try_2.agents;


import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import try_2.factory.CoffeeProductFactory;
import try_2.menu.boundary.CoffeeProduct;
import try_2.products.ProductType;
import try_2.tracking.Logger;
import try_2.tracking.OrderQueue;
import try_2.tracking.SupplyRequestQueue;

import java.util.Set;

public class Barista extends Agent {

    private AID orderManager;
    private boolean isBusy = false;
    private CoffeeProduct currentCoffee;

    @Override
    protected void setup() {
        orderManager = new AID("OrderManager", AID.ISLOCALNAME);

        addBehaviour(new TickerBehaviour(this, 2000) {
            @Override
            protected void onTick() {
                if (!isBusy && OrderQueue.hasOrders()) {
                    isBusy = true;
                    String order = OrderQueue.getNextOrder();
                    CoffeeProduct coffee = CoffeeProductFactory.create(order);

                    addBehaviour(new OneShotBehaviour() {
                        @Override
                        public void action() {
                            currentCoffee = CoffeeProductFactory.create(order);
                            while (!currentCoffee.isCoffeeProductReady()) {
                                Set<ProductType> neededIngredients = currentCoffee.tryPrepare();

                                if (!neededIngredients.isEmpty()) {
                                    for (ProductType type : neededIngredients) {
                                        SupplyRequestQueue.requestProduct(type);
                                    }

                                    ACLMessage supplyRequest = new ACLMessage(ACLMessage.REQUEST);
                                    supplyRequest.addReceiver(new AID("Supplier", AID.ISLOCALNAME));
                                    supplyRequest.setContent("SUPPLY_REQUEST");
                                    send(supplyRequest);

                                    Logger.logSupply(getLocalName() + ": Waiting for supply...");
                                    return;
                                }
                            }
                            finishCoffee();
                        }
                    });
                }
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && msg.getPerformative() == ACLMessage.CONFIRM && msg.getContent().equals("SUPPLY_DONE")) {
                    Logger.logSupply(getLocalName() + ": Got supply confirmation from Supplier.");
                    addBehaviour(new ResumePreparationBehaviour());
                } else {
                    block();
                }
            }
        });
    }
    private class ResumePreparationBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            if (currentCoffee == null) {
                isBusy = false;
                return;
            }

            while (!currentCoffee.isCoffeeProductReady()) {
                Set<ProductType> neededIngredients = currentCoffee.tryPrepare();

                if (!neededIngredients.isEmpty()) {
                    for (ProductType type : neededIngredients) {
                        SupplyRequestQueue.requestProduct(type);
                    }

                    ACLMessage supplyRequest = new ACLMessage(ACLMessage.REQUEST);
                    supplyRequest.addReceiver(new AID("Supplier", AID.ISLOCALNAME));
                    supplyRequest.setContent("SUPPLY_REQUEST");
                    send(supplyRequest);

                    Logger.logSupply(getLocalName() + ": Still missing ingredients. Requesting again...");
                    return;
                }
            }

            finishCoffee();
        }
    }
    private void finishCoffee() {
        Logger.logMessage(getLocalName() + ": " + currentCoffee.getName() + " is ready!");

        ACLMessage doneMsg = new ACLMessage(ACLMessage.INFORM);
        doneMsg.addReceiver(orderManager);
        doneMsg.setContent("DONE:" + currentCoffee.getName());
        send(doneMsg);

        currentCoffee = null;
        isBusy = false;
    }


}
