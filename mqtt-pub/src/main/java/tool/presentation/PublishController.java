package tool.presentation;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PublishController {

    public static void main(String[] args) {
        String broker = "tcp://172.26.106.21:32730";
        String topic_start = "task/start";
        String username = "admin";
        String password = "admin";
        String client_id = "publish_client";
        String content = "Hello MQTT";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, client_id, new MemoryPersistence());
            // connect options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // connect
            client.connect(options);
            // create message and setup QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // publish message
            client.publish(topic_start, message);
            System.out.println("Message published");
            System.out.println("topic: " + topic_start);
            System.out.println("message content: " + content);
            // disconnect
            client.disconnect();
            // close client
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
