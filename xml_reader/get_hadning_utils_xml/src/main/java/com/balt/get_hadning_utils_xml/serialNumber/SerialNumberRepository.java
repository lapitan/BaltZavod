package com.balt.get_hadning_utils_xml.serialNumber;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialNumberRepository extends CrudRepository<SerialNumber, String> {
}
