package try_2.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import try_2.client.MenuGUI;
import try_2.menu.boundary.CoffeeProduct;
import try_2.utils.ProductsRetriever;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MenuAgent extends Agent {
    private final Map<String, String> menu = new HashMap<>();
    private MenuGUI gui;
    private ProductsRetriever productsRetriever = new ProductsRetriever();

    @Override
    protected void setup() {
        Set<CoffeeProduct> products = productsRetriever.getProducts();

        products.forEach(product ->
            menu.put(product.getName(), productsRetriever.getFormattedProductRecipe(product))
        );

        setEnabledO2ACommunication(true, 0);

        gui = new MenuGUI();
        try {
            gui.setMenuAgentController(getContainerController().getAgent(getLocalName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        addBehaviour(new CyclicBehaviour() {
            public void action() {
                Object o = getO2AObject();
                if (o instanceof String) {
                    String query = (String) o;
                    handleQuery(query);
                }
                else {
                    block();
                }
            }
        });
    }

    private void handleQuery(String query) {
        String response = menu.containsKey(query)
                ? "Ingredients for " + query + ": " + menu.get(query)
                : "Coffee \"" + query + "\" does not exist in the menu.";
        gui.showResponse(response);
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
