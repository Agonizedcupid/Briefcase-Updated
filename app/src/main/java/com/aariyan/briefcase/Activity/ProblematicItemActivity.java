package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.briefcase.Adapter.ProblematicAdapter;
import com.aariyan.briefcase.Model.ProblematicModel;
import com.aariyan.briefcase.Network.APIs;
import com.aariyan.briefcase.Network.ApiClient;
import com.aariyan.briefcase.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProblematicItemActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView storeName,storeCode;
    private String sName,sCode;
    private String userId = "18";
    private ProblematicAdapter adapter;
    private List<ProblematicModel> list = new ArrayList<>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problematic_item);

        initUI();

        if (getIntent() != null) {
            sName = getIntent().getStringExtra("name");
            sCode = getIntent().getStringExtra("code");

            storeName.setText("Store: "+sName);
            storeCode.setText("Code: "+sCode);

            loadData(sCode);
        }
    }

    private void initUI() {

        progressBar = findViewById(R.id.progressbar);
        storeName = findViewById(R.id.storeNames);
        storeCode = findViewById(R.id.storeCodes);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.logVisits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProblematicItemActivity.this,LogVisit.class)
                        .putExtra("name",storeName.getText().toString())
                        .putExtra("code",storeCode.getText().toString())
                );
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void loadData(String name) {
        APIs apIs = ApiClient.getClient2().create(APIs.class);
        Call<ResponseBody> call = apIs.getProblematicItem(userId,name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONArray array = new JSONArray(response.body().string());
                    list.clear();
                    for (int i=0; i<array.length(); i++) {
                        JSONObject single = array.getJSONObject(i);
                        String CustomerPastelCode = single.getString("CustomerPastelCode");
                        String StoreName = single.getString("StoreName");
                        String PastelDescription = single.getString("PastelDescription");
                        String PastelCode = single.getString("PastelCode");
                        String strSalesName = single.getString("strSalesName");
                        String decLastYear = single.getString("decLastYear");
                        String decLastMonth = single.getString("decLastMonth");
                        String decPriorMonth = single.getString("decPriorMonth");
                        String decDifference = single.getString("decDifference");
                        String wasNotActive = single.getString("wasNotActive");

                        ProblematicModel model = new ProblematicModel(
                                CustomerPastelCode,
                                StoreName,
                                PastelDescription,
                                PastelCode,
                                strSalesName,
                                decLastYear,
                                decLastMonth,
                                decPriorMonth,
                                decDifference,
                                wasNotActive
                        );

                        list.add(model);
                    }
                    adapter = new ProblematicAdapter(ProblematicItemActivity.this,list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(ProblematicItemActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(ProblematicItemActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}