package com.lxx.ws;

import com.alibaba.fastjson.JSON;
import com.lxx.config.GetHttpSessionConfig;
import com.lxx.utils.MessageUtils;
import com.lxx.ws.pojo.Message;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的Session对象
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    /**
     * 建立websocket连接后，被调用
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //1，将websocket.Session进行保存
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String user = (String) this.httpSession.getAttribute("user");
        onlineUsers.put(user, session);
        //2，广播消息。需要将登陆的所有的用户推送给所有的用户
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }

    public Set getFriends() {
        Set<String> set = onlineUsers.keySet();
        return set;
    }

    private void broadcastAllUsers(String message) {
        try {
            //遍历map集合
            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
            for (Map.Entry<String, Session> entry : entries) {
                //获取到所有用户对应的session对象
                Session session = entry.getValue();
                //发送消息
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            //记录日志
        }
    }

    /**
     * 浏览器发送消息到服务端，该方法被调用
     * <p>
     * 张三  -->  李四
     * 客户端 --> 服务端  {"toName":"李四","message":"你好"}
     * 服务端 --> 客户端  {"isSystem":false,"fromName":"张三","message"："你好"}
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            //将消息推送给指定的用户
            // {"toName":"李四","message":"你好"}
            Message msg = JSON.parseObject(message, Message.class);
            //获取 消息接收方的用户名
            // "李四"
            String toName = msg.getToName();
            // "你好"
            String sendMsg = msg.getMessage();
            //获取消息接收方用户对象的websocket.Session对象
            Session session = onlineUsers.get(toName);
            // 发送消息给接收方
            // "张三"
            String user = (String) this.httpSession.getAttribute("user");
            // {"isSystem":false,"fromName":"张三","message"："你好"}
            String msg1 = MessageUtils.getMessage(false, user, sendMsg);
            session.getBasicRemote().sendText(msg1);
        } catch (Exception e) {
            //记录日志
        }
    }

    /**
     * 断开 websocket 连接时被调用
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        //1,从onlineUsers中剔除当前用户的session对象
        String user = (String) this.httpSession.getAttribute("user");
        onlineUsers.remove(user);
        //2,通知其他所有的用户，当前用户下线了
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }
}
