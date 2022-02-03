package com.aariyan.briefcase.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {

    @GET("GetOrders.php?")
    Call<ResponseBody> getOrderList(@Query("customercode") String customerCode, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);


    @GET("GetOrderLines.php?")
    Call<ResponseBody> getItemList(@Query("orderline") String orderId);

    @GET("GetSalesvstargets.php?")
    Call<ResponseBody> getSales(@Query("RepCode") String code);


    //Memo APP:
    @GET("getmessages")
    Call<ResponseBody> getMessageList();

    @GET("GetCustomers")
    Call<ResponseBody> getCustomerList();

    @GET("GetCustomerLastVisit?")
    Call<ResponseBody> getCustomerVisitMessage(@Query("customerCode") String customerCode);

//    @FormUrlEncoded
//    @POST("Logvisit")
//    Call<String> submitLogVisit(@Field("CustomerCode") String customerCode, @Field("notes") String notes,
//                                      @Field("catchupnotes") String catchUpNotes, @Field("nextvisit") String nextVisitDate,
//                                      @Field("userid") int userId
//    );

    @FormUrlEncoded
    @POST("Logvisit")
    Call<String> submitLogVisit(@Field("CustomerCode") String customerCode, @Field("notes") String notes,
                                @Field("catchupnotes") String catchUpNotes, @Field("nextvisit") String nextVisitDate,
                                @Field("userid") int userId, @Field("Lat") double latitude, @Field("Lon") double longitude,
                                @Field("answeroneid") String answeroneid, @Field("answeronetext") String answeronetext,
                                @Field("answertwoid") String answertwoid, @Field("answertwotext") String answertwotext,
                                @Field("answerthreeid") String answerthreeid, @Field("answerthreetext") String answerthreetext,
                                @Field("customersatisfactoyanswer") String customersatisfactoyanswer
    );

    @FormUrlEncoded
    @POST("Logreminder")
    Call<String> createMemo(@Field("CustomerCode") String customerCode, @Field("notes") String notes,
                            @Field("dates") String dates,
                            @Field("userid") int userId
    );

    @GET("GetVisits.php?")
    Call<ResponseBody> getVisits(@Query("from") String startDate, @Query("to") String endDate, @Query("userId") int userId);

    @GET("GetReminderswebview.php?")
    Call<ResponseBody> getMemo(@Query("from") String startDate, @Query("to") String endDate);

    @GET("GetSurvey.php?LocationId=1")
    Call<ResponseBody> surveyQuestion();

    @GET("GetSalesSummaryTargetPerRep.php?")
    Call<ResponseBody> getSalesTarget(@Query("RepCode") String name);

    @GET("GetCustomerDecreasingSales.php?")
    Call<ResponseBody> getCustomerDecreasingSales(@Query("userid") String userId);

    @GET("GetCustomerProblematicItems.php?")
    Call<ResponseBody> getProblematicItem(@Query("userid") String userId, @Query("customercode") String customerCode);


    @GET("GetNextVisits.php?")
    Call<ResponseBody> getDailySchedule(@Query("userid") String userId, @Query("from") String from,@Query("to") String to);

}
