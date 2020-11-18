package org.johan.infrastructure.dataAccess.hibernate;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import org.johan.infrastructure.dataAccess.entities.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerHibernateRepository extends CrudRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);
}
