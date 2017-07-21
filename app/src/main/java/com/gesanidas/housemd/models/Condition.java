package com.gesanidas.housemd.models;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class Condition
{
    String id;
    String name;
    String commonName;
    String probability;



    public Condition() {
    }


    public Condition(String id, String name, String commonName, String probability) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.probability = probability;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }
}
