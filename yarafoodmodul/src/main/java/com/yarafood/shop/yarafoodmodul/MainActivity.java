package com.yarafood.shop.yarafoodmodul;

import Models.SalesOrderRest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import entities.SalesOrder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpRequestAsk httpRequestAsk = new HttpRequestAsk(this);
        httpRequestAsk.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.layout.me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:

                break;
            default:
                break;
        }
        return false;
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, ResponseEntity<ArrayList<SalesOrder>>> {

        Context context;

        public HttpRequestAsk(Context context) {
            this.context = context;
        }

        @Override
        protected ResponseEntity<ArrayList<SalesOrder>> doInBackground(Void... Void) {
            //setProgressBarIndeterminateVisibility(true);
            return new SalesOrderRest().findAll();
        }

        @Override
        protected void onPostExecute(ResponseEntity<ArrayList<SalesOrder>> salesOrderResponseEntity) {
            super.onPostExecute(salesOrderResponseEntity);
            //setProgressBarIndeterminateVisibility(false);
            ArrayList<SalesOrder> salesOrderList = salesOrderResponseEntity.getBody();
            adapter = new OrderListAdapter(context, salesOrderList);

            ListView listView = (ListView) findViewById(R.id.ordersListView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent item = new Intent(MainActivity.this, Item.class);
                    Object object = parent.getItemAtPosition(position);
                    SalesOrder order = (SalesOrder) object;
                    long orderIdSelected = order.getId();
                    item.putExtra("id", orderIdSelected);
                    startActivity(item);
                }
            });
        }
    }
}
