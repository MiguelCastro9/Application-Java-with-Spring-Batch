package com.api;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 *
 * @author Miguel Castro
 */
@Configuration
public class BatchConfiguration {

    @Bean
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("productItemReader")
                .resource(new ClassPathResource("products.csv"))
                .delimited()
                .names("name", "price")
                .targetType(Product.class)
                .build();
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("INSERT INTO product (name, price) VALUES (:name, :price)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job importProductJob(JobRepository jobRepository, Step step, JobCompletionNotificationListener listener) {
        return new JobBuilder("importProductJob", jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
            FlatFileItemReader<Product> reader, ProductItemProcessor processor, JdbcBatchItemWriter<Product> writer) {
        return new StepBuilder("step", jobRepository)
                .<Product, Product>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
