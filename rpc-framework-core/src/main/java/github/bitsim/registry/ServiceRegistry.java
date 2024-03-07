package github.bitsim.registry;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public interface ServiceRegistry {
    <T> void register(T service);

    Object getService(String serviceName);
}
