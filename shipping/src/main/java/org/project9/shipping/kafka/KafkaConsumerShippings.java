package org.project9.shipping.kafka;

import com.google.gson.Gson;
import org.project9.shipping.data.ShippingUpdateRequest;
import org.project9.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerShippings {

    @Autowired
    ShippingService service;

    @KafkaListener(topics = "${kafkaTopic}", groupId = "${kafkaGroup}")
    public void listenShippingTopic(String message) {
        if (message != null) {
            ShippingUpdateRequest updateRequest = new Gson().fromJson(message, ShippingUpdateRequest.class);
            service.updateStatus(updateRequest);
        }
    }

}
