package com.dji.FPVDemo;

/**
 * Created by Han on 10/19/2017.
 */

import com.google.gson.Gson;

import se.sigma.sensation.dto.ApplicationRestartRequest;
import se.sigma.sensation.dto.ApplicationUpdateRequest;
import se.sigma.sensation.dto.DataCollectorConfigurationRequest;
import se.sigma.sensation.dto.DataCollectorDeleteRequest;
import se.sigma.sensation.dto.DataCollectorStatusRequest;
import se.sigma.sensation.dto.EnableAdvertisingPackageReceivingRequest;
import se.sigma.sensation.dto.NetworkSettingsRequest;
import se.sigma.sensation.dto.RegistrationTicket;
import se.sigma.sensation.dto.SensorCollectionRegistrationRequest;
import se.sigma.sensation.dto.SensorCollectionStatusRequest;
import se.sigma.sensation.dto.SensorCollectionUpdateRequest;
import se.sigma.sensation.dto.SensorRegistrationRequest;
import se.sigma.sensation.dto.SystemUpdateRequest;
import se.sigma.sensation.gateway.sdk.communication.InboxListener;
import se.sigma.sensation.gateway.sdk.communication.OutboxException;
import se.sigma.sensation.gateway.sdk.communication.SensationCommunicationManager;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Appiot {

    HashMap<String,String> hmap = new HashMap<String,String>();

    void start() {
        //Setting up ZeroMQ
        String[] sensors = {"temp","hum","accx","accy","accz","distfront","distleft","distright"};
        hmap.put("temp","a87d985b-f225-4189-b449-3db6c544668e");
        hmap.put("hum", "65aebdbd-7de8-4964-8862-b9ea8b4e9541");
        hmap.put("accx","449bb514-fae9-4b32-87b1-10869d7d56ed");
        hmap.put("accy","2e9b4051-825f-4a37-80bd-eff3051782e0");
        hmap.put("accz","3794dbdc-78c0-4e40-adc9-d10272b220ae");
        hmap.put("gpslon","");
        hmap.put("gpslat","");
        hmap.put("distfront","e598f7b8-9922-40a2-8a89-1f8dc5279a7f");
        hmap.put("distleft","b1561b83-bf8c-43d5-b6f5-7e466ac2e460");
        hmap.put("distright","240bb4f9-5c6c-4814-ab8e-8bb47de5b1f9");
        Context context = ZMQ.context(1);
        Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://*:5556");
        for (String sensor : sensors){
            subscriber.subscribe(sensor.getBytes(Charset.forName("UTF-8")));
        }
        //subscriber.subscribe("temp".getBytes(Charset.forName("UTF-8")));
        String registrationTicketAsJsonString = "{\"DataCollectorId\":\"f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"SecurityToken\":\"=\",\"SecurityTokenUpdate\":\"kJtXfXqgWUtQuV5ifFsy5VMb46pcsK3YtCGkBQqXIOw=\",\"InboxAccessTicket\":{\"IssuedDateTime\":\"2017-08-02T19:02:53.7757526Z\",\"ExpireDateTime\":\"2117-08-02T19:02:52.6809267Z\",\"Namespace\":\"eappiotsens\",\"IssuerName\":\"ListenAccessPolicy\",\"HttpServiceUri\":\"https://eappiotsens.servicebus.windows.net\",\"HttpServicePath\":\"f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"HttpSas\":\"SharedAccessSignature sr=https%3a%2f%2feappiotsens.servicebus.windows.net%2ff50b6b84-d7ae-448b-ba4a-62e8e9b87d76&sig=UgZrdLcfohTD9KLfe9o4g264j%2fC1VKNSGpV%2foDj7ogY%3d&se=4657374173&skn=ListenAccessPolicy\",\"AmqpServiceUri\":\"sb://eappiotsens.servicebus.windows.net\",\"AmqpServicePath\":\"f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"AmqpSas\":\"SharedAccessSignature sr=sb%3a%2f%2feappiotsens.servicebus.windows.net%2ff50b6b84-d7ae-448b-ba4a-62e8e9b87d76&sig=dGKjNowUHQaTZ%2bM6HSCAWqzwhOznGBagBMWqblp%2bmBA%3d&se=4657374173&skn=ListenAccessPolicy\"},\"OutboxAccessTicket\":{\"IssuedDateTime\":\"2017-08-02T19:02:53.8069983Z\",\"ExpireDateTime\":\"2117-08-02T19:02:52.6809267Z\",\"Namespace\":\"eappiotsens\",\"IssuerName\":\"SendAccessPolicy\",\"HttpServiceUri\":\"https://eappiotsens.servicebus.windows.net\",\"HttpServicePath\":\"datacollectoroutbox/publishers/f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"HttpSas\":\"SharedAccessSignature sr=https%3a%2f%2feappiotsens.servicebus.windows.net%2fdatacollectoroutbox%2fpublishers%2ff50b6b84-d7ae-448b-ba4a-62e8e9b87d76%2fmessages&sig=Lx4xNMMNyVwqIWIq%2b3YZ1j6RPUjvlxS%2fmU4N6yYRkrc%3d&se=4657374173&skn=SendAccessPolicy\",\"AmqpServiceUri\":\"sb://eappiotsens.servicebus.windows.net\",\"AmqpServicePath\":\"datacollectoroutbox/publishers/f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"AmqpSas\":\"SharedAccessSignature sr=sb%3a%2f%2feappiotsens.servicebus.windows.net%2fdatacollectoroutbox%2fpublishers%2ff50b6b84-d7ae-448b-ba4a-62e8e9b87d76&sig=a6NUIRKk1lqra7h8HkZU7lRFVYnH7dHBrXK4IM3pvOA%3d&se=4657374173&skn=SendAccessPolicy\"},\"BlobStorageUri\":\"https://eappiotsenslogs.blob.core.windows.net:443/f50b6b84-d7ae-448b-ba4a-62e8e9b87d76\",\"BlobStorageSas\":\"?sv=2015-04-05&sr=c&sig=SPtDcKbDYt9ONif8ho3XbMN6GxqX0B6oXEEfSxV5dDg%3D&st=2017-08-02T18%3A57%3A54Z&se=2117-08-02T19%3A02%3A54Z&sp=wl\"}";
        RegistrationTicket ticket = new Gson().fromJson(registrationTicketAsJsonString, RegistrationTicket.class);
        SensationCommunicationManager sensationCommunicationManager = new SensationCommunicationManager(ticket);
        sensationCommunicationManager.addInboxListener(new MyInboxListener());

        // The outbox has an internal queue.
        // First argument is the queue size, which will decide how many
        // measurements it may store.
        // Second argument is the interval in milliseconds of reading and
        // draining the queue and send measurements to AppIoT.
        sensationCommunicationManager.start(1024, 1000);
        while(true) {
            System.out.println("running");
            String topic = subscriber.recvStr();
            System.out.println(topic);
            String id = hmap.get(topic);
            Double parsedData = 0.0;
            if(id != null) {
                String data = subscriber.recvStr();
                try {
                    parsedData = Double.valueOf(data);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(topic + " " + parsedData);
                    sensationCommunicationManager.getOutbox().sendMeasurement(id,
                            new double[] {parsedData}, System.currentTimeMillis());
                } catch (OutboxException e) {
                    e.printStackTrace();

                }



            }
        }
    }

    class MyInboxListener implements InboxListener {

        public void onApplicationRestartRequest(String arg0) {
        }

        public void onApplicationRestartRequest(String arg0, ApplicationRestartRequest arg1) {
        }

        public void onApplicationUpdateRequest(String arg0, ApplicationUpdateRequest arg1) {
        }

        public void onDataCollectorConfigurationRequest(String arg0, DataCollectorConfigurationRequest arg1) {
        }

        public void onDataCollectorCustomCommandRequest(String arg0, String arg1, String arg2, String arg3) {
        }

        public void onDataCollectorDeleteRequest(String arg0, DataCollectorDeleteRequest arg1) {
        }

        public void onDataCollectorStatusRequest(String arg0, DataCollectorStatusRequest arg1) {
        }

        public void onEnableAdvertisingPackageReceivingRequest(String arg0, EnableAdvertisingPackageReceivingRequest arg1) {
        }

        public void onNetworkSettingsRequest(String arg0, NetworkSettingsRequest arg1) {
        }

        public void onSensorCollectionRegistrationRequest(String arg0, SensorCollectionRegistrationRequest arg1) {
        }

        public void onSensorCollectionStatusRequest(String arg0, SensorCollectionStatusRequest arg1) {
        }

        public void onSensorCollectionUpdateRequest(String arg0, String arg1, SensorCollectionUpdateRequest arg2) {
        }

        public void onSensorRegistrationRequest(String arg0, SensorRegistrationRequest arg1) {
        }

        public void onSystemRestartRequest(String arg0) {
        }

        public void onSystemUpdateRequest(String arg0, SystemUpdateRequest arg1) {
        }
    }