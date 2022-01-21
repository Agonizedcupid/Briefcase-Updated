package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.briefcase.R;

import java.util.Calendar;

public class ViewMemoVisit extends AppCompatActivity {

    private WebView webView;
    private ImageView backBtn;
    private ProgressBar progressBar;
    private TextView dateFrom, dateTo;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    int day, month, year;

    private String type = "";
    private TextView toolbarText;
    private TextView name, code;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memo_visit);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        initUI();

        if (getIntent() != null) {
            type = getIntent().getStringExtra("type");
            toolbarText.setText("View " + type);

            name.setText(getIntent().getStringExtra("name"));
            code.setText(getIntent().getStringExtra("code"));
        }
    }

    private void initUI() {

        name = findViewById(R.id.ccName);
        code = findViewById(R.id.ccCode);
        submit = findViewById(R.id.submit);

        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
        toolbarText = findViewById(R.id.toolbarText);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(ViewMemoVisit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        int j = i1 + 1;

                        String date = i + "-" + j + "-" + i2;
                        Toast.makeText(ViewMemoVisit.this, "You've selected " + date, Toast.LENGTH_LONG).show();
                        //Toast.makeText(AddProperty.this, "" + availableStatus, Toast.LENGTH_SHORT).show();
                        dateFrom.setText(date);

                    }
                }, day, month, year);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(ViewMemoVisit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        int j = i1 + 1;

                        String date = i + "-" + j + "-" + i2;
                        Toast.makeText(ViewMemoVisit.this, "You've selected " + date, Toast.LENGTH_LONG).show();
                        //Toast.makeText(AddProperty.this, "" + availableStatus, Toast.LENGTH_SHORT).show();
                        dateTo.setText(date);

                    }
                }, day, month, year);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });

        webView = findViewById(R.id.webView);
        backBtn = findViewById(R.id.backButton);
        progressBar = findViewById(R.id.progressbar);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheLink(type);
            }
        });
    }

    private void getTheLink(String type) {
        String url = "";
        if (type.equals("Visit")) {
            url = "http://102.37.0.48/memos/GetVisits.php?from=" + dateFrom.getText().toString() + "&to=" + dateTo.getText().toString() + "&userId=" + 1;
            loadWebView(url);
        } else if (type.equals("Memo")) {
            url = "http://102.37.0.48/memos/GetReminderswebview.php?from=" + dateFrom.getText().toString() + "&to=" + dateTo.getText().toString();
            loadWebView(url);
        }
    }

    private void loadWebView(String urlLink) {
        webView.loadUrl(urlLink);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}