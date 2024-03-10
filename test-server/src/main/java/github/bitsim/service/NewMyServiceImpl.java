package github.bitsim.service;

import github.bitsim.NewMyService;
import github.bitsim.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NewMyServiceImpl implements NewMyService {
    private final Logger logger= LoggerFactory.getLogger(NewMyServiceImpl.class);
    @Override
    public String login(User user) {
        logger.info(user.getUsername() + "访问了融合门户网页");
        logger.info("融合门户登录中......");
        logger.info("登录成功");
        return "融合门户";
    }
}
