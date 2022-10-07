package com.example.library;

public class Librarian {
    String LibrarianID;
    String LibrarianName;
    String LibrarianEmail;
    String LibrarianNic;

    public Librarian(String id, String name, String email){
        this.LibrarianID = id;
        this.LibrarianName = name;
        this.LibrarianEmail = email;
    }

    public Librarian(String id, String name, String email, String nic){
        this.LibrarianID = id;
        this.LibrarianName = name;
        this.LibrarianEmail = email;
        this.LibrarianNic = nic;
    }

    public String getLibrarianID() {
        return LibrarianID;
    }

    public void setLibrarianID(String librarianID) {
        LibrarianID = librarianID;
    }

    public String getLibrarianName() {
        return LibrarianName;
    }

    public void setLibrarianName(String librarianName) {
        LibrarianName = librarianName;
    }

    public String getLibrarianEmail() {
        return LibrarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        LibrarianEmail = librarianEmail;
    }

    public String getLibrarianNic() {
        return LibrarianNic;
    }

    public void setLibrarianNic(String librarianNic) {
        LibrarianNic = librarianNic;
    }
}
