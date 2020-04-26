package com.yarafood.shop.yarafoodmodul;

import Models.SalesOrderRest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import entities.SalesOrder;
import entities.SalesOrderItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OrderAsyncResponse{

    public OrderListAdapter adapter;
    public AlertDialog.Builder checkEditDialog;
    public SalesOrder selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkEditDialog = new AlertDialog.Builder(this);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpRequestAsk httpRequestAsk = new HttpRequestAsk(this);
        httpRequestAsk.execute();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    HttpOrderStatusRequest httpOrderStatusRequest = new HttpOrderStatusRequest(MainActivity.this);
                    httpOrderStatusRequest.execute("InProcess", String.valueOf(selectedOrder.getId()) );
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };

    @Override
    public void processFinish(boolean result) {
        if(result){
            Intent item = new Intent(MainActivity.this, Item.class);
            long orderIdSelected = selectedOrder.getId();
            item.putExtra("id", orderIdSelected);
            startActivity(item);
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, ResponseEntity<ArrayList<SalesOrder>>> {

        Context context;

        public HttpRequestAsk(Context context) {
            this.context = context;
        }

        @Override
        protected ResponseEntity<ArrayList<SalesOrder>> doInBackground(Void... Void) {
            return new SalesOrderRest().findAll();
        }

        @Override
        protected void onPostExecute(ResponseEntity<ArrayList<SalesOrder>> salesOrderResponseEntity) {
            super.onPostExecute(salesOrderResponseEntity);

            ArrayList<SalesOrder> salesOrderList = salesOrderResponseEntity.getBody();
            adapter = new OrderListAdapter(context, salesOrderList);

            ListView listView = (ListView) findViewById(R.id.ordersListView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkEditDialog.setMessage("Do you want to start picking this order?");
                    checkEditDialog.setPositiveButton("Start", dialogClickListener);
                    checkEditDialog.setNegativeButton("Cancel", dialogClickListener);
                    AlertDialog dialog = checkEditDialog.create();
                    checkEditDialog.show();
                    Object object = parent.getItemAtPosition(position);
                    selectedOrder = (SalesOrder) object;
                }
            });
        }
    }
}
