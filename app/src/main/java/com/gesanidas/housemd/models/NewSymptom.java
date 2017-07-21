package com.gesanidas.housemd.models;

/**
 * Created by gesanidas on 7/21/2017.
 */

public class NewSymptom
{
    String id;
    String name;
    Choice[] choices;


    public NewSymptom(String id, String name, Choice[] choices) {
        this.id = id;
        this.name = name;
        this.choices = choices;
    }

    public NewSymptom(String id, String name) {
        this.id = id;
        this.name = name;
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

    public Choice[] getChoices() {
        return choices;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }
}
