package com.aariyan.briefcase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aariyan.briefcase.Activity.ButtonActivity;
import com.aariyan.briefcase.Activity.CollaborationActivity;
import com.aariyan.briefcase.Activity.CustomerNameActivity;
import com.aariyan.briefcase.Activity.StockSheetActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class LandingActivity extends AppCompatActivity {

    private CardView orderAndLoadedDeals;
    private CardView salesTargets;
    private CardView stockSheet;
    private CardView visitMemos;
    private CardView collaborating;
    private CardView cPanel;
    private CardView createReport;

    private View bottomSheet;
    private BottomSheetBehavior behavior;

    private EditText customerName, customerCode;
    private Button getHistoryBtn;

    private EditText getSalesEditText;
    private Button getSalesBtn;

    private ImageView closeBottomSheet;

    private LinearLayout orderHistoryLayout,salesLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //Instantiate UI:
        initUI();
    }

    private void initUI() {

        orderHistoryLayout = findViewById(R.id.orderHistoryLayout);
        salesLayout = findViewById(R.id.salesLayout);

        orderAndLoadedDeals = findViewById(R.id.ordersLoadedDeals);
        salesTargets = findViewById(R.id.salesTargets);
        stockSheet = findViewById(R.id.stockSheet);
        visitMemos = findViewById(R.id.visitMemos);
        collaborating = findViewById(R.id.collaborating);
        cPanel = findViewById(R.id.cPanel);
        createReport = findViewById(R.id.createReport);

        closeBottomSheet = findViewById(R.id.closeBottomSheet);
        closeBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                orderHistoryLayout.setVisibility(View.GONE);
                salesLayout.setVisibility(View.GONE);
            }
        });

        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this, ButtonActivity.class));
            }
        });

        bottomSheet = findViewById(R.id.bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        customerName = findViewById(R.id.customerNameEdtText);
        customerCode = findViewById(R.id.customerCodeEdtText);
        getHistoryBtn = findViewById(R.id.orderHistoryBtn);

        getSalesEditText = findViewById(R.id.getSalesEdtText);
        getSalesBtn = findViewById(R.id.getSalesBtn);

        orderAndLoadedDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderHistoryLayout.setVisibility(View.VISIBLE);
                salesLayout.setVisibility(View.GONE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        salesTargets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderHistoryLayout.setVisibility(View.GONE);
                salesLayout.setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        getSalesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (TextUtils.isEmpty(getSalesEditText.getText().toString().trim())) {
                    getSalesEditText.setError("Pleas enter the code!");
                    getSalesEditText.requestFocus();
                    return;
                }

                Intent intent = new Intent(LandingActivity.this, Sales.class);
                intent.putExtra("code", getSalesEditText.getText().toString().trim());
                startActivity(intent);
            }
        });

        getHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Intent intent = new Intent(LandingActivity.this, OrderActivity.class);
                intent.putExtra("name", customerName.getText().toString());
                intent.putExtra("code", customerCode.getText().toString());
                startActivity(intent);
            }
        });

        stockSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, StockSheetActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        visitMemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, CustomerNameActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        collaborating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this, CollaborationActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}