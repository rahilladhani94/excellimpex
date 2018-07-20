package com.excelimpex.WebServices;



import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


public class ApiHandler {

    private static final String BASE_URL = "http://www.excelimpex.in/backend/api/api/";



    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(600);
    private static WebServices apiService,apiService1,apiService_recycle;
//    RequestInterceptor requestInterceptor = new RequestInterceptor() {
//        @Override
//        public void intercept(RequestFacade request) {
//            request.addPathParam("apiKey", DataProvider.getInstance(context).getApiKey();
//        }
//    };

    public static WebServices getApiService() {
        if (apiService1 == null) {


            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setClient(new OkClient(okHttpClient))
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            apiService1 = restAdapter.create(WebServices.class);
            return apiService1;
        } else {
            return apiService1;
        }


    }



}
