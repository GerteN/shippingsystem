package org.project9.shipping.service;

import com.google.gson.Gson;
import org.project9.shipping.data.ShippingCreateRequest;
import org.project9.shipping.data.ShippingUpdateInvoicing;
import org.project9.shipping.data.ShippingUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.project9.shipping.data.ShippingRepository;
import shipping.Shipping;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class ShippingService {

    @Autowired
    ShippingRepository repository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topicLogging}")
    private String topicLogging;

    public Optional<Shipping> getShipping(Integer shippingId, Integer userId) {
        if(!repository.existsById(shippingId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(userId.equals(0))
            return repository.findById(shippingId);
        return Optional.ofNullable(repository.findByShippingIdAndUserId(shippingId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Page<Shipping> getAll(Integer userId, Pageable pageable) {
        if(userId.equals(0))
            return repository.findAll(pageable);
        if(repository.findByUserId(userId, pageable).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return repository.findByUserId(userId, pageable);
    }

    public Shipping addShipping(ShippingCreateRequest shippingRequest){
        Shipping shipping = new Shipping();

        shipping.setShippingAddress(shippingRequest.getShippingAddress());
        shipping.setOrderId(shippingRequest.getOrderId());
        shipping.setUserId(shippingRequest.getUserId());
        shipping.setProducts(shippingRequest.getProducts());

        return repository.save(shipping);
    }

    public void updateStatus(ShippingUpdateRequest updateRequest) {
        Optional<Shipping> shipping = repository.findByOrderId(updateRequest.getOrderId());
        if(shipping.isPresent()) {
            Shipping s = shipping.get();
            if(updateRequest.getStatus() != 0)
                s.setStatus("Abort");
            else
                s.setStatus("Ok");
            repository.save(s);
        }
    }

    public void updateStatusInvoicing(ShippingUpdateInvoicing updateInvoicing){
        Optional<Shipping> shipping = repository.findByOrderIdAndUserId(updateInvoicing.getOrderId(), updateInvoicing.getUserId());
        if(shipping.isPresent()){
            Shipping s = shipping.get();
            s.setStatus("TODO");
            while(true){
                int DDT = (int)(Math.random()*((9999-1000)+1))+1000;
                if(!repository.findByDDT(DDT).isPresent()) {
                    s.setDDT(DDT);
                    repository.save(s);
                    break;
                }
            }
        }
        else {
            updateInvoicing.setTimestamp(Instant.now().getEpochSecond());
            kafkaTemplate.send(topicLogging, new Gson().toJson(updateInvoicing));
        }
    }

}
