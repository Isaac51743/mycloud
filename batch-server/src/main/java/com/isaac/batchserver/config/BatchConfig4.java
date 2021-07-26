package com.isaac.batchserver.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Configuration
public class BatchConfig4 {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    //test ItemReader
    @Bean
    public Job readerJob() {
        return jobBuilderFactory.get("reader job")
                .start(readerStep())
                .build();
    }

    @Bean
    public Step readerStep() {
        return stepBuilderFactory.get("reader step")
                .<String, String>chunk(2)
                .reader(reader1())
                .writer(writer1())
                .build();
    }

    @Bean
    public ItemReader<String> reader1() {
        return new ItemReader<String>() {
            Iterator<String> iterator = Arrays.asList("apple", "berry", "orange").iterator();
            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                //read item 1 by 1
                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    return null;
                }
            }
        };
    }

    @Bean
    public ItemWriter<String> writer1() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String s : list) {
                    System.out.println(s);
                }
            }
        };
    }
}
