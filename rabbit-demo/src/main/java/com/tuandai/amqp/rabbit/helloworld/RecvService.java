package com.tuandai.amqp.rabbit.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.tuandai.amqp.rabbit.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liusongrui on 2019/1/25.
 */
public class RecvService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RecvService.class);

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.host);
        factory.setPort(Constant.port);
        factory.setVirtualHost(Constant.vHost);
        factory.setUsername(Constant.username);
        factory.setPassword(Constant.password);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        LOGGER.info("[*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            LOGGER.info("[x] Received:{}", message);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }


}
