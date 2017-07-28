package com.gesanidas.housemd.models;

/**
 * Created by gesanidas on 7/28/2017.
 */

public class Question
{
    String type;
    String text;
    Symptom[] items;
    Condition[] conditions;


    public Question() {
    }

    public Question(String type, String text) {
        this.type = type;
        this.text = text;
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

    public Symptom[] getItems() {
        return items;
    }

    public void setItems(Symptom[] items) {
        this.items = items;
    }

    public Condition[] getConditions() {
        return conditions;
    }

    public void setConditions(Condition[] conditions) {
        this.conditions = conditions;
    }
}
