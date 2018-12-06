package com.diaspark.csvtomongo.service;

import com.diaspark.csvtomongo.model.CustomResultList;

public interface CommaSeparatorService {

	public CustomResultList findById(String orgId, int pageNum, long count);

}
