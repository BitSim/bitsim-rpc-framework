package github.bitsim;

/**
 * @author BitSim
 * @version v1.0.0
 **/
public class NewMyServiceImpl implements NewMyService {

    @Override
    public boolean login(User user) {
        System.out.println(user.getUsername() + "访问了XXX网页");
        System.out.println("正在登录中......");
        System.out.println("登录成功");
        return true;
    }
}
