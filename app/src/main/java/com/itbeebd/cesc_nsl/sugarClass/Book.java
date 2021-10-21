package com.itbeebd.cesc_nsl.sugarClass;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Book  extends SugarRecord implements Serializable {
    private final String bookUrl;
    private final String bookImageUrl;
    private final String bookName;
    private final String bookAuthorName;

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
