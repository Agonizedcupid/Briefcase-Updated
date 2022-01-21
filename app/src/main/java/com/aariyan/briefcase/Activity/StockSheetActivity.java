package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aariyan.briefcase.R;

public class StockSheetActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView backBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_sheet);
        initUI();
    }

    private void initUI() {
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

        loadWebView("http://102.37.0.48/memos/GetStockSheet.php");
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