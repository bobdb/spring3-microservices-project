package net.bobdb.productservice.batch;

import net.bobdb.productservice.models.Product;
import net.bobdb.productservice.repositories.ProductRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    @Value("${file.input}")
    private String fileInput;

    @Autowired
    ProductRepository productRepository;

    @Bean
    public FlatFileItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>()
                .name("productReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names("id","name","description","manufacturer","year","price")
                .fieldSetMapper(new BeanWrapperFieldSetMapper(){{
                    setTargetType(Product.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public MongoItemWriter<Product> writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<Product>().template(mongoTemplate).collection("product")
                .build();
    }



    @Bean
    public ProductsProcessor processor() {
        return new ProductsProcessor();
    }


    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, MongoItemWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<Product, Product> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public Job importProductsJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importProductsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }
}