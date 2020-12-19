package project9.shipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import project9.shipping.data.ShippingRepository;
import shipping.Shipping;

import javax.transaction.Transactional;

@Service
@Transactional
public class ShippingService {

    @Autowired
    ShippingRepository repository;


    public Shipping getShipping(Integer userId){
        return repository.findById(userId).get();
    }
}
