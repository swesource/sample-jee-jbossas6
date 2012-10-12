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
import javax.jms.TextMessage;

/**
 * MDB for testing against a local EMA (HornetQ) with the queue queue/SampleQueue.
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/SampleQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        //@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
        //@ActivationConfigProperty(propertyName = "user", propertyValue = "admin")
        //@ActivationConfigProperty(propertyName = "password", propertyValue = "admin2")
})
//@ResourceAdapter("hornetq-ra.rar")
public class SyncMDB implements MessageListener {

    @EJB
    private SyncSingleton syncSingleton;

    private Logger logger = LoggerFactory.getLogger(SyncMDB.class);

    public SyncMDB() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("Logging in SyncMDB");
            String correlationID = message.getJMSCorrelationID();
            if(syncSingleton.hasId(correlationID)) {
                syncSingleton.removeId(correlationID);
                System.out.println("SyncMDB.onMessage(): Removing ID. JMSCorrelationID=" + correlationID);
            }
            else {
                System.out.println("SyncMDB.onMessage(): Missing ID. JMSCorrelationID=" + correlationID);
            }
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
