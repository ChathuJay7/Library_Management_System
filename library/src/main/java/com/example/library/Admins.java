package com.example.library;

public class Admins {

    String AdminID;
    String AdminName;
    String AdminEmail;
    String AdminNic;
    String AdminUserRole;

    public Admins(String id, String name, String email, String adminUserRole){
        this.AdminID = id;
        this.AdminName = name;
        this.AdminEmail = email;
        this.AdminUserRole = adminUserRole;
    }

    public Admins(String id, String name, String email, String adminnic,String adminUserRole){
        this.AdminID = id;
        this.AdminName = name;
        this.AdminEmail = email;
        this.AdminNic = adminnic;
        this.AdminUserRole = adminUserRole;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public String getAdminUserRole() {
        return AdminUserRole;
    }

    public void setAdminUserRole(String adminUserRole){
        AdminUserRole = adminUserRole;
    }

    public String getAdminNic() {
        return AdminNic;
    }

    public void setAdminNic(String adminNic) {
        AdminNic = adminNic;
    }
}
