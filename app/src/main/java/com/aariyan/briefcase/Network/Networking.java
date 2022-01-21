package com.aariyan.briefcase.Network;

import android.content.Context;


import com.aariyan.briefcase.Constant.Constant;
import com.aariyan.briefcase.Interface.CustomerListInterface;
import com.aariyan.briefcase.Interface.MessageListInterface;
import com.aariyan.briefcase.Model.CustomerModel;
import com.aariyan.briefcase.Model.MessageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Networking {
    APIs apiService;
    private Context context;
    List<MessageModel> list = new ArrayList<>();
    List<CustomerModel> customerList = new ArrayList<>();

    public Networking(Context context) {
        this.context = context;
        apiService = ApiClient.getClient2().create(APIs.class);
    }

    public void getMessageList(MessageListInterface messageListInterface) {
        Call<ResponseBody> call = apiService.getMessageList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Constant.listOfMessage.clear();
                    JSONArray finalResponse = new JSONArray(response.body().string());
                    for (int i=0; i<finalResponse.length(); i++) {
                        JSONObject object = finalResponse.getJSONObject(i);
                        String intAutoId = object.getString("intAutoId");
                        int intDepId = object.getInt("intDepId");
                        String Department = object.getString("Department");
                        String messagestofollow = object.getString("messagestofollow");
                        int intCreatedBy = object.getInt("intCreatedBy");
                        int intAssignedTo = object.getInt("intAssignedTo");
                        JSONObject o = object.getJSONObject("dteTimeCreate");
                        String date = o.getString("date");
                        int intStatusId = object.getInt("intStatusId");
                        String strSubject = object.getString("strSubject");
                        String FromUser = object.getString("FromUser");

                        MessageModel model = new MessageModel(
                                intAutoId,intDepId,Department,messagestofollow,intCreatedBy,intAssignedTo,
                                date,intStatusId,strSubject,FromUser
                        );

                        list.add(model);
                    }

                    messageListInterface.messageList(list);

                }catch (Exception e) {
                    messageListInterface.error(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                messageListInterface.error(e.getMessage());
            }
        });

    }

    public void getCustomerList(CustomerListInterface customerListInterface) {
        Call<ResponseBody> call = apiService.getCustomerList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONArray finalResponse = new JSONArray(response.body().string());
                    for (int i=0; i<finalResponse.length(); i++) {
                        JSONObject object = finalResponse.getJSONObject(i);
                        String CustomerPastelCode = object.getString("CustomerPastelCode");
                        String StoreName = object.getString("StoreName");
                        String lastNotes = object.getString("lastNotes");
                        String lastDate = object.getString("lastDate");
                        CustomerModel model = new CustomerModel(CustomerPastelCode,StoreName,lastNotes,lastDate);
                        customerList.add(model);
                    }

                    customerListInterface.customerList(customerList);

                }catch (Exception e) {
                    customerListInterface.error(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                customerListInterface.error(e.getMessage());
            }
        });

    }
}
