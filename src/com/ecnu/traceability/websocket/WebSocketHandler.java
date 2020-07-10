package com.ecnu.traceability.websocket;

import com.alibaba.fastjson.JSONObject;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 消息处理控制器
 */
@Controller("websocket")
@RequestMapping("websocket")
public class WebSocketHandler extends TextWebSocketHandler {

    //    private List<WebSocketSession> sessionList=new ArrayList<>();
    private Set<String> userSet = new HashSet<>();
    private Long onlineNum = 0l;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("========链接========");
        synchronized (onlineNum) {
            onlineNum++;
        }
//        sessionList.add(session);
        OneNETDevice.pushOnlineDeviceNum("598576209", onlineNum);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("========链接断开========");
        synchronized (onlineNum) {
            if (onlineNum > 0)
                onlineNum--;
        }
//        sessionList.remove(session);
        OneNETDevice.pushOnlineDeviceNum("598576209", onlineNum);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String clientMessage = message.getPayload();
        //向oneNET发送在线人数+
//        JSONObject json = JSONObject.parseObject(clientMessage);
//        userSet.add(json.getString("user_id"));
//        OneNETDevice.pushOnlineDeviceNum("598576209", userSet.size());

        TextMessage returnMessage = new TextMessage(clientMessage + " recieved at server");
        session.sendMessage(returnMessage);
    }
}
