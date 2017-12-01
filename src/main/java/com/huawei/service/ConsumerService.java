package com.huawei.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 消息消费者服务
 */
@Service
public class ConsumerService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    public TextMessage receive(Destination destination) {
//        String defaultDestinationName = jmsTemplate.getDefaultDestinationName();
//        Destination defaultDestination = jmsTemplate.getDefaultDestination();

        TextMessage textMessage = (TextMessage) jmsTemplate.receive(destination);

        if (textMessage != null) {
            try {
                System.out.println("接收到的消息内容是: " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        return textMessage;
    }
}
