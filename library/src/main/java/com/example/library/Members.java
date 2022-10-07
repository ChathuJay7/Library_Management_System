package com.example.library;

public class Members {
    String MemberID;
    String MemberName;
    String MemberEmail;
    String MemberUserRole;
    String BookID;
    String MemberNic;
    String MemberContact;

    public Members(String id, String name, String email, String userRole, String bookid){
        this.MemberID = id;
        this.MemberName = name;
        this.MemberEmail = email;
        this.MemberUserRole = userRole;
        this.BookID = bookid;
    }

    public Members(String id, String name, String email, String nic, String bookid,String userRole){
        this.MemberID = id;
        this.MemberName = name;
        this.MemberEmail = email;
        this.MemberNic = nic;
        this.BookID = bookid;
        this.MemberUserRole = userRole;
    }

    public Members(String id, String name, String email, String nic,String contact, String bookid,String userRole){
        this.MemberID = id;
        this.MemberName = name;
        this.MemberEmail = email;
        this.MemberNic = nic;
        this.MemberContact = contact;
        this.BookID = bookid;
        this.MemberUserRole = userRole;
    }

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String memberID) {
        MemberID = memberID;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setAdminName(String memberName) {
        MemberName = memberName;
    }

    public String getMemberEmail() {
        return MemberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        MemberEmail = memberEmail;
    }

    public  String getMemberUserRole(){ return MemberUserRole; }

    public void setMemberUserRole(String memberUserRole){ MemberUserRole = memberUserRole; }

    public String getBookID(){ return BookID; }

    public void setBookID(String bookID){
        BookID = bookID;
    }

    public String getMemberNic() {
        return MemberNic;
    }

    public void setMemberNic(String memberNic) {
        MemberNic = memberNic;
    }

    public String getMemberContact() {
        return MemberContact;
    }

    public void setMemberContact(String memberContact) {
        MemberContact = memberContact;
    }
}
