package com.lxx.ws;

import com.alibaba.fastjson.JSON;
import com.lxx.config.GetHttpSessionConfig;
import com.lxx.pojo.ChatRecord;
import com.lxx.service.ChatRecordService;
import com.lxx.utils.MessageUtils;
import com.lxx.ws.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();
    
    // 用来存储每个Session对应的用户名
    private static final Map<Session, String> sessionUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;
    private String username; // 当前连接的用户名
    
    // 注意：由于WebSocket的特殊性，不能直接使用@Autowired注入，需要使用静态成员
    private static ChatRecordService chatRecordService;
    
    @Autowired
    public void setChatRecordService(ChatRecordService service) {
        ChatEndpoint.chatRecordService = service;
    }

    /**
     * 建立websocket连接后，被调用
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //1，将websocket.Session进行保存
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.username = (String) this.httpSession.getAttribute("user");
        
        // 保存用户名和session的对应关系
        onlineUsers.put(this.username, session);
        sessionUsers.put(session, this.username);
        
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
    public void onMessage(String message, Session session) {
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
            Session toSession = onlineUsers.get(toName);
            
            // 获取当前发送消息的用户名
            String fromName = sessionUsers.get(session);
            
            // {"isSystem":false,"fromName":"张三","message"："你好"}
            String msg1 = MessageUtils.getMessage(false, fromName, sendMsg);
            if (toSession != null) {
                toSession.getBasicRemote().sendText(msg1);
            }
            
            // 保存聊天记录到数据库
            if (chatRecordService != null) {
                ChatRecord chatRecord = new ChatRecord();
                chatRecord.setFromName(fromName);
                chatRecord.setToName(toName);
                chatRecord.setMessage(sendMsg);
                chatRecord.setSendTime(new Date());
                chatRecord.setMessageType(0); // 0表示文本消息
                chatRecord.setIsRead(0); // 0表示未读
                chatRecordService.saveChatRecord(chatRecord);
            }
        } catch (Exception e) {
            //记录日志
            e.printStackTrace();
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
        String username = sessionUsers.get(session);
        if (username != null) {
            onlineUsers.remove(username);
            sessionUsers.remove(session);
            
            //2,通知其他所有的用户，当前用户下线了
            String message = MessageUtils.getMessage(true, null, getFriends());
            broadcastAllUsers(message);
        }
    }
}
