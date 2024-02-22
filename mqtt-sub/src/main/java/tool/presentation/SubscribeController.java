package tool.presentation;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SubscribeController {
    public static void main(String[] args) {
        String broker = "tcp://172.26.106.21:32730";
        String topic_start = "task/start";
        String topic_stop = "task/stop";
        String username = "admin";
        String password = "admin";
        String client_id = "subscribe_client";
        int qos = 0;

        try {
            MqttClient client = new MqttClient(broker, client_id, new MemoryPersistence());
            // connect options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // setup callback
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            String[] topics = new String[]{topic_start, topic_stop};
            int[] qoss = new int []{qos,qos};
            client.subscribe(topics, qoss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
