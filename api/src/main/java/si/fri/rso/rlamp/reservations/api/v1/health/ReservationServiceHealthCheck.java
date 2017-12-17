package si.fri.rso.rlamp.reservations.api.v1.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.rlamp.lairbnb.reservations.services.config.ReservationConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class ReservationServiceHealthCheck implements HealthCheck {
    @Inject
    private ReservationConfig reservationConfig;

    @Override
    public HealthCheckResponse call() {
        if (reservationConfig.isHealthy()) {
            return HealthCheckResponse.named(ReservationServiceHealthCheck.class.getSimpleName()).up().build();
        }
        return HealthCheckResponse.named(ReservationServiceHealthCheck.class.getSimpleName()).down().build();
    }
}
