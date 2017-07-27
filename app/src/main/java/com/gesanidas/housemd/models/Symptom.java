package com.gesanidas.housemd.models;

import java.io.Serializable;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class Symptom implements Serializable
{
    String id;
    String name;
    String commonName;
    String sexFilter;
    String category;
    String seriousness;
    String choiceID;

    public Symptom(String id, String name, String commonName, String sexFilter, String category, String seriousness) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.sexFilter = sexFilter;
        this.category = category;
        this.seriousness = seriousness;
    }


    public Symptom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Symptom(String id, String name, String commonName, String choiceID) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.choiceID = choiceID;
    }

    public String getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(String choiceID) {
        this.choiceID = choiceID;
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

    public String getSexFilter() {
        return sexFilter;
    }

    public void setSexFilter(String sexFilter) {
        this.sexFilter = sexFilter;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeriousness() {
        return seriousness;
    }

    public void setSeriousness(String seriousness) {
        this.seriousness = seriousness;
    }
}
