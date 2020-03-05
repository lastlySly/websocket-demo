package com.lastlysly.task;

import com.lastlysly.websokcet.WebSocketConnector;
import com.lastlysly.websokcet.WebSocketConnector2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-05 11:07
 **/
@Component
public class ProductExpireTask {
    @Scheduled(fixedRate=2000)
    public void productExpire() throws IOException {

        String[] strs={"Test随机消息 ：30.1123",
                "Test随机消息 ：32.1021",
                "Test随机消息 ：33.1774",
                "Test随机消息 ：33.2372",
                "Test随机消息 ：31.0281",
                "Test随机消息 ：30.0222",
                "Test随机消息 ：32.1322",
                "Test随机消息 ：33.3221",
                "Test随机消息 ：31.2311",
                "Test随机消息 ：32.3112"};

//        WebSocketConnector.broadcast(LocalDateTime.now()+"  sessionId为1的用户（userId写死为sessionId）会看到另一条数据，请打开查看。  Test 消息---->"+ RandomStr(strs));
        /**
         * 测试指定用户
         */
        WebSocketConnector.sendMessage(LocalDateTime.now()+
                "    Test 只发送给userId为1的用户客户端---->"+ RandomStr(strs),"1");

        WebSocketConnector2.sendInfo("23333，这是测试多个websocket监听服务端");

    }

    /**
     * 随机返回字符串数组中的字符串
     */
    public static String RandomStr(String[] strs){
        int random_index = (int) (Math.random()*strs.length);
        return strs[random_index];
    }
}
