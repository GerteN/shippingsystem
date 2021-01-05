package org.project9.shipping.service;

import com.google.gson.Gson;
import org.project9.shipping.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shipping.Shipping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public Optional<Shipping> getShipping(Integer shippingId, Integer userId, HttpServletResponse response, HttpServletRequest request) {
        ShippingHttpErrors httpErrors = new ShippingHttpErrors();
        if(String.valueOf(response.getStatus()).startsWith("40")) {
            System.out.println("ciao");
            httpErrors.setTimestamp(Instant.now().getEpochSecond());
            httpErrors.setSourceIp(request.getRemoteAddr());
            httpErrors.setRequest(request.getRequestURI().concat(" + ").concat(request.getMethod()));
            httpErrors.setError(String.valueOf(response.getStatus()));
            kafkaTemplate.send(topicLogging, new Gson().toJson(httpErrors));
            return null;
        }
        if(!repository.existsById(shippingId)) {
            httpErrors.setTimestamp(Instant.now().getEpochSecond());
            httpErrors.setSourceIp(request.getRemoteAddr());
            httpErrors.setRequest(request.getRequestURI().concat(" + ").concat(request.getMethod()));
            httpErrors.setError(String.valueOf(response.getStatus()));
            kafkaTemplate.send(topicLogging, "http_errors", new Gson().toJson(httpErrors));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(userId.equals(0))
            return repository.findById(shippingId);
        return Optional.ofNullable(repository.findByShippingIdAndUserId(shippingId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    /*public Optional<Shipping> getShipping(Integer shippingId, Integer userId, HttpServletResponse response, HttpServletRequest request) {
        ShippingHttpErrors httpErrors = new ShippingHttpErrors();
        try {
            if (String.valueOf(response.getStatus()).startsWith("40")) {
                httpErrors.setRequest(request.getPathInfo().concat(" + ").concat(request.getMethod()));
                httpErrors.setTimestamp(Instant.now().getEpochSecond());
                httpErrors.setSourceIp(request.getRemoteAddr());
                httpErrors.setError(String.valueOf(response.getStatus()));
                kafkaTemplate.send(topicLogging, new Gson().toJson(httpErrors));
            }

            if (!repository.existsById(shippingId))
                if (String.valueOf(response.getStatus()).startsWith("40")) {

                }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            if (userId.equals(0))
                return repository.findById(shippingId);
            return Optional.ofNullable(repository.findByShippingIdAndUserId(shippingId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        } catch (Exception e) {
            if (String.valueOf(response.getStatus()).startsWith("50")) {
                httpErrors.setRequest(request.getPathInfo().concat(" + ").concat(request.getMethod()));
                httpErrors.setTimestamp(Instant.now().getEpochSecond());
                httpErrors.setSourceIp(request.getRemoteAddr());
                httpErrors.setError(e.getStackTrace().toString());
                kafkaTemplate.send(topicLogging, new Gson().toJson(httpErrors));
            }

        }
        return null;
    }*/

    public Page<Shipping> getAll(Integer userId, Pageable pageable) {
        if(userId.equals(0))
            return repository.findAll(pageable);
        if(repository.findByUserId(userId, pageable).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return repository.findByUserId(userId, pageable);
    }

    public Shipping addShipping(ShippingCreateRequest shippingRequest) {
        Shipping shipping = new Shipping();
        shipping.setShippingAddress(shippingRequest.getShippingAddress());
        shipping.setOrderId(shippingRequest.getOrderId());
        shipping.setUserId(shippingRequest.getUserId());
        shipping.setProducts(shippingRequest.getProducts());
        shipping.setStatus("default initial");
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

    public void updateStatusInvoicing(ShippingUpdateInvoicing updateInvoicing) {
        Optional<Shipping> shipping = repository.findByOrderIdAndUserId(updateInvoicing.getOrderId(), updateInvoicing.getUserId());
        if(shipping.isPresent()) {
            Shipping s = shipping.get();
            s.setStatus("TODO");
            while(true) {
                int DDT = (int)(Math.random()*10000);
                if(!repository.findByDDT(DDT).isPresent()) {
                    s.setDDT(DDT);
                    repository.save(s);
                    break;
                }
            }
        }
        else {
            updateInvoicing.setTimestamp(Instant.now().getEpochSecond());
            kafkaTemplate.send(topicLogging, "shipping_unavailable", new Gson().toJson(updateInvoicing));
        }
    }
}
