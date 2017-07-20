package com.gesanidas.housemd.models;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class Symptom
{
    String id;
    String name;
    String commonName;
    String sexFilter;
    String category;
    String seriousness;

    public Symptom(String id, String name, String commonName, String sexFilter, String category, String seriousness) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.sexFilter = sexFilter;
        this.category = category;
        this.seriousness = seriousness;
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
