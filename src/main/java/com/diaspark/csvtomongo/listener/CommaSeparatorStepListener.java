package com.diaspark.csvtomongo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommaSeparatorStepListener implements StepExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger("CommaSeparatorStepListener");

	@Override
	public void beforeStep(StepExecution stepExecution) {
		logger.info("comma step listener called");
		stepExecution.getExecutionContext().putString("fileName", "comma-separated.csv");
		stepExecution.getExecutionContext().putString("delimiter", ",");
		String name = stepExecution.getStepName();
		JobExecution jobExecution = stepExecution.getJobExecution();
		System.out.println("Get stepExecution Name:::: " + name + "Get JobExecution Id::::" + jobExecution.getId());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// System.out.println("Exiting !!!!!!!!!!!!!!");
		if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println("Job success");
		} else if (stepExecution.getStatus() == BatchStatus.FAILED) {
			System.out.println("Job Failed");
		}
		return stepExecution.getExitStatus();
	}
}
