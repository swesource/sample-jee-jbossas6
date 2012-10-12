package com.swesource.sample.jee.jbossas6;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
@Stateless
public class SyncSlsb implements Serializable {

    @Resource(mappedName="/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName="/queue/SampleQueue")
    private Queue queue;

    @EJB
    private SyncSingleton syncSingleton;

    public SyncSlsb() {
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
                System.out.println("SyncSlsb.addMessage: Adding ID. JMSCorrelationID=" + id.toString());
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
