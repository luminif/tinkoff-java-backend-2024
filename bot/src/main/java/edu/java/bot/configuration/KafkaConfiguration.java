package edu.java.bot.configuration;

import edu.java.bot.api.components.LinkUpdateRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final ApplicationConfig config;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest>
    kafkaListenerContainerFactory(ConsumerFactory<String, LinkUpdateRequest> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, LinkUpdateRequest> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerProps(),
            new StringDeserializer(), new JsonDeserializer<>(LinkUpdateRequest.class));
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.kafka().bootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.kafka().groupId());
        props.put(JsonDeserializer.TYPE_MAPPINGS, config.kafka().typeMappings());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, config.kafka().trustedPackages());
        return props;
    }
}
