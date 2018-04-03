package com.umawallet.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.umawallet.R;
import com.umawallet.api.RestClient;
import com.umawallet.api.responsepojos.GraphDatum;
import com.umawallet.api.responsepojos.ResponseGraphData;
import com.umawallet.custom.TfTextView;
import com.umawallet.helper.AppConstants;
import com.umawallet.helper.Functions;
import com.umawallet.helper.Preferences;
import com.umawallet.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shriji on 3/25/2018.
 */

public class StateChartFragment extends BaseFragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {
    private com.umawallet.custom.TfTextView tvTokenLite;
    private com.umawallet.custom.TfTextView tvTokenClassic;
    private android.widget.RadioButton rbWeekly;
    private android.widget.RadioButton rbNightly;
    private android.widget.RadioButton rbMonthly;
    private LineChart horizontalBarChart;
    private ArrayList<GraphDatum> eduliteGraphDataList = new ArrayList<>();
    private int selectedPostion=0;
    private GraphDatum graphDatum,graphDatummin;
    private boolean  isEthmClassicSelectedFirst=false;


    @SuppressLint({"ValidFragment", "Unused"})
    private StateChartFragment() {
    }

    @SuppressLint("ValidFragment")
    private StateChartFragment(BaseActivity activity) {
        setBaseActivity(activity);
    }

