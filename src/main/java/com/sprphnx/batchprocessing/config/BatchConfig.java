package com.sprphnx.batchprocessing.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sprphnx.batchprocessing.model.User;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
			       StepBuilderFactory stepBuilderFactory, 
			       ItemReader<User> itemReader,
			       ItemProcessor<User, User> userItemProcessor,
			       ItemWriter<User> dBWriter
			       ) {
		
		Step step = stepBuilderFactory.get("CSV-LOADER-STEP")
									  .<User, User>chunk(100)
									  .reader(itemReader)
									  .processor(userItemProcessor)
									  .writer(dBWriter)
									  .build();
		
		return jobBuilderFactory.get("CSV-LOADER")
						 		.incrementer(new RunIdIncrementer())
						 		.start(step)
						 		.build();
		
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader() {
		FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new ClassPathResource("user.csv"));
		itemReader.setName("CSV-Reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		
		return itemReader;
	}

	@Bean
	public LineMapper<User> lineMapper() {
		DefaultLineMapper<User> defaultLineMapper  = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames("id","name","dept","salary");
		
		BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		return defaultLineMapper;
	}
	
}
