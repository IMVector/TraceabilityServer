package com.ecnu.traceability.entity;

public class PushInfoKey {
    private String usermac;

    private String patientmac;

    public String getUsermac() {
        return usermac;
    }

    public void setUsermac(String usermac) {
        this.usermac = usermac == null ? null : usermac.trim();
    }

    public String getPatientmac() {
        return patientmac;
    }

    public void setPatientmac(String patientmac) {
        this.patientmac = patientmac == null ? null : patientmac.trim();
    }
}