package github.bitsim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class LibBookingImpl {
    private final Logger logger= LoggerFactory.getLogger(NewMyServiceImpl.class);
    public String login(User user) {
        logger.info(user.getUsername() + "访问了图书馆网页");
        logger.info("图书馆登录中......");
        logger.info("登录成功");
        return "图书馆";
    }
}
