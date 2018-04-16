package com.starthub.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

/**
 * Created by Harrison on 4/12/2018.
 */

@Entity
@Table(name = "ranks")
public class Rank {

    @Id
    @GeneratedValue
    private Long id;
    private double exitedProb;
    private double deadProb;
    private double operatingProb;

    public Rank(){}

    public Rank(double exitedProb, double deadProb, double operatingProb) {
        this.exitedProb = exitedProb;
        this.deadProb = deadProb;
        this.operatingProb = operatingProb;
    }

    public Rank(Map<String, Double> ranks) {
        ranks.forEach((key, rank) -> {
            switch (key){
                case "Exited" :
                    this.exitedProb = rank;
                    break;
                case "Operating" :
                    this.operatingProb = rank;
                    break;
                case "Dead" :
                    this.deadProb = rank;
                    break;
            }
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getExitedProb() {
        return exitedProb;
    }

    public void setExitedProb(double exitedProb) {
        this.exitedProb = exitedProb;
    }

    public double getDeadProb() {
        return deadProb;
    }

    public void setDeadProb(double deadProb) {
        this.deadProb = deadProb;
    }

    public double getOperatingProb() {
        return operatingProb;
    }

    public void setOperatingProb(double operatingProb) {
        this.operatingProb = operatingProb;
    }
}
