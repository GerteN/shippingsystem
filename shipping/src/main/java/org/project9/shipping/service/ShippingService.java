package org.project9.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.project9.shipping.data.ShippingRepository;
import shipping.Shipping;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ShippingService {

    @Autowired
    ShippingRepository repository;

    public Optional<Shipping> getShipping(Integer shippingId, Integer userId){
        if(!repository.existsById(shippingId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(userId.equals(0))
            return repository.findById(shippingId);
        return Optional.ofNullable(repository.findByShippingIdAndUserId(shippingId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Iterable<Shipping> getAll(Integer userId) {
        if(userId.equals(0))
            return repository.findAll();
        if(repository.findByUserId(userId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return repository.findByUserId(userId);
    }

}
