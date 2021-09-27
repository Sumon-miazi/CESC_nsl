package com.itbeebd.cesc_nsl.sugarClass;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Book  extends SugarRecord implements Serializable {
    private String bookUrl;
    private String bookImageUrl;
    private String bookName;
    private String bookAuthorName;

    public Book(String bookUrl, String bookImageUrl, String bookName, String bookAuthorName) {
        this.bookUrl = bookUrl;
        this.bookImageUrl = bookImageUrl;
        this.bookName = bookName;
        this.bookAuthorName = bookAuthorName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }
}
