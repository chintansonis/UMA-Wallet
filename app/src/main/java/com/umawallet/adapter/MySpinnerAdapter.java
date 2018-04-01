package com.umawallet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.umawallet.R;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.api.responsepojos.TokenBalance;
import com.umawallet.custom.TfTextView;


public class MySpinnerAdapter<T> extends ArrayAdapter<T> {
    private LayoutInflater mLayoutInflater;
    private boolean isCaseTitle = false;
    private Context context;

    public void setCaseTitle(boolean caseTitle) {
        isCaseTitle = caseTitle;
    }

    public MySpinnerAdapter(Context context) {
        super(context, 0);
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public MySpinnerAdapter(Context context, boolean isCaseTitle) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
        this.isCaseTitle = isCaseTitle;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        try {

            Log.e("pos", position + "");
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.spinner_list_header, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TfTextView) convertView.findViewById(R.id.txtTitle);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (getItem(position) instanceof AddressMaster) {
                viewHolder.txtTitle.setText(String.format("%s", ((AddressMaster) getItem(position)).getWalletNickName()));
            } else if (getItem(position) instanceof TokenBalance) {
                viewHolder.txtTitle.setText(String.format("%s", ((TokenBalance) getItem(position)).getTokenName()));
            }  else if (getItem(position) instanceof String) {
                viewHolder.txtTitle.setText(String.format("%s", getItem(position)));
            }
        } catch (Exception e) {

        }


        return convertView;
    }

    public View getView(int position, View convertView, ViewGroup viewgroup) {
        Log.e("pos", position + "");
        convertView = mLayoutInflater.inflate(R.layout.spinner_list_header, null);
        TfTextView txt = (TfTextView) convertView.findViewById(R.id.txtTitle);
        if (getItem(position) instanceof AddressMaster) {
            txt.setText(String.format("%s", ((AddressMaster) getItem(position)).getWalletNickName()));
        }else if (getItem(position) instanceof TokenBalance) {
            txt.setText(String.format("%s", ((TokenBalance) getItem(position)).getTokenName()));
        }
        else if (getItem(position) instanceof String) {
            txt.setText(String.format("%s", getItem(position)));
        }
        return convertView;
    }
    private static class ViewHolder {
        public TfTextView txtTitle;
    }
}
