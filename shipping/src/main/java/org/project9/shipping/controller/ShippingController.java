package org.project9.shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.project9.shipping.service.ShippingService;
import shipping.Shipping;

import java.util.Optional;

@Controller
@RequestMapping(path="/ss")
public class ShippingController {

    @Autowired
    ShippingService service;
    
    @GetMapping(value="/shipping/{shippingId}")
    public @ResponseBody
    Optional<Shipping> getShipping(@PathVariable Integer shippingId, @RequestHeader("X-User-ID") Integer userId){
        return service.getShipping(shippingId, userId);
    }

    /*
    @GetMapping(value="/shippings")
    public @ResponseBody
    Iterable<Shipping> getAll(){
        return service.getAll();
    }
    */
    
}
