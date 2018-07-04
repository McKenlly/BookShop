package com.inc.bokoch.and.tatrenko.bookshop.Adapters;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.Model.ResultConnection;
import com.inc.bokoch.and.tatrenko.bookshop.Network.PostAsyncTask;
import com.inc.bokoch.and.tatrenko.bookshop.R;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class DetailFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = DetailFragmentAdapter.class.getSimpleName();

    private List<Book> mBooks;

    public DetailFragmentAdapter(FragmentManager fragmentManager, List<Book> data) {
        super(fragmentManager);
        mBooks = data;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position, mBooks.get(position));
    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    public static class PlaceholderFragment extends Fragment {

        Book mBook;
        int mPos;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG = "book_title";

        public static PlaceholderFragment newInstance(int sectionNumber, Book book) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putParcelable(ARG_IMG, book);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.mPos = args.getInt(ARG_SECTION_NUMBER);
            mBook = args.getParcelable(ARG_IMG);
        }

        private void ShowInfo(String title, String body) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            builder.setTitle(title)
                    .setMessage(body)
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ResultConnection resultConnection = ResultConnection.OK;
                                    dialog.dismiss();
                                    PostAsyncTask asyncTask = new PostAsyncTask();
                                    asyncTask.execute(mBook);
                                    try {
                                         resultConnection = asyncTask.get();
                                    } catch (InterruptedException | ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                    MDToast.makeText(getActivity(), String.valueOf(resultConnection),
                                            MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                                }
                            })
                    .setNegativeButton(getString(R.string.cancel),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_book_fragment, container, false);
            final ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image_view);
            final TextView title = (TextView) rootView.findViewById(R.id.detail_title_text_view);
            final TextView author = (TextView) rootView.findViewById(R.id.detail_author_text_view);
            final EditText count = (EditText) rootView.findViewById(R.id.detail_count_edit_text);
            final Button buyButton = (Button) rootView.findViewById(R.id.detail_buy_button);
            final TextView amount = (TextView) rootView.findViewById(R.id.detail_amount_text_view);
            final TextView price = (TextView) rootView.findViewById(R.id.detail_price_text_view);
            GlideDownload(imageView);

            title.setText(mBook.getTitle());
            author.setText(mBook.getAuthor());
            amount.setText(String.valueOf(mBook.getAmount()));
            price.setText(String.valueOf(mBook.getPrice()) + "$");

            buyButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!count.getText().toString().equals("")) {
                        Integer count_book = Integer.valueOf(count.getText().toString());
                        if (count_book == 0 || count_book > mBook.getAmount()) {
                            MDToast.makeText(getActivity(), getString(R.string.very_big_amount_books),
                                                MDToast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
                        } else {
                            MDToast.makeText(getActivity(), getString(R.string.success),
                                                MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                            ShowInfo(getString(R.string.invoice), String.valueOf(count_book * mBook.getPrice()) + "$");
                        }
                    } else {
                        MDToast.makeText(getActivity(), getString(R.string.empty_count),
                                                MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    }
                }
            });
            return rootView;
        }

        private void GlideDownload(ImageView view) {
            Glide.with(Objects.requireNonNull(getActivity()))
                    .load(mBook.getUri())
                    .apply(new RequestOptions()
                            .placeholder(R.color.colorPrimary)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e(TAG, "Glide: onFailed");
                            MDToast.makeText(getActivity(), getString(R.string.not_network),
                                    MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(view);

        }


    }
}
