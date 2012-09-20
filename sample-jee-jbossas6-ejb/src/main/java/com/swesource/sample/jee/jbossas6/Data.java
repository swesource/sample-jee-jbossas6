package com.swesource.sample.jee.jbossas6;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 */
@Entity
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String data;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
