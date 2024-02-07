package com.esmailsasso.azkaralsalah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.image_adapter_layout, parent ,false);
        }
        TextView text = convertView.findViewById(R.id.page_name);
        text.setCompoundDrawablesWithIntrinsicBounds(0,mThumbIds[position],0,0);
        text.setText(mStrings[position]);

        return convertView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sebha,
            R.drawable.sabah,
            R.drawable.masa2,
            R.drawable.reeh,
            R.drawable.duaa_istikhara,
            R.drawable.duaa_sa3b,
            R.drawable.duaa_safar,
            R.drawable.rokia,
            R.drawable.matar,
            R.drawable.nawm

    };

    private Integer[] mStrings = {
            R.string.AzkarAlsalah,
            R.string.AzkarAlsabah,
            R.string.AzkarAlmasa2,
            R.string.reeh,
            R.string.Istikhara,
            R.string.sa3b,
            R.string.safar,
            R.string.rokia,
            R.string.matar,
            R.string.nawm

    };
}
