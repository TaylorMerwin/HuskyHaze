package edu.tacoma.uw.huskyhaze;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
//    private List<NewsHeadlines> headlines;
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
