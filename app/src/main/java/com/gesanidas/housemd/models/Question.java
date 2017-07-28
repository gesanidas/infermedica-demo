package com.gesanidas.housemd.models;

import java.util.ArrayList;

/**
 * Created by gesanidas on 7/28/2017.
 */

public class Question
{
    String type;
    String text;
    ArrayList<Symptom> symptoms;
    ArrayList<Condition> conditions;


    public Question() {
    }

    public Question(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public Question(String type, String text, ArrayList<Symptom> symptoms, ArrayList<Condition> conditions) {
        this.type = type;
        this.text = text;
        this.symptoms = symptoms;
        this.conditions = conditions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(ArrayList<Condition> conditions) {
        this.conditions = conditions;
    }
}
