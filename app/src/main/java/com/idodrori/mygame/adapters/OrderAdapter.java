package com.idodrori.mygame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.modle.Order;
import com.idodrori.mygame.modle.Order;
import com.idodrori.mygame.modle.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {



    public interface OnOrderClickListener {
        void onOrderClick(Order order);

        void onLongOrderClick(Order order);
    }






    private final OrderAdapter.OnOrderClickListener onOrderClickListener;

    List<Order> orderList;
    public OrderAdapter(@Nullable final OrderAdapter.OnOrderClickListener onOrderClickListener) {
        orderList = new ArrayList<>();
        this.onOrderClickListener = onOrderClickListener;
    }

    public OrderAdapter(List<Order> orderList, OrderAdapter.OnOrderClickListener onOrderClickListener) {
        this.orderList = orderList;
        this.onOrderClickListener = onOrderClickListener;
    }




    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrow, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderIdValue.setText(order.getOrderId());
        holder.tvTotalPriceValue.setText(String.format(Locale.getDefault(), order.getTotalPrice() + ""));
        holder.tvTimestampValue.setText(order.getFormattedDate(order.getTimestamp()));

        holder.tvUserValue.setText(order.getUser().getFname() + " " + order.getUser().getLname());


        holder.rcOrderItems.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        holder.tvPhone.setText(order.getUser().getPhone());
        holder.tvOrderStatus.setText(order.getStatus());
    if(order.getDateHairCut()!=0) {
          holder.tvDatemeeting.setText(order.getFormattedDate(order.getDateHairCut()));
        }


        HairCutAdapter adapter = new HairCutAdapter(order.getHairCuts(), new HairCutAdapter.OnHairCutClickListener() {
            @Override
            public void onHairCutClick(HairCut hairCut) {

            }

            @Override
            public void onLongHairCutClick(HairCut hairCut) {

            }
        });


        holder.rcOrderItems.setAdapter(adapter);


        holder.itemView.setOnClickListener(v -> {
            if (onOrderClickListener != null) {
                onOrderClickListener.onOrderClick(order);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onOrderClickListener != null) {
                onOrderClickListener.onLongOrderClick(order);
            }
            return true;
        });



    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOrders(List<Order> filteredOrders) {
        this.orderList = filteredOrders;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserValue, tvTimestampValue, tvOrderIdValue, tvTotalPriceValue, tvPhone, tvOrderStatus,tvDatemeeting;
        private RecyclerView rcOrderItems;


        public OrderViewHolder(View itemView) {
            super(itemView);


            tvUserValue = itemView.findViewById(R.id.tvUserValueO);
            tvTimestampValue = itemView.findViewById(R.id.tvTimestampValueO);
            tvOrderIdValue = itemView.findViewById(R.id.tvOrderIdValueO);
            tvTotalPriceValue = itemView.findViewById(R.id.tvTotalPriceValueO);
            tvPhone = itemView.findViewById(R.id.tvBuyerPhone);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvDatemeeting = itemView.findViewById(R.id.tvDatemeeting);


            rcOrderItems = itemView.findViewById(R.id.rcOtrdrItem);


        }
    }


}
