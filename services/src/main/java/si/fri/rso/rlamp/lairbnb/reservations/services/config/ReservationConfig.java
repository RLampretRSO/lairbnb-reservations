package si.fri.rso.rlamp.lairbnb.reservations.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("service-properties")
public class ReservationConfig {
    @ConfigValue(value = "external-services.user-service.enabled", watch = true)
    private boolean userServiceEnabled;

    @ConfigValue(value = "external-services.lair-service.enabled", watch = true)
    private boolean lairServiceEnabled;

    @ConfigValue(value = "service.healthy", watch = true)
    private boolean healthy;

    public boolean isUserServiceEnabled() {
        return userServiceEnabled;
    }

    public void setUserServiceEnabled(boolean userServiceEnabled) {
        this.userServiceEnabled = userServiceEnabled;
    }

    public boolean isLairServiceEnabled() {
        return lairServiceEnabled;
    }

    public void setLairServiceEnabled(boolean lairServiceEnabled) {
        this.lairServiceEnabled = lairServiceEnabled;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
