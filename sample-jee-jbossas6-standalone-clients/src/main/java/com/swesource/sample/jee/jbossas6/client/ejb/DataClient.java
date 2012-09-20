package com.swesource.sample.jee.jbossas6.client.ejb;

import com.swesource.sample.jee.jbossas6.Data;
import com.swesource.sample.jee.jbossas6.DataRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 */
public class DataClient {

    private long delay = 0;
    private long loop = 1;

    private void parseArgs(String[] argv) {
        if(argv.length == 2) {
            delay =  Long.parseLong(argv[0]);
            loop = Long.parseLong(argv[1]);
        }
        else {
            System.out.println("Usage: DataClient <delay (ms)> <#loops>" + argv.length);
            System.exit(0);
        }
    }

    private DataRemote getDataBean() {
        try {
            //Properties properties = new Properties();
            //properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            //properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming.client:org.jnp.interfaces");
            //properties.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
            //context = new InitialContext(properties);
            Context context = new InitialContext();
            Object o = context.lookup("sample-jee-jbossas6/DataBean/remote");
            return (DataRemote)o;
            //DataRemote dataBean = (DataRemote)o;
            //return dataBean;
        }
        catch (NamingException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static void main(String[] argv) {
        DataClient client = new DataClient();
        client.parseArgs(argv);
        DataRemote dataBean = client.getDataBean();

        System.out.println(dataBean.ping());

        Data data1 = new Data();
        data1.setData("mydata");
        dataBean.store(data1);

        for(int i = 0 ; i < client.loop ; i++) {
            Data data2 = dataBean.find(1L);
            System.out.printf("Loop #%06d: %s\n", i, data2.getData());
            //System.out.println("Loop #" + i + ": " + data2.getData());
            try {
                Thread.sleep(client.delay);
            }
            catch(InterruptedException e) {
                System.out.println("Loop #" + i);
                e.printStackTrace();
            }
        }
    }
}