package com.viewnext.suma.event;

import com.viewnext.suma.configuration.SumaQueueConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles the communication with the Event Bus.
 */
@Component
public class EventDispatcher {

	private final RabbitTemplate rabbitTemplate;

	private final SumaQueueConfiguration sumaConfiguration;

	@Autowired
	EventDispatcher(final RabbitTemplate rabbitTemplate, final SumaQueueConfiguration sumaConfiguration) {
		this.rabbitTemplate = rabbitTemplate;
		this.sumaConfiguration = sumaConfiguration;
	}

	public void send(final SumaSolvedEvent multiplicationSolvedEvent) {
		rabbitTemplate.convertAndSend(sumaConfiguration.getExchange(), sumaConfiguration.getSolvedKey(),
				multiplicationSolvedEvent);
	}
}
