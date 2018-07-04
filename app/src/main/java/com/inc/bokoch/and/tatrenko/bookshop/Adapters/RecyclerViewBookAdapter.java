package com.inc.bokoch.and.tatrenko.bookshop.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.inc.bokoch.and.tatrenko.bookshop.Fragments.DetailBookFragment;
import com.inc.bokoch.and.tatrenko.bookshop.Model.Book;
import com.inc.bokoch.and.tatrenko.bookshop.R;


public class RecyclerViewBookAdapter extends RecyclerView.Adapter<RecyclerViewBookAdapter.BookHolder> {

    private static final String TAG = RecyclerViewBookAdapter.class.getSimpleName();

    private List<Book> mBooks;
    protected static Fragment mFragment;

    public RecyclerViewBookAdapter(Fragment fragment, List<Book> books) {
        mBooks = books;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_book, viewGroup, false);
        return new BookHolder(view);
    }


    public void setData(List<Book> data) {
        mBooks.clear();
        if (data != null) {
            mBooks.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BookHolder holder, final int position) {
        if (position >= 0 && position < mBooks.size()) {
            Log.i("OnBindViewHolder", "Position = " + position);
            Book book = mBooks.get(position);
            holder.bindBook(book);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick");
                    Fragment detailBook = DetailBookFragment.newInstance(position, mBooks);
                    mFragment.getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, detailBook)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewBook;
        TextView mTextViewBook;
        TextView mTextViewPrice;

        private BookHolder(View itemView) {
            super(itemView);
            mImageViewBook = itemView.findViewById(R.id.item_book_image_view);
            mTextViewBook = itemView.findViewById(R.id.item_book_title_text_view);
            mTextViewPrice = itemView.findViewById(R.id.item_book_price_text_view);
        }


        private void bindBook(Book book) {
            mTextViewBook.setText(book.getTitle());
            mTextViewPrice.setText(String.valueOf(book.getPrice()) + "$");
            Log.e(TAG, "DownloadGlideImage");
            Glide.with(mFragment)
                    .load(book.getUri())
                    .thumbnail(.9999f)
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_no_network)
                            .placeholder(R.color.colorPrimary)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(mImageViewBook).getRequest();
        }


    }
}
