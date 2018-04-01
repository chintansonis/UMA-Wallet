package com.umawallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.umawallet.R;
import com.umawallet.api.responsepojos.Transcation;
import com.umawallet.custom.TfTextView;

/**
 * Created by Shriji on 3/28/2018.
 */

public class TransactionDetailActiivty extends BaseActivity {

    private com.umawallet.custom.TfTextView tvFromDetail;
    private com.umawallet.custom.TfTextView tvTODetail;
    private com.umawallet.custom.TfTextView tvGasFee;
    private com.umawallet.custom.TfTextView tvConfirmation;
    private com.umawallet.custom.TfTextView tvTransaction;
    private com.umawallet.custom.TfTextView tvTransactionTime;
    private com.umawallet.custom.TfTextView tvTransactionBlock;
    private android.widget.ScrollView scrollView;
    private Transcation transcation;
    private android.support.v7.widget.Toolbar toolbar;
    private TfTextView txtTitle;
    private TfTextView txtPrice;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        setShowBackMessage(false);
        if (getIntent() != null) {
            transcation = (Transcation) getIntent().getSerializableExtra("transaction");
        }
        init();
        initToolbar();
        setData();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTitle = (TfTextView) toolbar.findViewById(R.id.txtTitle);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtTitle.setText(getString(R.string.transaction_detail));
    }
    private void setData() {
    tvConfirmation.setText(transcation.getConfirmation());
    tvFromDetail.setText(transcation.getFromAdd());
    tvGasFee.setText(transcation.getGasFee());
    tvTODetail.setText(transcation.getToAdd());
    tvTransaction.setText(transcation.getTransaction());
    tvTransactionTime.setText(transcation.getTransactionTime());
    tvTransactionBlock.setText(transcation.getBlock());
        if(transcation.getTranscationType().equalsIgnoreCase("C")){
            txtPrice.setText("+ "+transcation.getTokenCount()+" "+transcation.getTokenShortCode());
            txtPrice.setTextColor(getResources().getColor(R.color.color_green));
        }else {
            txtPrice.setTextColor(getResources().getColor(R.color.red));
            txtPrice.setText("- "+transcation.getTokenCount()+" "+transcation.getTokenShortCode());
        }
    }

    private void init() {
        this.scrollView = (ScrollView) findViewById(R.id.scrollView);
        this.tvTransactionBlock = (TfTextView) findViewById(R.id.tvTransactionBlock);
        this.tvTransactionTime = (TfTextView) findViewById(R.id.tvTransactionTime);
        this.tvTransaction = (TfTextView) findViewById(R.id.tvTransaction);
        this.tvConfirmation = (TfTextView) findViewById(R.id.tvConfirmation);
        this.txtPrice = (TfTextView) findViewById(R.id.txtPrice);
        this.tvGasFee = (TfTextView) findViewById(R.id.tvGasFee);
        this.tvTODetail = (TfTextView) findViewById(R.id.tvTODetail);
        this.tvFromDetail = (TfTextView) findViewById(R.id.tvFromDetail);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                doFinish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doFinish() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        doFinish();
    }
}
