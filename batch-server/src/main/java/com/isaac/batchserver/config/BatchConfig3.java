package com.isaac.batchserver.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BatchConfig3 {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    // test listener
    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listener job")
                .start(step1OfListenerJob())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println(jobExecution.getJobInstance().getJobName() + " before job....");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println(jobExecution.getJobInstance().getJobName() + " after job....");
                    }
                }).next(step2OfListenerJob())
                .build();
    }

    @Bean
    public Step step1OfListenerJob() {
        return stepBuilderFactory.get("step1 of listener job")
                .<String, String>chunk(2)                // how many items will be submitted each time
                .faultTolerant()
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext chunkContext) {
                        System.out.println(chunkContext.getStepContext().getStepName() + " before chunk....");
                    }

                    @Override
                    public void afterChunk(ChunkContext chunkContext) {
                        System.out.println(chunkContext.getStepContext().getStepName() + " after chunk....");
                    }

                    @Override
                    public void afterChunkError(ChunkContext chunkContext) {
                        System.out.println(chunkContext.getStepContext().getStepName() + " error chunk....");
                    }
                })
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step2OfListenerJob() {
        return stepBuilderFactory.get("step2 of listener job")
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println(stepExecution.getStepName() + " before step....");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println(stepExecution.getStepName() + " after step....");
                        return ExitStatus.COMPLETED;
                    }
                })
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2 of listerer job");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(Arrays.asList("Isaac", "Barry", "Bao", "Shawn"));
    }

    @Bean
    public ItemWriter<String> writer() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String s : list) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        };
    }
}
