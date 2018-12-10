package com.diaspark.csvtomongo.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diaspark.csvtomongo.constant.SystemConstants;
import com.diaspark.csvtomongo.model.CustomResultList;
import com.diaspark.csvtomongo.service.CommaSeparatorService;

@Controller
public class BatchController {

	private final CommaSeparatorService commaSeparatorService;

	@Autowired
	BatchController(CommaSeparatorService commaSeparatorService) {
        this.commaSeparatorService = commaSeparatorService;
    }

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("commaDelimiterJob")
	Job commaDelimiterJob;

	@Autowired
	@Qualifier("pipeDelimiterJob")
	Job pipeDelimiterJob;

	@RequestMapping(value = "/batch")
	@ResponseBody
	public String executeBatch() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addString(SystemConstants.SOURCE, SystemConstants.SPRING_BOOT).toJobParameters();
		jobLauncher.run(commaDelimiterJob, jobParameters);
		jobLauncher.run(pipeDelimiterJob, jobParameters);
		return SystemConstants.SUCCESS;
	}

	@RequestMapping(value = "{orgId}", method = RequestMethod.GET)
	public @ResponseBody CustomResultList findById(@PathVariable("orgId") String orgId, @RequestParam(required=false) int pageNum, @RequestParam(required=false) Long count) {
		if(count == null) {
			count = 0l;
		}
		return commaSeparatorService.findById(orgId, pageNum, count);
	}

}
