package com.swesource.sample.jee.jbossas6;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
@Local(DataLocal.class)
@Remote(DataRemote.class)
@Stateless
public class DataBean implements DataLocal, DataRemote {

    @PersistenceContext(unitName = "samplePU")
    EntityManager em;

    public String ping() {
        return this.getClass().getName();
    }

    public Data find(Long id) {
        return em.find(Data.class, id);
    }

    public void store(Data data) {
        em.persist(data);
    }
}