    public static BaseFragment getFragment(BaseActivity activity) {
        BaseFragment fragment = new StateChartFragment(activity);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseActivity((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        init(view);
    }

    private void init(View view) {
        rbMonthly = (RadioButton) view.findViewById(R.id.rbMonthly);
        rbNightly = (RadioButton) view.findViewById(R.id.rbNightly);
        rbWeekly = (RadioButton) view.findViewById(R.id.rbWeekly);
        tvTokenClassic = (TfTextView) view.findViewById(R.id.tvTokenClassic);
        tvTokenLite = (TfTextView) view.findViewById(R.id.tvTokenLite);
        horizontalBarChart = (LineChart) view.findViewById(R.id.chart);
        horizontalBarChart.setOnChartGestureListener(this);
        horizontalBarChart.setOnChartValueSelectedListener(this);
        horizontalBarChart.setDrawGridBackground(false);
        tvTokenClassic.setOnClickListener(this);
        tvTokenLite.setOnClickListener(this);
        rbMonthly.setOnClickListener(this);
        rbNightly.setOnClickListener(this);
        rbWeekly.setOnClickListener(this);
        rbWeekly.setChecked(true);
        rbNightly.setChecked(false);
        rbMonthly.setChecked(false);
        selectedTab(1);
        selectedTab(1);
    }

    private void initChart() {
        ArrayList<String> xVals = getXAxisValues();
        ArrayList<Entry> yVals = getDataSet();
        LineDataSet set1;
        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Stats");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        horizontalBarChart.setData(data);
        Legend l = horizontalBarChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        /*// no description text

        horizontalBarChart.setNoDataTextDescription("You need to provide data for the chart.");*/

        // enable touch gestures
        horizontalBarChart.setTouchEnabled(true);
        horizontalBarChart.refreshDrawableState();

        // enable scaling and dragging
        horizontalBarChart.setDragEnabled(true);
        horizontalBarChart.setScaleEnabled(true);
        // horizontalBarChart.setScaleXEnabled(true);
        // horizontalBarChart.setScaleYEnabled(true);

        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(9f);

        YAxis leftAxis = horizontalBarChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        Log.d("System out", "price round" + Math.round(graphDatum.getPrice()));
        leftAxis.setAxisMaxValue(Math.round(graphDatum.getPrice()) + 4);
        /*leftAxis.setAxisMaxValue(8f);*/
        leftAxis.setAxisMinValue(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(20f, 20f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        horizontalBarChart.getAxisRight().setEnabled(false);

        //horizontalBarChart.getViewPortHandler().setMaximumScaleY(2f);
        //horizontalBarChart.getViewPortHandler().setMaximumScaleX(2f);

        horizontalBarChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        //horizontalBarChart.setScaleMinima(1f, Math.round(graphDatum.getPrice()));
//        horizontalBarChart.zoom(0f,7f,0f,0f);
        /*horizontalBarChart.zoom(0f,7f,0f,0f);*/
        horizontalBarChart.invalidate();
    }

    private ArrayList<Entry> getDataSet() {
        /*ArrayList<Entry> dataSets = null;
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        for (int i = 0; i < eduliteGraphDataList.size(); i++) {
            Entry v1e1 = new Entry(eduliteGraphDataList.get(i).getPrice(), i); // Jan
            valueSet1.add(v1e1);
        }
        return dataSets;*/
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < eduliteGraphDataList.size(); i++) {
            Entry v1e1 = new Entry(eduliteGraphDataList.get(i).getPrice(), i); // Jan
            yVals.add(v1e1);
        }
        return yVals;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < eduliteGraphDataList.size(); i++) {
            xAxis.add(eduliteGraphDataList.get(i).getDay());
        }
        return xAxis;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTokenLite:
                selectedTab(2);
                break;
            case R.id.tvTokenClassic:
                if(!isEthmClassicSelectedFirst){
                    isEthmClassicSelectedFirst=true;
                    selectedTab(1);
                    selectedTab(1);
                }else {
                    selectedTab(1);
                }
                break;
            case R.id.rbMonthly:
                rbMonthly.setChecked(true);
                rbWeekly.setChecked(false);
                rbNightly.setChecked(false);
                if(selectedPostion==1){
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduClassicApi("3");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }
                }else {
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduLiteApi("3");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }

                }
                break;
            case R.id.rbNightly:
                rbMonthly.setChecked(true);
                rbWeekly.setChecked(true);
                rbNightly.setChecked(true);
                if(selectedPostion==1){
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduClassicApi("2");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }
                }else {
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduLiteApi("2");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }

                }
                break;
            case R.id.rbWeekly:
                rbMonthly.setChecked(false);
                rbWeekly.setChecked(true);
                rbNightly.setChecked(false);
                if(selectedPostion==1){
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduClassicApi("1");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }
                }else {
                    if (Functions.isConnected(getBaseActivity())) {
                        callEduLiteApi("1");
                    } else {
                        Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
                    }
                }
                break;
        }
    }
    private void selectedTab(int position) {
        if (position == 1) {
            selectedPostion=position;
            tvTokenLite.setBackground(getBaseActivity().getResources().getDrawable(R.drawable.blue_border_grey_bg));
            tvTokenLite.setTextColor(getBaseActivity().getResources().getColor(R.color.grey_dark));
            tvTokenClassic.setBackground(getBaseActivity().getResources().getDrawable(R.drawable.blue_border_blue_bg));
            tvTokenClassic.setTextColor(getBaseActivity().getResources().getColor(R.color.colorPrimaryDark));
            rbWeekly.setChecked(true);
            if (Functions.isConnected(getBaseActivity())) {
                callEduClassicApi("1");
            } else {
                Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
            }
        } else {
            selectedPostion=position;
            tvTokenLite.setBackground(getBaseActivity().getResources().getDrawable(R.drawable.blue_border_blue_bg));
            tvTokenLite.setTextColor(getBaseActivity().getResources().getColor(R.color.colorPrimaryDark));
            tvTokenClassic.setBackground(getBaseActivity().getResources().getDrawable(R.drawable.blue_border_grey_bg));
            tvTokenClassic.setTextColor(getBaseActivity().getResources().getColor(R.color.grey_dark));
            rbWeekly.setChecked(true);
            if (Functions.isConnected(getBaseActivity())) {
                callEduLiteApi("1");
            } else {
                Functions.showToast(getBaseActivity(), getResources().getString(R.string.err_no_internet_connection));
            }


        }
    }

    private void callEduClassicApi(String type) {
        getBaseActivity().showProgressDialog(false);
        RestClient.get().getEduClassicGraphDatasApi((Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_CURRENCY)), type).enqueue(new Callback<ResponseGraphData>() {
            @Override
            public void onResponse(Call<ResponseGraphData> call, Response<ResponseGraphData> response) {
                getBaseActivity().hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if (eduliteGraphDataList != null) {
                            eduliteGraphDataList.clear();
                        }
                        eduliteGraphDataList.addAll(response.body().getGraphData());
                        graphDatum = Collections.max(eduliteGraphDataList);
                        graphDatummin = Collections.min(eduliteGraphDataList);
                        if (horizontalBarChart != null) {
                            horizontalBarChart.invalidate();
                            horizontalBarChart.clear();
                        }
                        initChart();
                    } else {
                    }
                } else {
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ResponseGraphData> call, Throwable t) {
                getBaseActivity().hideProgressDialog();
                Functions.showToast(getBaseActivity(), t.getMessage());
            }
        });
    }

    private void callEduLiteApi(String type) {
        getBaseActivity().showProgressDialog(false);
        RestClient.get().getEduLiteGraphDatasApi((Preferences.getInstance(getBaseActivity()).getString(Preferences.KEY_CURRENCY)), type).enqueue(new Callback<ResponseGraphData>() {
            @Override
            public void onResponse(Call<ResponseGraphData> call, Response<ResponseGraphData> response) {
                getBaseActivity().hideProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase(AppConstants.ResponseSuccess)) {
                        if (eduliteGraphDataList != null) {
                            eduliteGraphDataList.clear();
                        }
                        eduliteGraphDataList.addAll(response.body().getGraphData());
                        graphDatum = Collections.max(eduliteGraphDataList);
                        graphDatummin = Collections.min(eduliteGraphDataList);
                        if (horizontalBarChart != null) {
                            horizontalBarChart.invalidate();
                            horizontalBarChart.clear();
                        }
                        initChart();
                    } else {
                    }
                } else {
                    Functions.showToast(getBaseActivity(), getString(R.string.err_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ResponseGraphData> call, Throwable t) {
                getBaseActivity().hideProgressDialog();
                Functions.showToast(getBaseActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            horizontalBarChart.highlightValues(null); // or highlightTouch(null) for
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
