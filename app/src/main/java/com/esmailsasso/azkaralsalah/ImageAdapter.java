package com.esmailsasso.azkaralsalah;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    OnItemClickListener onItemClickListener;

    private final Integer adUnitFlag = 159;

    public ImageAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    // Setter method for the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter_layout, parent, false);
        return new MyViewHolder(itemView, adUnitFlag,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return Data.INSTANCE.getMThumbIds().length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ViewGroup adContainer;
        Integer adUnitFlag;

        private OnItemClickListener onItemClickListener;

        MyViewHolder(@NonNull View itemView, Integer adUnitFlag, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.adUnitFlag = adUnitFlag;

            text = itemView.findViewById(R.id.page_name);
            adContainer = itemView.findViewById(R.id.adContainer);

            text.setOnClickListener(
                    v -> {
                        if (onItemClickListener != null) {
                            int position = getAdapterPosition();
                            int adsBeforePosition = (position + 1) / 3;
                            // we have to calculate the real position because we add ads by the formula:((position - 2) % 3 == 0)
                            position -= adsBeforePosition;



                            onItemClickListener.onItemClick(position);
                        }
                    }
            );
        }

        public void bind(int position) {
            if ((position - 2) % 3 == 0){
                // If it's an ad unit, set the adView instead of the usual image and text
                AdView adView = new AdView(itemView.getContext());
                adView.setAdSize(AdSize.BANNER);


                // if debug then test ad else live ad:
                if (BuildConfig.DEBUG) {
                    // test ad:
                    adView.setAdUnitId("ca-app-pub-3940256099942544/9214589741");
                }else {
                    // live ad:
                    adView.setAdUnitId("ca-app-pub-6937623243660682/9850652823");
                }

                // Add the adView to the layout
                adContainer.setVisibility(View.VISIBLE);
                text.setVisibility(View.GONE);


                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.CENTER;


                // Set layout parameters for the ad
                adContainer.addView(adView, layoutParams);

                loadAd(adView);

                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }
                });
            } else {
                // Otherwise, display the usual image and text
                adContainer.setVisibility(View.GONE);
                text.setVisibility(View.VISIBLE);
                text.setCompoundDrawablesWithIntrinsicBounds(0, Data.INSTANCE.getMThumbIds()[position], 0, 0);
                text.setText(Data.INSTANCE.getMStrings()[position]);

            }
        }
        // load ad
        private void loadAd(AdView v) {
            AdRequest adRequest = new AdRequest.Builder().build();
            v.loadAd(adRequest);
        }
    }






}
