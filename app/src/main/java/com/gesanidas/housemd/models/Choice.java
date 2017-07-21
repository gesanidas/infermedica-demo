package com.gesanidas.housemd.models;

/**
 * Created by gesanidas on 7/21/2017.
 */

public class Choice
{
    String choiceId;
    String label;

    public Choice(String choiceId, String label) {
        this.choiceId = choiceId;
        this.label = label;
    }

    public Choice() {
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
