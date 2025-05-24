package com.fiap.postech.techchallenge.appointment_microservice.service;

import com.fiap.postech.techchallenge.appointment_microservice.dtos.AppointmentNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationProducerService.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange:appointment.exchange}")
    private String exchangeName;

    @Value("${app.rabbitmq.routing-key:appointment.notification}")
    private String routingKey;

    public NotificationProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAppointmentNotification(AppointmentNotificationDTO notification) {
        try {
            logger.info("📤 ENVIANDO NOTIFICAÇÃO para RabbitMQ");
            logger.info("Exchange: {}, Routing Key: {}", exchangeName, routingKey);
            logger.info("Dados: ID={}, Paciente={}, Email={}",
                    notification.id(), notification.name(), notification.email());

            rabbitTemplate.convertAndSend(exchangeName, routingKey, notification);

            logger.info("✅ NOTIFICAÇÃO ENVIADA COM SUCESSO!");
            logger.info("================================================");

        } catch (Exception e) {
            logger.error("❌ ERRO ao enviar notificação para RabbitMQ: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao enviar notificação via RabbitMQ", e);
        }
    }
}
