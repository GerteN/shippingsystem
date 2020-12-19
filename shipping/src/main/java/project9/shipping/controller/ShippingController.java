package project9.shipping.controller;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project9.shipping.service.ShippingService;
import shipping.Shipping;


@Controller
@RequestMapping(path="/shipping")
public class ShippingController {
    @Autowired
    ShippingService service;
    
    @GetMapping(value="/{id}")
    public @ResponseBody
    Shipping getShipping(@PathVariable Integer UserId){ return service.getShipping(UserId); }
    
}
