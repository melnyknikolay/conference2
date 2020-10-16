package com.app.config;

import com.app.listener.MyJmsListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.MethodNameBasedMBeanInfoAssembler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfiguration {

    @Bean
    public MyJmsListener getListener() {
        return new MyJmsListener();
    }

    @Bean
    public DefaultMessageListenerContainer jmsMessageListenerContainer() {

        DefaultMessageListenerContainer factory = new DefaultMessageListenerContainer();
        factory.setTransactionManager(new JmsTransactionManager(connectionFactory()));
        //factory.setConcurrency(LISTENER_CONCURRENCY + "-" + LISTENER_MAX_CONCURRENCY);
        //factory.setConcurrentConsumers(LISTENER_CONCURRENCY);
        //factory.setMaxConcurrentConsumers(LISTENER_MAX_CONCURRENCY);
        factory.setConnectionFactory(connectionFactory());
        factory.setDestinationName("myQueue");
        factory.setMessageListener(getListener());
        //factory.setAutoStartup(AUTO_STARTUP);

        //amount of allowed idle (not working) consumers
        //factory.setIdleConsumerLimit(IDLE_CONSUMER_LIMIT);

        //time for idle (not working) consumers
        //factory.setIdleTaskExecutionLimit(IDLE_TASK_EXECUTION_LIMIT);

        //factory.setMessageSelector("(age < 60) AND (age > 30)");

        return factory;
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        //connectionFactory.setBrokerURL(BROKER_URL);
        //connectionFactory.setUserName(BROKER_USERNAME);
        //connectionFactory.setPassword(BROKER_PASSWORD);
        return connectionFactory;
    }

    @Bean
    public MBeanExporter jmxConfig() {
        MBeanExporter exporter = new MBeanExporter();
        Map<String, Object> map = new HashMap();
        map.put("audit.service:type=JMSContainer,name=queueContainer", jmsMessageListenerContainer());
        exporter.setBeans(map);
        MethodNameBasedMBeanInfoAssembler assembler = new MethodNameBasedMBeanInfoAssembler();
        assembler.setManagedMethods("setMaxConcurrentConsumers", "getMaxConcurrentConsumers", "setConcurrentConsumers", "getConcurrentConsumers", "start", "stop", "isRunning", "setIdleConsumerLimit", "getIdleConsumerLimit", "setIdleTaskExecutionLimit", "getIdleTaskExecutionLimit");
        exporter.setAssembler(assembler);
        return exporter;
    }
}
