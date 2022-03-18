package org.example.raspberry.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<CsvItem> reader() {
        return new FlatFileItemReaderBuilder<CsvItem>()
                .name("nomineeItemReader")
                .resource(new ClassPathResource("movielist.csv"))
                .delimited()
                .delimiter(";")
                .names("year", "title", "studios", "producer", "winner")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{ setTargetType(CsvItem.class); }})
                .build();
    }

    @Bean
    public ListUnpackingItemWriter<Nominee> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Nominee> delegate = new JdbcBatchItemWriterBuilder<Nominee>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO nominees(year, title, studios, producer, winner) VALUES (:year, :title, :studios, :producer, :winner)")
                .dataSource(dataSource)
                .build();

        return new ListUnpackingItemWriter<>(delegate);
    }


    @Bean
    public CsvItemToNomineeProcessor processor() {
        return new CsvItemToNomineeProcessor();
    }

    @Bean
    public Job importNominees(Step step1) {
        return jobBuilderFactory.get("importNomineeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ListUnpackingItemWriter<Nominee> writer) {
        return stepBuilderFactory.get("step1")
                .<CsvItem, List<Nominee>> chunk(50)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}
