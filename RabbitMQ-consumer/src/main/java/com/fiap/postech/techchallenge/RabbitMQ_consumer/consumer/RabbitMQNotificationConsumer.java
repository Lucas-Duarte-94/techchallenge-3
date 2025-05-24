package com.fiap.postech.techchallenge.RabbitMQ_consumer.consumer;

import com.fiap.postech.techchallenge.RabbitMQ_consumer.dto.AppointmentNotificationDTO;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQNotificationConsumer {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQNotificationConsumer.class);

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void processAppointment(AppointmentNotificationDTO notification, Message message, Channel channel) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            logger.info("📥 RECEBENDO NOTIFICAÇÃO do RabbitMQ");
            logger.info("================================================");
            logger.info("ID da Consulta: {}", notification.id());
            logger.info("Paciente: {}", notification.name());
            logger.info("Email: {}", notification.email());
            logger.info("Data/Hora: {}", notification.startDateTime());
            logger.info("Médico: {}", notification.doctorName());
            logger.info("Tipo: {}", notification.type());
            logger.info("================================================");

            // Confirma o processamento da mensagem
            channel.basicAck(deliveryTag, false);
            logger.info("✅ NOTIFICAÇÃO PROCESSADA COM SUCESSO!");

        } catch (Exception e) {
            logger.error("❌ ERRO ao processar notificação: {}", e.getMessage(), e);

            // Rejeita a mensagem e envia para Dead Letter Queue
            channel.basicNack(deliveryTag, false, false);
            logger.error("🗑️ Mensagem enviada para Dead Letter Queue");
        }
    }
}
