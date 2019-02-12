package com.tuandai.amqp.rabbit.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tuandai.amqp.rabbit.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liusongrui on 2019/1/25.
 */
public class SendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendService.class);

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        /*连接可以抽象为socket连接，为我们维护协议版本信息和协议证书等。
        这里我们连接上了本机的消息服务器实体（localhost）。
        如果我们想连接其它主机上的RabbitMQ服务，只需要修改一下主机名或是IP就可以了*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constant.host);
        factory.setPort(Constant.port);
        factory.setVirtualHost(Constant.vHost);
        factory.setUsername(Constant.username);
        factory.setPassword(Constant.password);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /*接下创建channel（信道），这是绝大多数API都能用到的。
        为了发送消息，你必须要声明一个消息消息队列，然后向该队列里推送消息*/
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "HELLO WORLD!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        LOGGER.info("[X] Sent {}", message);

         /*声明一个幂等的队列（只有在该队列不存在时，才会被创建）。
         消息的上下文是一个字节数组，你可以指定它的编码。*/
        channel.close();
        connection.close();

    }

}
