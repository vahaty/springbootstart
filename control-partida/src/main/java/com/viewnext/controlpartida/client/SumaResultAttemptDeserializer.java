package com.viewnext.controlpartida.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.viewnext.controlpartida.client.dto.SumaResultAttempt;

/**
 * Deserializes an attempt coming from the Multiplication microservice into the
 * Gamification's representation of an attempt.
 */
public class SumaResultAttemptDeserializer extends JsonDeserializer<SumaResultAttempt> {

	@Override
	public SumaResultAttempt deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		return new SumaResultAttempt(node.get("user").get("alias").asText(),
				node.get("suma").get("factorA").asInt(), node.get("suma").get("factorB").asInt(),
				node.get("resultAttempt").asInt(), node.get("correct").asBoolean());
	}
}
