package github.bitsim.registry;

import github.bitsim.enumerate.RpcErrorMessageEnum;
import github.bitsim.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class DefaultServiceRegistry implements ServiceRegistry {
    private final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);
    private final ConcurrentMap<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registryService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();

        if (registryService.contains(serviceName)) return;

        registryService.add(serviceName);

        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_MISSING_INTERFACE, serviceName + ":没有实现的接口");
        }

        //将不同接口名称与业务一一映射
        Arrays.stream(interfaces).forEach(clazz -> serviceMap.put(clazz.getCanonicalName(), service));

        logger.info("添加业务:{},该业务实现了接口:{}", serviceName, interfaces);
    }

    @Override
    public synchronized Object getService(String interfaceName) {
        Object service = serviceMap.get(interfaceName);
        if (Objects.isNull(service)) {
            throw new RpcException(RpcErrorMessageEnum.INTERFACE_NO_FOUND_SERVICE, interfaceName + ":接口没有相应的业务实现");
        }
        return service;
    }
}
