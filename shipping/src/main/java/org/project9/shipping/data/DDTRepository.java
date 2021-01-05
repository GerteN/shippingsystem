package org.project9.shipping.data;

import org.springframework.data.repository.CrudRepository;
import shipping.DDT;

import java.util.Optional;

public interface DDTRepository extends CrudRepository<DDT, Integer> {
    public Optional<DDT> findTopByOrderByIdDesc();
}
