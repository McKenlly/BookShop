package com.inc.bokoch.and.tatrenko.bookshop.Utils;

import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.Model.GenreEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ParseBooks {
    public static List<Book> FromJson(JSONArray array) throws JSONException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            int id = obj.getInt("id");
            String title = obj.getString("title");
            String author = obj.getString("author");
            GenreEnum genre = GenreEnum.valueOf(obj.getString("genre"));
            double price = obj.getDouble("price");
            String uri = obj.getString("pictureId");
            int amount = obj.getInt("amount");
            books.add(new Book(id, title, author, genre, price, uri, amount));
        }
        return books;
    }
    public static JSONObject ToJson(Book book) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", book.getId());
        obj.put("title", book.getTitle());
        obj.put("genre", book.getGenre());
        obj.put("price", book.getPrice());
        obj.put("author", book.getAuthor());
        obj.put("uri", book.getUri());
        obj.put("amount", book.getAmount());
        return obj;
    }

}
