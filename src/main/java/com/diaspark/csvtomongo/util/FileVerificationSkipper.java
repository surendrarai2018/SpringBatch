package com.diaspark.csvtomongo.util;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

import com.diaspark.csvtomongo.constant.SystemConstants;

public class FileVerificationSkipper implements SkipPolicy {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemConstants.BAD_RECORD_LOGGER);

	@Override
	public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
		if (exception instanceof FileNotFoundException) {
			return false;
		} else if (exception instanceof FlatFileParseException && skipCount <= 8) {
			FlatFileParseException ffpe = (FlatFileParseException) exception;
			StringBuilder errorMessage = new StringBuilder();
			errorMessage.append("An error occured while processing the " + ffpe.getLineNumber()
					+ " line of the file. Below was the faulty " + "input.\n");
			errorMessage.append(ffpe.getInput() + "\n");
			logger.error("{}", errorMessage.toString());
			return true;
		} else {
			return false;
		}
	}

}
