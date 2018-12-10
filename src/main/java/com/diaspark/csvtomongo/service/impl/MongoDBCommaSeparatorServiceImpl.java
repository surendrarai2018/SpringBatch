package com.diaspark.csvtomongo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.diaspark.csvtomongo.constant.SystemConstants;
import com.diaspark.csvtomongo.model.CustomResultList;
import com.diaspark.csvtomongo.model.Domain;
import com.diaspark.csvtomongo.service.CommaSeparatorService;
import com.diaspark.csvtomongo.util.CriptoUtil;

@Service
public class MongoDBCommaSeparatorServiceImpl implements CommaSeparatorService{

	private MongoTemplate mongoTemplate;
	 
    @Autowired
    MongoDBCommaSeparatorServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
	public CustomResultList findById(String id, int pageNum, Long count) {
		CustomResultList customResultList = new CustomResultList();
		int newPageNum = pageNum + 1;
		String _id = CriptoUtil.encrypt(id);
		Query query = new Query();
		// Get only 100 records based on orgId
		int greaterThan = (pageNum*100);
		int lessThan = (pageNum*100) + 101;
		if(count == 0){
			Query queryForCount = new Query();
			queryForCount.addCriteria(Criteria.where("orgId").is(_id));
			count = mongoTemplate.count(queryForCount, Domain.class);
		}
		query.addCriteria(Criteria.where("orgId").is(_id).andOperator(Criteria.where("sequence").gt(greaterThan),
				Criteria.where("sequence").lt(lessThan)));
	
		List<Domain> domains = mongoTemplate.find(query, Domain.class);
		if (domains != null && domains.size() > 0) {
			customResultList.setResults(domains);
			if(count > 100 && count - greaterThan > 100){
				String nextLink = SystemConstants.URL+id+SystemConstants.PAGE_NUM+newPageNum+SystemConstants.PAGE_COUNT+count;				
				customResultList.setNextLine(nextLink);
			}
			customResultList.setCount(count);
		}
		System.out.println("query - " + query.toString());
		return customResultList;
	}

}
