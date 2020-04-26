package com.yarafood.shop.yarafoodmodul;

import android.content.Context;
import android.os.AsyncTask;
import android.text.BoringLayout;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import Models.SalesOrderRest;
import entities.SalesOrder;
import entities.SalesOrderItem;

public class HttpOrderStatusRequest extends AsyncTask<String, Void, ResponseEntity<Boolean>> {

    Context context;

    public HttpOrderStatusRequest(Context context){
        this.context = context;
    }

    @Override
    protected ResponseEntity<Boolean> doInBackground(String... status) {
        MainActivity mainActivity = (MainActivity) context;
        try {
            SalesOrderRest salesOrderRest = new SalesOrderRest();
            return salesOrderRest.SetOrderStatus( status[0], status[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ResponseEntity<Boolean> result) {
        super.onPostExecute(result);
        boolean booleanResult = result.getBody().booleanValue();
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.processFinish(booleanResult);
    }
}
