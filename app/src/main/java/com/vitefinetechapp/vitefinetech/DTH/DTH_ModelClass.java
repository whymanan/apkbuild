package com.vitefinetechapp.vitefinetech.DTH;

public class DTH_ModelClass {
    private String Operator, PlanName, Description, Amount  ,Validity;

    public DTH_ModelClass() {
    }

    public DTH_ModelClass(String operator, String planName, String description, String amount, String validity) {
        Operator = operator;
        PlanName = planName;
        Description = description;
        Amount = amount;
        Validity = validity;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getValidity() {
        return Validity;
    }

    public void setValidity(String validity) {
        Validity = validity;
    }
}
