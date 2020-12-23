package org.project9.shipping.data;

import org.springframework.data.repository.CrudRepository;
import shipping.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends CrudRepository<Shipping,Integer>{

    public Optional<Shipping> findByShippingIdAndUserId(Integer shippingId, Integer userId);

    public List<Shipping> findByUserId(Integer userId);

}
