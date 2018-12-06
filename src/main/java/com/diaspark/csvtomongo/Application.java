package com.diaspark.csvtomongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.diaspark.csvtomongo.util.CriptoUtil;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements ApplicationListener<ApplicationReadyEvent> {

	private MongoTemplate mongoTemplate;

	@Autowired
	Application(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		loadData();
	}

	@SuppressWarnings("unchecked")
	private void loadData() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(criteria);
		query.fields().include("orgId");
		List<Integer> list = mongoTemplate.getCollection("domain").distinct("orgId");
		List<String> list2 = new ArrayList<String>();
		for (Integer integer : list) {
			list2.add(CriptoUtil.encrypt(integer.toString()));
		}
		System.out.println("List of orgId :::: " + list2);
	}

}
