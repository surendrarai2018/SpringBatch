package com.diaspark.csvtomongo.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.diaspark.csvtomongo.listener.CommaSeparatorStepListener;
import com.diaspark.csvtomongo.model.Domain;
import com.diaspark.csvtomongo.util.FileVerificationSkipper;

@EnableBatchProcessing
@Configuration
public class CommaSeparatedFileReaderJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private FlatFileItemReader<Domain> commaSeparatorReader;

	@Autowired
	private MongoItemWriter<Domain> mongoItemWriter;
	
	private static final Integer IMPORT_CHUNKSIZE = 10;

	@Bean
	public Job commaDelimiterJob() {
		Job job= jobBuilderFactory.get("commaDelimiterJob").incrementer(new RunIdIncrementer())
				.start(commaSeparatedStep()).build();
//		System.out.println("Get JobExecution Name:::: "+job.getName());
	return job;
	}

	@Bean
	public Step commaSeparatedStep() {
		Step step = stepBuilderFactory.get("commaSeparatedStep").<Domain, Domain>chunk(IMPORT_CHUNKSIZE).reader(commaSeparatorReader)
				.faultTolerant().skipPolicy(fileVerificationSkipper()).writer(mongoItemWriter)
				.listener(commaStepListener()).build();
	    System.out.println("Get stepExecution Name:::: " + step.getName());
		return step;
	}

	@Bean
	public CommaSeparatorStepListener commaStepListener() {
		return new CommaSeparatorStepListener();
	}

	@Bean
	public SkipPolicy fileVerificationSkipper() {
		return new FileVerificationSkipper();
	}

}
