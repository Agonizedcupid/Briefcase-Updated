package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.briefcase.LandingActivity;
import com.aariyan.briefcase.Model.SurveyModel;
import com.aariyan.briefcase.Network.APIs;
import com.aariyan.briefcase.Network.ApiClient;
import com.aariyan.briefcase.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity {

    public String name = "";

    private TextView userName, howMuchIMade, toGo, target, gp, toGoText, weeksLeft, weeksLeftAmount;
    private TextView daysLeft, daysLeftAmount;

    private ProgressBar progressBar;

    private PieChart pieChart;

    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales2);

        initUI();

        if (getIntent() != null) {
            name = getIntent().getStringExtra("name");
            userName.setText(name);
            loadData(name);
        }

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalesActivity.this, LandingActivity.class));
            }
        });
    }

    private void initUI() {
        userName = findViewById(R.id.userNames);
        howMuchIMade = findViewById(R.id.howMuchIMade);
        toGo = findViewById(R.id.toGo);
        target = findViewById(R.id.target);
        gp = findViewById(R.id.gp);
        toGoText = findViewById(R.id.toGoText);
        weeksLeft = findViewById(R.id.weeksLeft);
        weeksLeftAmount = findViewById(R.id.weeksLeftAmount);
        daysLeft = findViewById(R.id.daysLeft);
        daysLeftAmount = findViewById(R.id.daysLeftAmount);

        pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Sales");

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setEnabled(true);
        l.setDrawInside(true);

        progressBar = findViewById(R.id.progressbar);
    }

    private void loadData(String n) {
        APIs apiService = ApiClient.getClient2().create(APIs.class);
        Call<ResponseBody> call = apiService.getSalesTarget(n);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject singleObject = array.getJSONObject(i);
                        String mnyMade = singleObject.getString("mnyMade");
                        String mnyTarget = singleObject.getString("mnyTarget");
                        String mnyTogo = singleObject.getString("mnyTogo");
                        String mnyGP = singleObject.getString("mnyGP");
                        int intweeksleft = singleObject.getInt("intweeksleft");
                        int intDaysLeft = singleObject.getInt("intDaysLeft");
                        String mnyToAchieveintheremainingweeks = singleObject.getString("mnyToAchieveintheremainingweeks");
                        String mnyToAchieveintheremainingdays = singleObject.getString("mnyToAchieveintheremainingdays");
                        String strRepName = singleObject.getString("strRepName");

                        howMuchIMade.setText(mnyMade);
                        target.setText(mnyTarget);
                        toGo.setText(mnyTogo);
                        gp.setText(mnyGP);
                        toGoText.setText("TO Go: " + mnyTogo);
                        weeksLeft.setText("" + intweeksleft);
                        weeksLeftAmount.setText(mnyToAchieveintheremainingweeks);
                        daysLeft.setText("" + intDaysLeft);
                        daysLeftAmount.setText(mnyToAchieveintheremainingdays);

                        int tg = (int) Double.parseDouble(mnyTogo);
                        int t = (int) Double.parseDouble(mnyTarget);

                        pieEntryList.add(new PieEntry(tg, "To Go"));
                        pieEntryList.add(new PieEntry(t, "Target"));

                        ArrayList<Integer> colors = new ArrayList<>();
                        for (int color : ColorTemplate.MATERIAL_COLORS) {
                            colors.add(color);
                        }

                        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "Sales");
                        pieDataSet.setColors(colors);
                        pieData = new PieData(pieDataSet);
                        pieData.setDrawValues(true);
                        pieData.setValueTextColor(Color.BLACK);
                        pieData.setValueTextSize(12);
                        //pieData.setValueFormatter( new DefaultAxisValueFormatter(10));
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                    }
                    progressBar.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(SalesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}