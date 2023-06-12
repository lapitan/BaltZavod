package com.balt.database_writer.messageConsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.balt.database_writer.messageParser.MessageParser;
import com.balt.database_writer.service.HandlingUnitService;

@Component
@EnableRabbit
public class MessageConsumer {

    @Autowired
    MessageParser messageParser;

    @RabbitListener(queues = "requestToDb")
    public void processQueue(String message){
        messageParser.parse(message);
    }

    
}
