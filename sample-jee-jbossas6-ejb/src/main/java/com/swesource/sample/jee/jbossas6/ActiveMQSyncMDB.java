package com.swesource.sample.jee.jbossas6;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * MDB for testing against an Apache MQ with the queue hqtest.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "hqtest"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        //@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
        //@ActivationConfigProperty(propertyName = "user", propertyValue = "admin")
        //@ActivationConfigProperty(propertyName = "password", propertyValue = "admin2")
})
@ResourceAdapter("activemq-rar.rar")
public class ActiveMQSyncMDB implements MessageListener {

    @EJB
    private SyncSingleton syncSingleton;

    private Logger logger = LoggerFactory.getLogger(ActiveMQSyncMDB.class);

    public ActiveMQSyncMDB() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("Logging in ActiveMQSyncMDB");
            String correlationID = message.getJMSCorrelationID();
            if(syncSingleton.hasId(correlationID)) {
                syncSingleton.removeId(correlationID);
                System.out.println("ActiveMQSyncMDB.onMessage(): Removing ID. JMSCorrelationID=" + correlationID);
            }
            else {
                System.out.println("ActiveMQSyncMDB.onMessage(): Missing ID. JMSCorrelationID=" + correlationID);
            }
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
