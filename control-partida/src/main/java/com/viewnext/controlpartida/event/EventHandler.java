package com.viewnext.controlpartida.event;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.viewnext.controlpartida.service.GameService;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Component
class EventHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventHandler.class);

    private GameService gameService;

    EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${suma.queue}")
    void handleMultiplicationSolved(final SumaSolvedEvent event) {
        log.info("SUma Solved Event received: {}", event.getSumaResultAttemptId());
        try {
            gameService.newAttemptForUser(event.getUserId(),
                    event.getSumaResultAttemptId(),
                    event.isCorrect());
        } catch (final Exception e) {
            log.error("Error when trying to process MultiplicationSolvedEvent", e);
            // Avoids the event to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
