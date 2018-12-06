package com.diaspark.csvtomongo.listener;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PipeSeparatorStepListener implements StepExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger("PipeSeparatorStepListener");
  @Override
  public void beforeStep(StepExecution stepExecution) {
    logger.info("pipe step listener called");
    stepExecution.getExecutionContext().putString("fileName", "pipe-separated.csv");
    stepExecution.getExecutionContext().putString("delimiter", "|");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
