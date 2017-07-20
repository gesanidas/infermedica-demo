package com.gesanidas.housemd.models;

/**
 * Created by ΕΛΙΣΑΒΕΤ on 20/7/2017.
 */

public class Condition
{
    String id;
    String name;
    String commonName;
    String sexFilter;
    String category;
    String prevalence;
    String severity;
    String triageLevel;
    String acuteness;
    String hint;
    String icd10Code;


    public Condition() {
    }

    public Condition(String id, String name, String commonName) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
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

    public String getPrevalence() {
        return prevalence;
    }

    public void setPrevalence(String prevalence) {
        this.prevalence = prevalence;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTriageLevel() {
        return triageLevel;
    }

    public void setTriageLevel(String triageLevel) {
        this.triageLevel = triageLevel;
    }

    public String getAcuteness() {
        return acuteness;
    }

    public void setAcuteness(String acuteness) {
        this.acuteness = acuteness;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }
}
