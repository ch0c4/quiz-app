package org.johan.infrastructure.dataAccess.hibernate;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import org.johan.infrastructure.dataAccess.entities.QuizEntity;

@Repository
public interface QuizHibernateRepository extends CrudRepository<QuizEntity, Long> {
}
