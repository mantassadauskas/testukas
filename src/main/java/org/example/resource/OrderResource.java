import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.Trade;
import org.example.entity.User;
import org.example.enums.OrderType;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
import org.example.repository.TradeRepository;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Map;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    public OrderResource() {
        System.out.println("OrderResource instance created");
    }

    static {
        System.out.println("OrderResource class loaded");
    }

    @Inject
    OrderRepository orderRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    TradeRepository tradeRepository;

    @RolesAllowed({"USER"})

    @POST
    @Transactional
    public Response createOrder(OrderDTO dto, @Context SecurityContext ctx) {
        System.out.println("createOrder method called");

        String username = ctx.getUserPrincipal().getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        Order order = new Order();
        order.item = dto.item;
        order.quantity = dto.quantity;
        order.price = dto.price;
        order.type = OrderType.valueOf(dto.type.toUpperCase());
        order.user = user;
        order.createdAt = LocalDateTime.now();

        orderRepository.persist(order);
        System.out.println("dto.item" + dto.item);
        System.out.println(Response.ok(order));


        // Check for matching order
        OrderType oppositeType = order.type == OrderType.BUY ? OrderType.SELL : OrderType.BUY;
        Optional<Order> match = orderRepository.find("item = ?1 and type = ?2 and quantity = ?3",
                order.item, oppositeType, order.quantity).firstResultOptional();

        if (match.isPresent()) {
            Order matchedOrder = match.get();

            // Create trade
            Trade trade = new Trade();
            trade.buyer = (order.type == OrderType.BUY) ? user : matchedOrder.user;
            trade.seller = (order.type == OrderType.SELL) ? user : matchedOrder.user;
            trade.buyOrder = (order.type == OrderType.BUY) ? order : matchedOrder;
            trade.sellOrder = (order.type == OrderType.SELL) ? order : matchedOrder;
            trade.item = order.item;
            trade.quantity = order.quantity;
            trade.timestamp = LocalDateTime.now();

            tradeRepository.persist(trade);

            order.transaction = trade;
            matchedOrder.transaction = trade;

            orderRepository.persist(order);
            orderRepository.persist(matchedOrder);

            return Response.ok(Map.of("status", "Matched", "tradeId", trade.id)).build();
        }

        return Response.ok(order).build();
    }
}
