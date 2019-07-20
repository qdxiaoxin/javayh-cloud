package com.javayh.service.send;

import com.javayh.util.LogOut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.javayh.util.StaticNumber.JAVAYH_EXCHANGE;

/**
 * @author Dylan Yang
 * @Description: SendFanoutService
 * @Title: SendFanoutService
 * @ProjectName javayh-cloud
 * @date 2019/7/20 12:10
 */
@Slf4j
@Component
public class SendFanoutService implements SendService{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送主题
     * @param message 内容
     */
    @Override
    public void sendMessage(Object message) {
        rabbitTemplate.convertAndSend(JAVAYH_EXCHANGE,"",message);
        log.info("send fanout success -> {}",message);

    }

    /**
     * 消息发送回调函数
     * @param correlationData
     * @param isSendSuccess
     * @param arg
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean isSendSuccess, String arg) {
        LogOut.logOut(correlationData,isSendSuccess,arg);
    }
}

