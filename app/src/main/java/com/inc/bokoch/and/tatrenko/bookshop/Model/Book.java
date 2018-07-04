package com.inc.bokoch.and.tatrenko.bookshop.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int mId;
    private String mTitle;
    private String mAuthor;
    private GenreEnum mGenre;
    private double mPrice;


    private int mAmount;
    private String mUri;

    public Book(int id, String title, String author, GenreEnum genre, double price, String uri, int amount) {
        mId = id;
        mTitle = title;
        mAuthor = author;
        mGenre = genre;
        mPrice = price;
        mUri = uri;
        mAmount = amount;
    }
    private Book(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mAuthor = in.readString();
        mGenre = GenreEnum.valueOf(in.readString());
        mPrice = in.readDouble();
        mUri = in.readString();
        mAmount = in.readInt();
    }

    public int getId() {
        return mId;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public GenreEnum getGenre() {
        return mGenre;
    }

    public void setGenre(GenreEnum genre) {
        mGenre = genre;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }


    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mAuthor);
        parcel.writeString(mGenre.toString());
        parcel.writeDouble(mPrice);
        parcel.writeString(mUri);
        parcel.writeInt(mAmount);
    }
    @Override
    public String toString() {
        return "Book {" +
                "mId='" + mId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mGenre=" + mGenre + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mUri='" + mUri + '\'' +
                ", m='" + mAmount +
                '}';
    }
}
