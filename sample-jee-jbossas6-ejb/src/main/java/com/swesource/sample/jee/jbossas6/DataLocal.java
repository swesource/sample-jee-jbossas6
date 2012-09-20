package com.swesource.sample.jee.jbossas6;

import javax.ejb.Local;

/**
 *
 */
@Local
public interface DataLocal {
    public String ping();
    public Data find(Long id);
    public void store(Data data);
}
