package org.project9.shipping.data;

import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import shipping.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends PagingAndSortingRepository<Shipping, Integer> {

    public Optional<Shipping> findByShippingIdAndUserId(Integer shippingId, Integer userId);
    public Page<Shipping> findByUserId(Integer userId, Pageable pageable);
    public Optional<Shipping> findByOrderId(Integer orderId);
    public Optional<Shipping> findByOrderIdAndUserId(Integer OrderId, Integer userId);



}
