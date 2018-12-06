package com.diaspark.csvtomongo.repository;

import org.springframework.data.repository.CrudRepository;

import com.diaspark.csvtomongo.model.CustomResultList;

public interface CommaSeparatorRepository extends CrudRepository<CustomResultList, Integer>{

}
