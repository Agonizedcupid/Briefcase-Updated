package com.aariyan.briefcase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aariyan.briefcase.Adapter.CustomerAdapter;
import com.aariyan.briefcase.Adapter.DecreasingSalesAdapter;
import com.aariyan.briefcase.Interface.CustomerListInterface;
import com.aariyan.briefcase.Model.CustomerModel;
import com.aariyan.briefcase.Model.DecreasingSalesModel;
import com.aariyan.briefcase.Network.APIs;
import com.aariyan.briefcase.Network.ApiClient;
import com.aariyan.briefcase.Network.Networking;
import com.aariyan.briefcase.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerNameActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private EditText search;
    private ProgressBar progressBar;

    //Problematic Items
    private RecyclerView recyclerViews;
    private List<DecreasingSalesModel> lists = new ArrayList<>();
    private DecreasingSalesAdapter decreasingSalesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_name);

        initUI();

        loadProblematicCustomer();
    }

    private void loadProblematicCustomer() {
        APIs api = ApiClient.getClient2().create(APIs.class);
        Call<ResponseBody> call = api.getCustomerDecreasingSales("" + 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray finalResponse = new JSONArray(response.body().string());
                    lists.clear();
                    for (int i=0; i<finalResponse.length(); i++) {
                        JSONObject singleObject = finalResponse.getJSONObject(i);
                        String Code = singleObject.getString("Code");
                        String StoreName = singleObject.getString("StoreName");
                        String LastYear = singleObject.getString("LastYear");
                        String lym = singleObject.getString("lym");
                        String cym = singleObject.getString("cym");
                        String diff = singleObject.getString("diff");

                        DecreasingSalesModel model = new DecreasingSalesModel(
                                Code,StoreName,LastYear,lym,cym,diff
                        );
                        lists.add(model);
                        decreasingSalesAdapter = new DecreasingSalesAdapter(CustomerNameActivity.this,lists);
                        recyclerViews.setAdapter(decreasingSalesAdapter);
                        decreasingSalesAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                }catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CustomerNameActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CustomerNameActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initUI() {


        recyclerViews = findViewById(R.id.recyclerViewProblematicCustomer);
        recyclerViews.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = findViewById(R.id.customerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search = findViewById(R.id.searchHere);
        progressBar = findViewById(R.id.progressbar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loadData();
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadData() {
        Networking networking = new Networking(CustomerNameActivity.this);
        networking.getCustomerList(new CustomerListInterface() {
            @Override
            public void customerList(List<CustomerModel> list) {
                if (!list.isEmpty()) {
                    adapter = new CustomerAdapter(CustomerNameActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CustomerNameActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void error(String message) {
                Toast.makeText(CustomerNameActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}