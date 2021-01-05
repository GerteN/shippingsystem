package org.project9.shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.project9.shipping.service.ShippingService;
import shipping.Shipping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@RequestMapping(path="")
public class ShippingController {

    @Autowired
    ShippingService service;

    @GetMapping(value="/shipping/{shippingId}")
    public @ResponseBody
    Optional<Shipping> getShipping(@PathVariable Integer shippingId, @RequestHeader("X-User-ID") Integer userId, HttpServletResponse response, HttpServletRequest request) {
        return service.getShipping(shippingId, userId, response, request);
    }

    @GetMapping(value="/shippings")
    public @ResponseBody
    Page<Shipping> getAll(@RequestHeader("X-User-ID") Integer userId, Pageable pageable) {
        return service.getAll(userId, pageable);
    }

    @GetMapping(value = "/ping")
    public @ResponseBody
    String pingAck(){
        return service.pingAck();
    }



}
