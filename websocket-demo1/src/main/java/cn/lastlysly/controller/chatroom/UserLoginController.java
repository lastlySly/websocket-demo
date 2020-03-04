package cn.lastlysly.controller.chatroom;

import cn.lastlysly.pojo.UserSheet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-28 17:19
 **/
@Controller
public class UserLoginController {

    //模拟数据库用户的数据
    public static Map<String, String> userMap = new HashMap<String, String>();
    static{
        userMap.put("jack", "111");
        userMap.put("mary", "222");
        userMap.put("tom", "333");
        userMap.put("lastly", "444");
        userMap.put("汐", "123456");
    }

    //在线用户存储
    public static Map<String, UserSheet> onlineUser = new HashMap<>();
    static{
        onlineUser.put("模拟sessionId",new UserSheet("系统管理员","123"));
    }

    /**
     * 登陆
     * @param username
     * @param pwd
     * @param session
     * @return
     */
    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String userLogin( @RequestParam(value="username", required=true)String username,
                             @RequestParam(value="pwd",required=true) String pwd, HttpSession session) {

        String password = userMap.get(username);
        if (pwd.equals(password)) {
            UserSheet user = new UserSheet(username, pwd);
            String sessionId = session.getId();
            onlineUser.put(sessionId, user);
            System.out.println("用户"+ user.getUsername() + "登陆成功，sessionId为" + sessionId);
            return "redirect:/chatroom/chat.html";
        } else {
            return "redirect:/chatroom/error.html";
        }

    }

}
