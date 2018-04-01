package com.umawallet.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umawallet.R;
import com.umawallet.api.BaseResponse;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.ui.AddUpdateWalletDetailActivity;
import com.umawallet.ui.BaseActivity;
import com.umawallet.ui.WalletActvity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anish on 19-01-2018.
 */

public class WalletListAdpater extends RecyclerView.Adapter<WalletListAdpater.MyViewHolder> {
    private BaseActivity activity;
    ArrayList<AddressMaster> dataValues = new ArrayList<>();


    public WalletListAdpater(BaseActivity activity, ArrayList<AddressMaster> transcations) {
        this.activity = activity;
        this.dataValues = transcations;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_wallet, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvNickName.setText(dataValues.get(position).getWalletNickName());
        holder.tvValueAddress.setText(dataValues.get(position).getWalletAddress());
        if(!dataValues.get(position).getAdharNumber().equalsIgnoreCase("")){
            holder.tvValueAddhar.setText(dataValues.get(position).getAdharNumber());
        }else {
            holder.tvValueAddhar.setText("Pending To Add");
        }
        if(!dataValues.get(position).getWalletPublicKey().equalsIgnoreCase("")){
            holder.tvValueKey.setText(dataValues.get(position).getWalletPublicKey());
        }else {
            holder.tvValueKey.setText("Pending To Add");
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWallet(dataValues.get(position));
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddUpdateWalletDetailActivity.class);
                intent.putExtra("isFrom","Edit Wallet");
                intent.putExtra("addressMaster",dataValues.get(position));
                Functions.fireIntent(activity, intent);
                activity.finish();
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    private void deleteWallet(final AddressMaster addressMaster) {
        new AlertDialog.Builder(activity)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Functions.isConnected(activity)) {
                            deleteWalletApi(addressMaster);
                        } else {
                            Functions.showToast(activity, activity.getResources().getString(R.string.err_no_internet_connection));
                        }
                    }
                })
                .setNegativeButton("not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .setMessage("Are you sure? \nyou want to delete this wallet ?")
                .show();
    }

    private void deleteWalletApi(final AddressMaster addressMaster) {
        activity.showProgressDialog(false);
        RestClient.get().deleteWalletApi(addressMaster.getAddressId()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                activity.hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        dataValues.remove(addressMaster);
                        notifyDataSetChanged();
                        WalletActvity.checkVisibility(dataValues);
                    } else {
                        Functions.showToast(activity, response.body().getMessage());
                    }
                } else {
                    Functions.showToast(activity, activity.getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                activity.hideProgressDialog();
                Functions.showToast(activity, activity.getString(R.string.err_something_went_wrong));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataValues != null && dataValues.size() > 0) ? dataValues.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private com.umawallet.custom.TfTextView tvNickName;
        private android.widget.TextView tvValueAddress;
        private android.widget.TextView tvValueKey;
        private android.widget.TextView tvValueAddhar;
        private android.widget.ImageView imgDelete;
        private android.widget.ImageView imgEdit;

        public MyViewHolder(View view) {
            super(view);
            this.imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
            this.imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
            this.tvValueAddhar = (TextView) view.findViewById(R.id.tvValueAddhar);
            this.tvValueKey = (TextView) view.findViewById(R.id.tvValueKey);
            this.tvValueAddress = (TextView) view.findViewById(R.id.tvValueAddress);
            this.tvNickName = (TfTextView) view.findViewById(R.id.tvNickName);
        }
    }
}
