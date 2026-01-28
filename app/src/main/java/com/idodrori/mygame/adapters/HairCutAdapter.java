package com.idodrori.mygame.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.utils.ImageUtil;

import java.util.List;

public class HairCutAdapter extends RecyclerView.Adapter<HairCutAdapter.HairCutViewHolder> {

    private List<HairCut> hairCutList;

    public HairCutAdapter(List<HairCut> hairCutList) {
        this.hairCutList = hairCutList;
    }

    @NonNull
    @Override
    public HairCutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_haircut, parent, false);
        return new HairCutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HairCutViewHolder holder, int position) {
        HairCut hairCut = hairCutList.get(position);

        holder.tvName.setText(hairCut.getName());
        holder.tvPrice.setText(hairCut.getPrice() + " ₪");
        holder.tvType.setText(hairCut.getType());

        // שימוש ב-ImageUtil כדי להמיר את ה-Base64 לתמונה

            // תמונת ברירת מחדל אם אין תמונה

        if(hairCut.getPic()!=null) {
            holder.ivImage.setImageBitmap(ImageUtil.convertFrom64base(hairCut.getPic()));
        }


    }

    @Override
    public int getItemCount() {
        return hairCutList.size();
    }

    public static class HairCutViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvType;
        ImageView ivImage;

        public HairCutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvType = itemView.findViewById(R.id.tvItemType);
            ivImage = itemView.findViewById(R.id.ivItemImage);
        }
    }
}