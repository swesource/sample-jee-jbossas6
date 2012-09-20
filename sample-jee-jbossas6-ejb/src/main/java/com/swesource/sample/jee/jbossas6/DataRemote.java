package com.swesource.sample.jee.jbossas6;

import javax.ejb.Remote;

/**
 *
 */
@Remote
public interface DataRemote {
    public String ping();
    public Data find(Long id);
    public void store(Data data);
}
