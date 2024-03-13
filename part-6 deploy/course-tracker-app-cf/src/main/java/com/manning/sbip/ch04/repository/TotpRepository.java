package com.manning.sbip.ch04.repository;

import com.manning.sbip.ch04.model.TotpDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotpRepository extends CrudRepository<TotpDetails, Long> {
    void deleteByUsername(String username);

    TotpDetails findByUsername(String username);
}
