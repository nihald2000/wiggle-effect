package com.nihal.wiggledemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.nihal.wiggleanim.R;
import com.nihal.wiggleanimlib.WiggleEffect;
import java.util.List;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> mItemList;
    private boolean isEditMode = false;

    public HomeScreenAdapter(Context context, List<Integer> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(mItemList.get(position));

        if (isEditMode) {
            new WiggleEffect(holder.itemView).start();  // Start wiggle effect in edit mode
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gridImage);
        }
    }
}
