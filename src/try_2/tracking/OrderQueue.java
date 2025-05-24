package try_2.tracking;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private static final OrderQueue instance = new OrderQueue();

    private static Queue<String> orders = new LinkedList<>();

    private OrderQueue() {}

    public static OrderQueue getInstance() {
        return instance;
    }

    public static synchronized void addOrder(String order) {
        orders.add(order);
    }

    public static synchronized String getNextOrder() {
        return orders.poll();
    }

    public static synchronized boolean hasOrders() {
        return !orders.isEmpty();
    }
}
