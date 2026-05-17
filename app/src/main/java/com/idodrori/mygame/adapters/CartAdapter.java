package com.idodrori.mygame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.Cart;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.utils.ImageUtil;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cart != null && cart.getHairCuts() != null && position < cart.getHairCuts().size()) {
            HairCut hairCut = cart.getHairCut(position);
            holder.bind(hairCut);
        }
    }

    @Override
    public int getItemCount() {
        return (cart != null && cart.getHairCuts() != null) ? cart.getHairCuts().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.cartImage);
            product_name = itemView.findViewById(R.id.cart_name);
            product_price = itemView.findViewById(R.id.cart_price);


        }

        public void bind(final HairCut hairCut) {


            if (hairCut.getPic() != null) {

                product_image.setImageBitmap(ImageUtil.convertFrom64base(hairCut.getPic()));
            }
            product_name.setText(hairCut.getName());
            product_price.setText(hairCut.getPrice() + "₪");


        }

    }
}

