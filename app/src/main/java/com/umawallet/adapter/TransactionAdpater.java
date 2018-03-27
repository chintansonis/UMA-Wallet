package com.umawallet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umawallet.R;
import com.umawallet.api.responsepojos.Transcation;
import com.umawallet.helper.Functions;
import com.umawallet.ui.TransactionDetailActiivty;

import java.util.ArrayList;

/**
 * Created by anish on 19-01-2018.
 */

public class TransactionAdpater extends RecyclerView.Adapter<TransactionAdpater.MyViewHolder> {
    private Activity activity;
    ArrayList<Transcation> dataValues = new ArrayList<>();

    public TransactionAdpater(Activity activity, ArrayList<Transcation> transcations) {
        this.activity = activity;
        this.dataValues = transcations;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_transcation, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvWalletFromToAddress.setText(dataValues.get(position).getFromAdd() + " --> " + dataValues.get(position).getToAdd());
        holder.tvWalletTransferToken.setText("Transfer " + dataValues.get(position).getTokenShortCode());
        if (dataValues.get(position).getTranscationType().equalsIgnoreCase("C")) {
            holder.imgTransaction.setImageResource(R.drawable.ic_up_arrow);
            holder.txtTranscationAmount.setText("+ "+dataValues.get(position).getTokenCount() + " "+ dataValues.get(position).getTokenShortCode());
            holder.txtTranscationAmount.setTextColor(activity.getResources().getColor(R.color.color_green));
        }else {
            holder.txtTranscationAmount.setText("- "+dataValues.get(position).getTokenCount() + " "+ dataValues.get(position).getTokenShortCode());
            holder.txtTranscationAmount.setTextColor(activity.getResources().getColor(R.color.red));
            holder.imgTransaction.setImageResource(R.drawable.ic_down_arrow);
        }
        holder.tvDate.setText(dataValues.get(position).getTransactionTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TransactionDetailActiivty.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("transaction", dataValues.get(position));
                intent.putExtras(bundle);
                Functions.fireIntent(activity, intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (dataValues != null && dataValues.size() > 0) ? dataValues.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private android.widget.ImageView imgTransaction;
        private TextView tvWalletTransferToken;
        private TextView tvWalletFromToAddress;
        private TextView txtTranscationAmount;
        private TextView tvDate;


        public MyViewHolder(View view) {
            super(view);
            this.txtTranscationAmount = (TextView) view.findViewById(R.id.txtTranscationAmount);
            this.tvWalletFromToAddress = (TextView) view.findViewById(R.id.tvWalletFromToAddress);
            this.tvDate = (TextView) view.findViewById(R.id.tvDate);
            this.tvWalletTransferToken = (TextView) view.findViewById(R.id.tvWalletTransferToken);
            this.imgTransaction = (ImageView) view.findViewById(R.id.imgTransaction);

        }
    }
}
