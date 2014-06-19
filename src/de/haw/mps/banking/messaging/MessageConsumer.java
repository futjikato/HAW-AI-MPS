package de.haw.mps.banking.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import de.haw.mps.MpsLogger;

import java.io.IOException;

public final class MessageConsumer extends Thread {

    private Connection connection;

    private QueueingConsumer consumer;

    protected static final String PAYMENT_QUEUE_NAME = "haspaa-payments";

    public MessageConsumer() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");

        connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(PAYMENT_QUEUE_NAME, false, false, false, null);

        consumer = new QueueingConsumer(channel);
        channel.basicConsume(PAYMENT_QUEUE_NAME, true, consumer);
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                byte[] msgBytes = delivery.getBody();

                MessageService.getInstance().add(new BankMessage(msgBytes));
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            }
        }

        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MpsLogger.getLogger().info("Banking message consumer stops.");
    }
}
