package com.inc.bokoch.and.tatrenko.bookshop.Network;

import android.os.AsyncTask;

import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.Model.GenreEnum;
import com.inc.bokoch.and.tatrenko.bookshop.Utils.ConstUtils;
import com.inc.bokoch.and.tatrenko.bookshop.Utils.ParseBooks;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GetAsyncTask extends AsyncTask<GenreEnum, Void, List<Book>> {

    @Override
    protected List<Book> doInBackground(GenreEnum... genreEnums) {
        GenreEnum genreEnum = genreEnums[0];
        URL url = HttpRequest.createUrl(ConstUtils.BASIC_URL +"/" + genreEnum.toString());
        String jsonResponse = null;
        List<Book> books = null;
        try {
            assert url != null;
            jsonResponse = HttpRequest.makeHttpRequestGET(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonResponse != null) {
            try {
                books = ParseBooks.FromJson(new JSONArray(jsonResponse));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return books;
    }
}