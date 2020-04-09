package com.yarafood.shop.yarafoodmodul;

import androidx.appcompat.app.AppCompatActivity;
import entities.SalesOrderItem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Item extends AppCompatActivity implements AsyncResponse {

    public long id;
    public ArrayList<SalesOrderItem> items;
    public SalesOrderItem currentItem;
    public int itemCounter;
    public int itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        final long orderID = intent.getLongExtra("id", 0);
        this.id = orderID;
        HttpRequestItems httpRequestAsk = new HttpRequestItems(this);
        httpRequestAsk.execute();

        TextInputEditText scannedProductTxtEdit = (TextInputEditText) findViewById(R.id.scannedProductTxtEdit);
        scannedProductTxtEdit.setCursorVisible(false);

        /*scannedProductTxtEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Context context = getApplicationContext();
                if (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    OnEnter();
                    return true;
                }
                return false;
            }
        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceivers();
    }

    private void registerReceivers() {
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(receiver, filter);
    }


    private void unRegisterReceivers() {
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get result of the suspend/resume API call
            String action = intent.getAction();
            Bundle b = intent.getExtras();

            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                //  Received a barcode scan
                try {
                    TextInputEditText scannedProductTxtEdit = (TextInputEditText) findViewById(R.id.scannedProductTxtEdit);

                        String decodedSource = intent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
                        String decodedData = intent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
                        String decodedLabelType = intent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));

                        scannedProductTxtEdit.setText(decodedData);

                } catch (Exception e) {
                    //  Catch if the UI does not exist when we receive the broadcast
                }
            }
        }
    };

    @Override
    public void processFinish(ArrayList<SalesOrderItem> salesOrderItems) {
        this.items = salesOrderItems;
        startShowItem();
    }

    public void startShowItem() {
        this.itemCounter = 0;
        this.currentItem = this.items.get(itemCounter);
        this.itemQuantity = this.currentItem.getQuantity();
        setItem(currentItem);
    }

    public void setItem(SalesOrderItem item) {
        TextView orderIdTxtV = (TextView) findViewById(R.id.orderIdTxtV);
        TextView customerTxtV = (TextView) findViewById(R.id.customerTxtV);
        TextView productDescrTxtV = (TextView) findViewById(R.id.ProductDescrTxtV);
        TextView quantityTxtV = (TextView) findViewById(R.id.quantityTxtV);
        TextView productIdTxtV = (TextView) findViewById(R.id.ProductIDTxtV);

        orderIdTxtV.setText(String.valueOf(item.getSalesOrder().getId()));
        customerTxtV.setText(item.getSalesOrder().getCustomer());
        productDescrTxtV.setText(item.getProduct().getDescription());
        quantityTxtV.setText(String.valueOf(item.getQuantity()));
        productIdTxtV.setText(String.valueOf(item.getProduct().getId()));
    }

    public void OnEnter() {

        TextInputEditText scannedProductTxtEdit = (TextInputEditText) findViewById(R.id.scannedProductTxtEdit);
        String productIdScanned = scannedProductTxtEdit.getText().toString();
        scannedProductTxtEdit.setText("");
        if (productIdScanned.isEmpty()) {
            toastNotOkay("Product ID field is empty, Scan again!");
        } else if (!productIdScanned.equals(currentItem.getProduct().getId())) {
            toastNotOkay("Wrong Product!! Scan again!!");
        } else if (productIdScanned.equals(currentItem.getProduct().getId())) {
            toastOkay();
            int itemQuantity = currentItem.getQuantity();
            if (itemQuantity > 1) {
                itemQuantity--;
                currentItem.setQuantity(itemQuantity);
                setItem(currentItem);
            } else {
                itemCounter++;
                if (itemCounter < items.size()) {
                    currentItem = items.get(itemCounter);
                    itemQuantity = currentItem.getQuantity();
                    setItem(currentItem);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Order Finished!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, text, duration).show();
                    finish();
                }
            }
        }

    }

    public void toastOkay() {

        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(this).inflate(R.layout.custom_toast, null);
        TextView textView = view.findViewById(R.id.toast);
        textView.setText("Scan Okay!");
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toastNotOkay(String message) {

        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(this).inflate(R.layout.custom_toast_block, null);
        TextView textView = view.findViewById(R.id.notOkayToast);
        textView.setText(message);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}


