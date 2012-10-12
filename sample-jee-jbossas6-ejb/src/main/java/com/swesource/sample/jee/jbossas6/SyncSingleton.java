package com.swesource.sample.jee.jbossas6;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Startup
@Singleton
public class SyncSingleton {

    private Set<String> ids;

    @PostConstruct
    void init() {
        ids = new HashSet<String>();
    }

    public void addId(String id) {
        if (hasId(id)) {
            System.out.println("SyncSingleton.addId: Id [" + id + "] already exist.");
        } else {
            ids.add(id);
        }
    }

    public boolean hasId(String id) {
        return ids.contains(id) ? true : false;
    }

    public void removeId(String id) {
        if (hasId(id)) {
            ids.remove(id);
        } else {
            System.out.println("SyncSingleton.removeId: Id [" + id + "] does not exist.");
        }
    }

    public Set listIds() {
        return ids;
    }
}
