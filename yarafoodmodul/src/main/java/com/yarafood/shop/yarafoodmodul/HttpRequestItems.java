package com.yarafood.shop.yarafoodmodul;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;

import Models.SalesOrderRest;
import entities.SalesOrder;
import entities.SalesOrderItem;

public class HttpRequestItems extends AsyncTask<Void, Void, ResponseEntity<ArrayList<SalesOrderItem>>> {

    Context context;


    public HttpRequestItems(Context context){
        this.context = context;
    }

    @Override
    protected ResponseEntity<ArrayList<SalesOrderItem>> doInBackground(Void... Void) {
        Item item = (Item) context;
        return new SalesOrderRest().findOrderItems( item.id );
    }

    @Override
    protected void onPostExecute(ResponseEntity<ArrayList<SalesOrderItem>> salesOrderItemsRespEntity) {
        super.onPostExecute(salesOrderItemsRespEntity);
        ArrayList<SalesOrderItem> salesOrderItemsList = salesOrderItemsRespEntity.getBody();
        Item item = (Item) context;
        item.processFinish(salesOrderItemsList);
    }
}
