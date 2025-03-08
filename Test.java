package com.example.belajar;
import javax.xml.crypto.Data;

public class Test {
    public static void main(String[] args) {
        Database db = new Database();
        db.open();
//        System.out.println(db.Data);
        db.view();
    }
}
