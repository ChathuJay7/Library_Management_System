package com.example.library;

public class AssistLibrarian {
    String AssistLibrarianID;
    String AssistLibrarianName;
    String AssistLibrarianEmail;
    String AssistLibrarianUserRole;
    String AssistLibrarianNic;

    public AssistLibrarian(String id, String name, String email, String userRole){
        this.AssistLibrarianID = id;
        this.AssistLibrarianName = name;
        this.AssistLibrarianEmail = email;
        this.AssistLibrarianUserRole = userRole;
    }

    public AssistLibrarian(String id, String name, String email, String nic, String userRole){
        this.AssistLibrarianID = id;
        this.AssistLibrarianName = name;
        this.AssistLibrarianEmail = email;
        this.AssistLibrarianNic = nic;
        this.AssistLibrarianUserRole = userRole;
    }

    public String getAssistLibrarianID() {
        return AssistLibrarianID;
    }

    public void setAssistLibrarianID(String assistLibrarianID) {
        AssistLibrarianID = assistLibrarianID;
    }

    public String getAssistLibrarianName() {
        return AssistLibrarianName;
    }

    public void setAssistLibrarianName(String assistLibrarianName) {
        AssistLibrarianName = assistLibrarianName;
    }

    public String getAssistLibrarianEmail() {
        return AssistLibrarianEmail;
    }

    public void setAssistLibrarianEmail(String assistLibrarianEmail) {
        AssistLibrarianEmail = assistLibrarianEmail;
    }

    public String getAssistLibrarianUserRole() { return AssistLibrarianUserRole; }

    public void setAssistLibrarianUserRole(String assistLibrarianUserRole) { AssistLibrarianUserRole = assistLibrarianUserRole; }

    public String getAssistLibrarianNic() {
        return AssistLibrarianNic;
    }

    public void setAssistLibrarianNic(String assistLibrarianNic) {
        AssistLibrarianNic = assistLibrarianNic;
    }
}
