package com.example.n4u1.test130.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.n4u1.test130.R;
import com.example.n4u1.test130.models.ContentDTO;
import com.example.n4u1.test130.views.PollSingleActivity;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.common.stats.StatisticsTracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class PollResultDialog extends DialogFragment {
    public PollResultDialog() {
    }

    private DatabaseReference mDatabaseReference;

    int contentHit;
    int male = 0;
    int female = 0;

    String STATISTICS_CODE = "0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_pollresult, container);
        TextView textView = view.findViewById(R.id.textView);
        HorizontalBarChart pollActivity_horizontalBarChart_result = view.findViewById(R.id.pollActivity_horizontalBarChart_result);


        //차트클릭시 다이얼로그 닫기
        pollActivity_horizontalBarChart_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String contentKey = bundle.getString("currentContent", null);
            int imageN = bundle.getInt("imageN");
            int currentPick = bundle.getInt("imagePick");
            contentHit = bundle.getInt("contentHits");
            Log.d("lkj contentKey", contentKey);
            Log.d("lkj imageN", String.valueOf(imageN));
            Log.d("lkj currentPick", String.valueOf(currentPick));
            Log.d("lkj currentHits", String.valueOf(contentHit));
            setChartData(imageN, contentKey, view, currentPick);
            getPicker(contentKey);
            //textView Test

        }

        return view;
    }


    //투표한 사용자 uid parsing
    private void getPicker(String k) {
        DatabaseReference mDatabaseReference_;
        mDatabaseReference_ = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference_.child("user_contents").child(k).child("contentPicker").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String string = dataSnapshot.getValue().toString();
                int contentPickerCount = (int) dataSnapshot.getChildrenCount();

                ArrayList<String> stringArrayList = new ArrayList<>();
                String[] stringArray;
                stringArray = string.split("=");
                stringArray[0] = stringArray[0].replace("{", "");

                for (int i = 1; i < contentPickerCount; i++) {
                    int idx = stringArray[i].indexOf(" ");
                    int idx_ = stringArray[i].length();
                    stringArray[i] = stringArray[i].substring(idx + 1, idx_);
                }
                stringArray[contentPickerCount] = null;
                Collections.addAll(stringArrayList, stringArray);
                tt(stringArrayList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //투표한 사람 uid로 통계 구하기
    private void tt(ArrayList<String> stringArrayList) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        for (int i = 0; i < contentHit; i++) {
            mDatabaseReference.child("users").child(stringArrayList.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, Object> contentDTO = (Map<String, Object>) dataSnapshot.getValue();
                    if (contentDTO.get("sex").equals("남")){
                        Log.d("lkj sex 남자", "남");
//                        yy("남");
                    } else {
                        Log.d("lkj sex 여자", "여");
//                        uu("여");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void yy(String string) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(string);
        for (int i = 0; i < 10; i++) {
            if(stringArrayList.get(i)==null) Log.d("lkj yy", "lkj yy");
            else Log.d("lkj yy", stringArrayList.get(i));
        }
    }

    private void uu(String string) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add(string);
        for (int i = 0; i < 10; i++) {
            if(stringArrayList.get(i)==null) if(stringArrayList.get(i)==null) Log.d("lkj uu", "lkj uu");
            else Log.d("lkj uu", stringArrayList.get(i));
        }
    }



    //차트 세팅
    private void setChartData(final int contentN, String key, final View v, final int pick) {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("user_contents").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> contentDTO = (Map<String, Object>) dataSnapshot.getValue();

                ArrayList<String> labels = new ArrayList<>();
                ArrayList<BarEntry> yValue = new ArrayList<>();
                ArrayList<Integer> tmp = new ArrayList<>();

                for (int i = 0; i < contentN + 1; i++) {
                    int j = contentN - i + 1;
                    labels.add("`" + String.valueOf(j));
                }

                Object object0 = contentDTO.get("candidateScore_0");
                Object object1 = contentDTO.get("candidateScore_1");
                Object object2 = contentDTO.get("candidateScore_2");
                Object object3 = contentDTO.get("candidateScore_3");
                Object object4 = contentDTO.get("candidateScore_4");
                Object object5 = contentDTO.get("candidateScore_5");
                Object object6 = contentDTO.get("candidateScore_6");
                Object object7 = contentDTO.get("candidateScore_7");
                Object object8 = contentDTO.get("candidateScore_8");
                Object object9 = contentDTO.get("candidateScore_9");

                tmp.add(Integer.parseInt(object0.toString()));
                tmp.add(Integer.parseInt(object1.toString()));
                tmp.add(Integer.parseInt(object2.toString()));
                tmp.add(Integer.parseInt(object3.toString()));
                tmp.add(Integer.parseInt(object4.toString()));
                tmp.add(Integer.parseInt(object5.toString()));
                tmp.add(Integer.parseInt(object6.toString()));
                tmp.add(Integer.parseInt(object7.toString()));
                tmp.add(Integer.parseInt(object8.toString()));
                tmp.add(Integer.parseInt(object9.toString()));

                HorizontalBarChart pollActivity_horizontalBarChart_result = v.findViewById(R.id.pollActivity_horizontalBarChart_result);
//                HorizontalBarChart pollActivity_horizontalBarChart_result1 = v.findViewById(R.id.pollActivity_horizontalBarChart_result1);

                CategoryBarChartXaxisFormatter xAxisFormatter = new CategoryBarChartXaxisFormatter(labels);
                XAxis xAxis = pollActivity_horizontalBarChart_result.getXAxis();
                xAxis.setValueFormatter(xAxisFormatter);
                xAxis.setDrawAxisLine(false);
                xAxis.setDrawGridLines(false);
                xAxis.setGranularity(1);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setXOffset(10); // 후보 1,2,3,4,5....위치
                xAxis.setTextSize(20f); // 후보 1,2,3,4,5... 크기


                YAxis yAxis = pollActivity_horizontalBarChart_result.getAxisLeft();
                yAxis.setAxisMinimum(0);
                yAxis.setMinWidth(0);
                yAxis.setMaxWidth(3);
                yAxis.setDrawZeroLine(true);
                yAxis.setDrawTopYLabelEntry(true);


                yAxis.setCenterAxisLabels(true);
                yAxis.setEnabled(true);

//                pollActivity_horizontalBarChart_result.getDescription().setEnabled(false);
                pollActivity_horizontalBarChart_result.setTouchEnabled(true);
                pollActivity_horizontalBarChart_result.setDragEnabled(false);
                pollActivity_horizontalBarChart_result.setDoubleTapToZoomEnabled(false);
                pollActivity_horizontalBarChart_result.setPinchZoom(false);
                pollActivity_horizontalBarChart_result.setDescription(null);
                pollActivity_horizontalBarChart_result.animateY(2500);
                pollActivity_horizontalBarChart_result.setFitBars(true);
                pollActivity_horizontalBarChart_result.setDrawBarShadow(false);
                pollActivity_horizontalBarChart_result.getAxisLeft().setEnabled(false);
                pollActivity_horizontalBarChart_result.getAxisRight().setEnabled(false);
                pollActivity_horizontalBarChart_result.getXAxis().setEnabled(true);
                pollActivity_horizontalBarChart_result.setDrawValueAboveBar(true);
                pollActivity_horizontalBarChart_result.setDrawGridBackground(false);
                pollActivity_horizontalBarChart_result.getLegend().setEnabled(false);


                for (int i = 0; i < contentN; i++) {
                    yValue.add(new BarEntry((float) contentN - i, tmp.get(i)));
                }

                BarDataSet set1 = new BarDataSet(yValue, null);
//                set1.setColor(Color.GRAY);
                set1.setColors(ColorTemplate.MATERIAL_COLORS);

                BarData data1 = new BarData(set1);
                data1.setBarWidth(0.5f); //바 크기
                data1.setValueTextSize(15f); //결과값 크기
                data1.setValueTextColor(Color.GRAY);


                ResultValueFormatter resultValueFormatter = new ResultValueFormatter();
                data1.setValueFormatter(resultValueFormatter);

                pollActivity_horizontalBarChart_result.setData(data1);
                pollActivity_horizontalBarChart_result.invalidate(); //refresh


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("lkj setChartData", "setChartData ERR");

            }
        });

    }


    public class ResultValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;

        public ResultValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");


        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value) + "표";
        }
    }

    public class CategoryBarChartXaxisFormatter implements IAxisValueFormatter {
        ArrayList<String> mValues;

        public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int val = (int) value;
            String label = "";
            if (val >= 0 && val < mValues.size()) {
                label = mValues.get(val);
            } else {
                label = "";
            }

            return label;
        }
    }


}
