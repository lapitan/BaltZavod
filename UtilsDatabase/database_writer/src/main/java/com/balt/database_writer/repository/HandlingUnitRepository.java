package com.balt.database_writer.repository;

import org.springframework.data.repository.CrudRepository;

import com.balt.database_writer.entity.HandlingUnit;
import com.balt.database_writer.entity.HandlingUnitId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HandlingUnitRepository extends CrudRepository<HandlingUnit, HandlingUnitId> {

    // List<HandlingUnit> findByHandlingUnitId(HandlingUnitId handlingUnitId);

    Optional<HandlingUnit> findByHandlingUnitId(HandlingUnitId handlingUnitId);

    // @Query("Select u")
    // public Optional<HandlingUnit> findByCompositeId(HandlingUnitId handlingUnitId);
    
}
