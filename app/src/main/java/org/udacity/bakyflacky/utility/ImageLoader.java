package org.udacity.bakyflacky.utility;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.udacity.bakyflacky.R;

public class ImageLoader {
    public static void fetchIntoView(String url, ImageView view) {
        view.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.user_placeholder_loading)
                .error(R.drawable.user_placeholder_loading_error)
                .into(view);
    }
}
