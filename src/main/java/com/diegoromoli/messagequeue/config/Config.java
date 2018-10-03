package com.diegoromoli.messagequeue.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.buffered.QueueBufferConfig;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class Config {

    @Value("${aws.local.sqs.localElasticMq.enable}")
    private Boolean enableLocalElasticMq;
    @Value("${aws.local.sqs.localElasticMq.startServer}")
    private Boolean startLocalElasticMq;
    @Value("${aws.sqs.uri.scheme}")
    private String scheme;
    @Value("${aws.sqs.uri.host}")
    private String host;
    @Value("${aws.sqs.uri.path}")
    private String path;
    @Value("${aws.sqs.uri.port}")
    private String port;
    @Value("${aws.config.access.key}")
    private String awsConfigAccessKey;
    @Value("${aws.config.secret.key}")
    private String awsConfigSecretKey;
    @Value("${aws.config.sqs.queue.name}")
    private String sqsQueueName;
    @Value("${queue.buffer.max.batch.open.ms}")
    private int queueBufferMaxBatchOpenMs;
    @Value("${queue.buffer.max.batch.size}")
    private int queueBufferMaxBatchSize;
    @Value("${queue.buffer.max.inflight.outbound.batches}")
    private int queueBufferMaxInflightOutboundBatches;

    @Bean
    public UriComponents elasticMqLocalSqsUri() {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .build()
                .encode();
    }

    @Bean
    public SQSRestServer sqsRestServer(UriComponents elasticMqLocalSqsUri) {
        SQSRestServer sqsRestServer = SQSRestServerBuilder
                .withPort(Integer.valueOf(elasticMqLocalSqsUri.getPort()))
                .withInterface(elasticMqLocalSqsUri.getHost())
                .start();
        return sqsRestServer;
    }

    @Bean
    @Lazy
    @DependsOn("sqsRestServer")
    @Profile({"local","test"})
    public AmazonSQSAsync amazonSqsLocal(AWSCredentials amazonAWSCredentials) {
        AmazonSQSAsyncClient awsSQSAsyncClient = new AmazonSQSAsyncClient(amazonAWSCredentials);
        awsSQSAsyncClient.setEndpoint(createURI());
        awsSQSAsyncClient.createQueue(sqsQueueName);
        QueueBufferConfig config = new QueueBufferConfig()
                .withMaxBatchOpenMs(queueBufferMaxBatchOpenMs)
                .withMaxBatchSize(queueBufferMaxBatchSize)
                .withMaxInflightOutboundBatches(queueBufferMaxInflightOutboundBatches);
        AmazonSQSBufferedAsyncClient amazonSQSBufferedAsyncClient = new AmazonSQSBufferedAsyncClient
                (awsSQSAsyncClient, config);
        return amazonSQSBufferedAsyncClient;
    }

    @Bean
    @Lazy
    @Profile("dev")
    public AmazonSQSAsync amazonSqs(AWSCredentials amazonAWSCredentials) {
        AmazonSQSAsyncClient awsSQSAsyncClient = new AmazonSQSAsyncClient(amazonAWSCredentials);
        awsSQSAsyncClient.setEndpoint(createURI());
        awsSQSAsyncClient.createQueue(sqsQueueName);
        QueueBufferConfig config = new QueueBufferConfig()
                .withMaxBatchOpenMs(queueBufferMaxBatchOpenMs)
                .withMaxBatchSize(queueBufferMaxBatchSize)
                .withMaxInflightOutboundBatches(queueBufferMaxInflightOutboundBatches);
        AmazonSQSBufferedAsyncClient amazonSQSBufferedAsyncClient = new AmazonSQSBufferedAsyncClient
                (awsSQSAsyncClient, config);
        return amazonSQSBufferedAsyncClient;
    }

    @Bean
    @Profile("local")
    public AWSCredentials localAwsCredentials() {
        return new BasicAWSCredentials(awsConfigAccessKey, awsConfigSecretKey);
    }

    @Bean
    @Profile("!local")
    public AWSCredentials awsCredentials() {
        return new DefaultAWSCredentialsProviderChain().getCredentials();
    }

    private String createURI() {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .build()
                .encode().toUriString();
    }
}