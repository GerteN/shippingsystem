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
import shipping.DDT;
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
    DDTRepository ddtRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topicLogging}")
    private String topicLogging;

    public Optional<Shipping> getShipping(Integer shippingId, Integer userId, HttpServletResponse response, HttpServletRequest request) {
        if(!repository.existsById(shippingId)) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(userId.equals(0))
            return repository.findById(shippingId);
        return Optional.ofNullable(repository.findByShippingIdAndUserId(shippingId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Page<Shipping> getAll(Integer userId, Pageable pageable, HttpServletResponse response, HttpServletRequest request) {
        Page<Shipping> shipping;
        if(userId.equals(0))
            shipping = repository.findAll(pageable);
        else
            shipping = repository.findByUserId(userId, pageable);
        if(shipping.getTotalPages() < pageable.getPageNumber()+1) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "400");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if(shipping.isEmpty()) {
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "404");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shipping;
    }

    public void sendKafkaError(Long timestamp, String sourceIp, String request, String error){
        ShippingHttpErrors httpErrors = new ShippingHttpErrors();
        httpErrors.setTimestamp(timestamp);
        httpErrors.setSourceIp(sourceIp);
        httpErrors.setRequest(request);
        httpErrors.setError(error);
        kafkaTemplate.send(topicLogging, "http_errors", new Gson().toJson(httpErrors));
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
            DDT ddt = new DDT();
            ddtRepository.save(ddt);

            DDT Ddt = ddtRepository.findTopByOrderByIdDesc().get();
            System.out.println(Ddt);
            if(Ddt != null)
                s.setDDT(Ddt);
        }
        else {
            updateInvoicing.setTimestamp(Instant.now().getEpochSecond());
            kafkaTemplate.send(topicLogging, "shipping_unavailable", new Gson().toJson(updateInvoicing));
        }
    }

    public String pingAck(Integer userId, HttpServletRequest request, HttpServletResponse response){
        if(userId != 0){
            sendKafkaError(Instant.now().getEpochSecond(), request.getRemoteAddr(), request.getRequestURI().concat(" ").concat(request.getMethod()), "403");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return "'serviceStatus': 'up', 'dbStatus': 'up'";
    }
}
