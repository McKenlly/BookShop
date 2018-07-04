package com.inc.bokoch.and.tatrenko.bookshop.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.Model.ResultConnection;
import com.inc.bokoch.and.tatrenko.bookshop.Utils.ConstUtils;

import java.io.IOException;
import java.net.URL;

public class PostAsyncTask extends AsyncTask<Book, Void, ResultConnection> {

    @Override
    protected ResultConnection doInBackground(Book... books) {
        // Create URL object
        Book book = books[0];
        URL url = HttpRequest.createUrl(ConstUtils.BASIC_URL);
        if (url == null) {
            return ResultConnection.NOT_CREATING_URL;
        }
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse;
        try {
            jsonResponse = HttpRequest.makeHttpRequestPOST(url, book.getId(), book.getAmount());
        } catch (IOException e) {
            Log.d("AsyncTask", "Response error");
            return ResultConnection.ERROR;
        }

        if (jsonResponse.equals("success")) {
            return ResultConnection.OK;
        }
        return ResultConnection.ERROR;
    }
}
