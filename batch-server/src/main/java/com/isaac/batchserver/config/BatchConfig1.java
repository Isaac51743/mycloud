package com.isaac.batchserver.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;


@Configuration
public class BatchConfig1 {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;


    // test basic job and step
    @Bean
    public Job myJob1() {
        return jobBuilderFactory.get("my job 1")          // the name of the job
//                .start(step1OfJob1())
//                .next(step2OfJob1())
//                .next(step3OfJob1())
//                .build();
                // add condition for each step
                .start(step1OfJob1()).on("COMPLETED").to(step2OfJob1())
                .from(step2OfJob1()).on("COMPLETED").to(step3OfJob1())
                .from(step3OfJob1()).end()
                .build();
    }

    @Bean
    public Step step1OfJob1() {
        return stepBuilderFactory.get("step1 for job1")          // the name of the step
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("this is step1 for job1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2OfJob1() {
        return stepBuilderFactory.get("step2 for job1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step2 for job1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3OfJob1() {
        return stepBuilderFactory.get("step3 for job1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step3 for job1");
                    return RepeatStatus.FINISHED;
                }).build();
    }


    // test flow
    @Bean
    public Job myJob2() {
        return jobBuilderFactory.get("my job 2")
                .start(flow1OfJob2())
                .next(step1OfJob2())
                .end()
                .build();

    }

    @Bean
    public Flow flow1OfJob2() {
        return new FlowBuilder<Flow>("flow1 for job2")
                .start(step3OfJob2())
                .next(step2OfJob2())
                .build();
    }

    @Bean
    public Step step1OfJob2() {
        return stepBuilderFactory.get("step1 for job2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step1 for job2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2OfJob2() {
        return stepBuilderFactory.get("step2 for job2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step2 for job2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3OfJob2() {
        return stepBuilderFactory.get("step3 for job2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step3 for job2");
                    return RepeatStatus.FINISHED;
                }).build();
    }


    // test split()
    @Bean
    public Job myJob3() {
        return jobBuilderFactory.get("my job 3")
                .start(flow1OfJob3())
                .split(new SimpleAsyncTaskExecutor()).add(flow2OfJob3())
                .end().build();
    }

    @Bean
    public Flow flow1OfJob3() {
        return new FlowBuilder<Flow>("flow1 for job3")
                .start(step1OfJob3())
                .build();
    }

    @Bean
    public Flow flow2OfJob3() {
        return new FlowBuilder<Flow>("flow2 for job3")
                .start(step2OfJob3())
                .next(step3OfJob3())
                .build();
    }

    @Bean
    public Step step1OfJob3() {
        return stepBuilderFactory.get("step1 for job3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step1 for job3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2OfJob3() {
        return stepBuilderFactory.get("step2 for job3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step2 for job3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3OfJob3() {
        return stepBuilderFactory.get("step3 for job3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step3 for job3");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
