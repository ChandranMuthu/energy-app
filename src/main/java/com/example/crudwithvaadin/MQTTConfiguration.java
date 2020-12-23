package com.example.crudwithvaadin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class MQTTConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${mqtt.clientId}")
    private String clientId;
    @Value("${mqtt.hostname}")
    private String hostname;
    @Value("${mqtt.port}")
    private int port;

    @Autowired
    private EnergyOutputRepository energyOutputRepository;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }

    @Bean
    public DefaultMqttPahoClientFactory clientFactory()
    {
        final DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
        clientFactory.setConnectionOptions(mqttConnectOptions());
        return clientFactory;
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://"+hostname+ ":"+port, clientId, clientFactory(),
                        "/CMD-RES/ES-24:6F:28:DC:44:B8/", "/STATUS/ES-24:6F:28:DC:44:B8/");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
//        return new MessageHandler() {
//            @Override
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println(message.getPayload());
//            }
//        };
        return new MqttMessageHandler(energyOutputRepository);
    }

//    @Bean
//    @ConfigurationProperties(prefix = "mqtt")
//    public MqttConnectOptions mqttConnectOptions() {
//        return new MqttConnectOptions();
//    }
//
//    @Bean
//    public IMqttClient mqttClient(@Value("${mqtt.clientId}") String clientId,
//                                  @Value("${mqtt.hostname}") String hostname,
//                                  @Value("${mqtt.port}") int port) throws MqttException {
//        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId);
//        mqttClient.connect(mqttConnectOptions());
//        return mqttClient;
//    }
    //

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
}
