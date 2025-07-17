package mantas.filter;
import jakarta.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class RateLimitingFilter implements ContainerRequestFilter {

    private final Map<String, List<Long>> requestCounts = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 5; // 5 requests per minute
    private static final long TIME_WINDOW = 60000; // 1 minute

    @Override
        public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        // Only apply rate limiting to login endpoint
        if (!path.equals("auth/login")) {
            return;
        }

        String clientIp = getClientIp(requestContext);
        long now = System.currentTimeMillis();

        requestCounts.compute(clientIp, (ip, timestamps) -> {
            if (timestamps == null) {
                timestamps = new ArrayList<>();
            }

            // Remove old timestamps
            timestamps.removeIf(timestamp -> now - timestamp > TIME_WINDOW);

            if (timestamps.size() >= MAX_REQUESTS) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Too many requests");

                requestContext.abortWith(
                    Response.status(Response.Status.TOO_MANY_REQUESTS)
                        .entity(errorResponse)
                        .build()
                );
                return timestamps;
            }

            timestamps.add(now);
            return timestamps;
        });
    }

    private String getClientIp(ContainerRequestContext requestContext) {
        String xForwardedFor = requestContext.getHeaderString("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = requestContext.getHeaderString("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return "unknown";
    }
}
