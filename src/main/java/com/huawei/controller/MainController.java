package com.huawei.controller;


import com.huawei.service.ConsumerService;
import com.huawei.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by dllo on 17/11/29.
 */
@Controller
public class MainController {

    @Resource(name = "queueDestination")
    private Destination queueDestination;

    @Resource
    private ProducerService producerService;

    @Resource
    private ConsumerService consumerService;


    @RequestMapping("home")
    public String mainPage() {

        return "home";
    }

    @RequestMapping("/producer")
    public String producerPage() {
        return "producer";
    }

    @RequestMapping("consumer")
    public String consumerPage(Model model) throws JMSException {
        TextMessage textMessage = consumerService.receive(queueDestination);
        if (textMessage != null) {
            model.addAttribute("textMessage", textMessage.getText());
        }else {
            model.addAttribute("textMessage", "没有新消息");
        }
        return "consumer";
    }

    @RequestMapping("/sendmsg")
    public String sendMsg(@RequestParam("message") String msg) {
        System.out.println("---->消息发送到jms<----");
        producerService.sendMessage(queueDestination, msg);
        return "home";
    }
}
