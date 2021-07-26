package com.isaac.batchserver.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig2 {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobLauncher jobLauncher;

    // test decider
    @Bean
    public JobExecutionDecider jobExecutionDecider1() {
        return new JobExecutionDecider() {

            int count = 0;

            @Override
            public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
                count ++;
                if (count % 2 == 0) {
                    return new FlowExecutionStatus("even");
                } else {
                    return new FlowExecutionStatus("odd");
                }
            }
        };
    }

    @Bean
    public Job myJob4() {
        return jobBuilderFactory.get("my job 4")
                .start(step1OfJob4())
                .next(jobExecutionDecider1())
                .from(jobExecutionDecider1()).on("even").to(step2OfJob4())
                .from(jobExecutionDecider1()).on("odd").to(step3OfJob4())
                .from(step3OfJob4()).on("*").to(jobExecutionDecider1()) // "*" means no matter what status is returned
                .end().build();

    }

    @Bean
    public Step step1OfJob4() {
        return stepBuilderFactory.get("step1 for job4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("this is step1 for job4");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2OfJob4() {
        return stepBuilderFactory.get("step2 for job4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step2 for job4");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3OfJob4() {
        return stepBuilderFactory.get("step3 for job4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("this is step3 for job4");
                    return RepeatStatus.FINISHED;
                }).build();
    }


    // test child job and parent job
    @Bean
    public Job myJob5(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return jobBuilderFactory.get("parent job 5")
                .start(childJobStep1(jobRepository, platformTransactionManager))
                .next(childJobStep2(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step step1OfChildJob1() {
        return stepBuilderFactory.get("step1 of child job 1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("this is step1 of child job 1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step step2OfChildJob1() {
        return stepBuilderFactory.get("step2 of child job 1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("this is step2 of child job 1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job childJob1() {
        return jobBuilderFactory.get("child job 1")
                .start(step1OfChildJob1())
                .next(step2OfChildJob1())
                .build();
    }

    @Bean
    public Step step1OfChildJob2() {
        return stepBuilderFactory.get("step1 of child job 2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("this is step1 of child job 2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Job childJob2() {
        return jobBuilderFactory.get("child job 2")
                .start(step1OfChildJob2())
                .build();
    }

    @Bean
    public Step childJobStep1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("child job step 1"))
                .job(childJob1())
                .launcher(jobLauncher) // use launcher of parent job
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step childJobStep2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("child job step 2"))
                .job(childJob2())
                .launcher(jobLauncher) // use launcher of parent job
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

}
