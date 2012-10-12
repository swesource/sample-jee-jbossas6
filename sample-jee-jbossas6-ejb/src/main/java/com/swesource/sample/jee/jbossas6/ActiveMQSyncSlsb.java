package com.swesource.sample.jee.jbossas6;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;
import java.io.Serializable;
import java.util.Set;

/**
 *
 */
@Stateless
public class ActiveMQSyncSlsb implements Serializable {

    @Resource(mappedName="/activemq/QueueConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName="/queue/hqtest")
    private Queue queue;

    @EJB
    private SyncSingleton syncSingleton;

    public ActiveMQSyncSlsb() {
    }

    public void addMessage(int loop) {

        try {
            StringBuffer id = new StringBuffer(this.toString());
            id.append(':');
            id.append(System.currentTimeMillis());
            id.append(':');

            /*
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY ,"org.jnp.interfaces.NamingContextFactory");
            props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            props.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
            Context context = new InitialContext(props);
            ConnectionFactory connectionFactory = (ConnectionFactory)context.lookup("/ConnectionFactory");
            */

            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            for(int i=0 ; i<loop ; i++) {
                id.append(i);
                System.out.println("ActiveMQSyncSlsb.addMessage: Adding ID. JMSCorrelationID=" + id.toString());
                syncSingleton.addId(id.toString());
                TextMessage message = session.createTextMessage();
                message.setText("test");
                message.setJMSCorrelationID(id.toString());
                producer.send(message);
            }
            // ActiveMQ: javax.jms.TransactionException: Can't commit if an XA tx is already in progress
            //session.commit();
            session.close();
            connection.close();
        }
        catch (JMSException e) {
            e.printStackTrace();
        }
    }
    public Set<String> listMessageIds() {
        return syncSingleton.listIds();
    }
}
