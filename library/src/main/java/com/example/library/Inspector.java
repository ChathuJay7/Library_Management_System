package com.example.library;

public class Inspector {
    String InspectorID;
    String InspectorName;
    String InspectorEmail;
    String InspectorNic;


    public Inspector(String id, String name, String email, String nic){
        this.InspectorID = id;
        this.InspectorName = name;
        this.InspectorEmail = email;
        this.InspectorNic = nic;
    }

    public String getInspectorID() {
        return InspectorID;
    }

    public void setInspectorID(String inspectorID) {
        InspectorID = inspectorID;
    }

    public String getInspectorName() {
        return InspectorName;
    }

    public void setInspectorName(String inspectorName) {
        InspectorName = inspectorName;
    }

    public String getInspectorEmail() {
        return InspectorEmail;
    }

    public void setInspectorEmail(String inspectorEmail) {
        InspectorEmail = inspectorEmail;
    }

    public String getInspectorNic() {
        return InspectorNic;
    }

    public void setInspectorNic(String inspectorNic) {
        InspectorNic = inspectorNic;
    }
}
