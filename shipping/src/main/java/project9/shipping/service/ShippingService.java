package project9.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import project9.shipping.data.ShippingRepository;
import shipping.Shipping;

@Service
public class ShippingService {

    @Autowired
    ShippingRepository repository;


    public Shipping getShipping(Integer UserId){ return repository.findById(UserId).get();}
}
