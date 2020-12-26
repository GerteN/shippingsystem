package org.project9.shipping.kafka;

import com.google.gson.Gson;
import org.project9.shipping.data.ShippingCreateRequest;
import org.project9.shipping.data.ShippingUpdateInvoicing;
import org.project9.shipping.data.ShippingUpdateRequest;
import org.project9.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerShippings {

    @Autowired
    ShippingService service;

    @KafkaListener(topics = "${topicOrders}", groupId = "${kafkaGroup}")
    public void listenShippingTopic(String message) {
        if (message != null && !message.isEmpty()){
            System.out.println(message);
            if(message.contains("status")){
                ShippingUpdateRequest updateStatus = new Gson().fromJson(message, ShippingUpdateRequest.class);
                service.updateStatus(updateStatus);
            }
            else{
                ShippingCreateRequest createShipping = new Gson().fromJson(message, ShippingCreateRequest.class);
                service.addShipping(createShipping);
            }
        }
    }

    @KafkaListener(topics = "${topicInvoicing}", groupId = "${kafkaGroup}")
    public void listenShippingInvoice(String message){
        if (message != null && !message.isEmpty()){
            ShippingUpdateInvoicing updateStatus = new Gson().fromJson(message, ShippingUpdateInvoicing.class);
            service.updateStatusInvoicing(updateStatus);
        }
    }

}
