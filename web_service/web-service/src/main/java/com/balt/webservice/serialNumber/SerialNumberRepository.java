package com.balt.webservice.serialNumber;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialNumberRepository extends CrudRepository<SerialNumber, String> {
}
