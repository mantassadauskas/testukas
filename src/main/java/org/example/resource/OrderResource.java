import org.example.dto.OrderDTO;
import org.example.entity.Order;
import org.example.entity.User;
import org.example.repository.OrderRepository;
import org.example.repository.UserRepository;
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
        order.user = user;

        orderRepository.persist(order);
        System.out.println("dto.item" + dto.item);
        System.out.println(Response.ok(order));

        return Response.ok(order).build();
    }
}
