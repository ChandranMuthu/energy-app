package com.example.crudwithvaadin;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MqttMessageHandler implements MessageHandler {

    private static final Logger LOGGER = getLogger(MqttMessageHandler.class);

    private final EnergyOutputRepository energyOutputRepository;

    public MqttMessageHandler(final EnergyOutputRepository energyOutputRepository) {
        this.energyOutputRepository = energyOutputRepository;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        System.out.println(message.getPayload());
        saveEnergyOutput(message.getPayload().toString());
    }

    private void saveEnergyOutput(final String message) {
        final Gson gson = new Gson();
        final JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        final String transactionId = jsonObject.get("ts").getAsString();
        final List<EnergyOutput> existingEnergyList = energyOutputRepository.findByTransactionId(transactionId);
        if(existingEnergyList.size() > 0)
        {
            LOGGER.info("Data already exists in the database with the transaction id {} ",transactionId);
            return;
        }
        final LocalDateTime deviceDateTime = getDateFromString(jsonObject.get("datetime").getAsString());
        for (int i = 1; i <= 25; i++) {
            final String memberName = Integer.toString(i);
            if (jsonObject.has(memberName)) {
                LOGGER.info("Parsing device - {} ", memberName);
                final EnergyOutput energyOutput = new EnergyOutput();
                energyOutput.setTransactionId(jsonObject.get("ts").getAsString());
                energyOutput.setDeviceIndex(memberName);
                energyOutput.setDeviceDateTime(deviceDateTime);
                final JsonObject childObject = jsonObject.get(memberName).getAsJsonObject();
                energyOutput.setDeviceType(childObject.get("device_type").getAsString());
                energyOutput.setTransactionConfig(childObject.get("txn").getAsString());
                energyOutput.setTransactionResponseCode(childObject.get("res").getAsString());
                energyOutput.setDataLength(childObject.get("datalen").getAsInt());
                final JsonArray data = childObject.get("data").getAsJsonArray();
                if (data.size() > 0) {
                    energyOutput.setInputReading(StringUtils.join(data));
                }
                System.out.println(energyOutput.toString());
                energyOutputRepository.save(energyOutput);
            }
        }
    }

    private LocalDateTime getDateFromString(final String dateInString) {
        LocalDateTime localDateTime;
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy kk:mm:ss");
            localDateTime = LocalDateTime.parse(dateInString, formatter);
        } catch (Exception e) {
            LOGGER.warn("Error occurred while parsing the datetime. setting the default datetime");
            localDateTime = LocalDateTime.now();
        }
        return localDateTime;
    }
}
