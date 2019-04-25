package com.mdeaf.Entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class AppTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;

    public AppTask() {
        super();
    }

    public AppTask(Long id,String task) {
        super();
        this.id=id;
        this.task = task;
    }
    
    public AppTask(String task) {
    	super();
    	this.task=task;
    }

    public Long getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
