package com.example.library;

import javafx.collections.ObservableList;

//import java.util.Date;
import java.sql.Date;

public class Books {

    String bookID;
    String bookName;
    String isBookAvailable;
    String memberID;
    String bookDate;

    public Books(String id, String name, String isAvailable, String memberID, String date){
        this.bookID = id;
        this.bookName = name;
        this.isBookAvailable = isAvailable;
        this.memberID = memberID;
        this.bookDate = date;
    }


    public Books(String id, String name, String isAvailable, String memberID) {
        this.bookID = id;
        this.bookName = name;
        this.isBookAvailable = isAvailable;
        this.memberID = memberID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String id){
        this.bookID = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name){
        this.bookName = name;
    }

    public String getIsBookAvailable() {
        return isBookAvailable;
    }

    public void setIsBookAvailable(String isAvailable){
        this.isBookAvailable = isAvailable;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String id){
        this.memberID = id;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

}
