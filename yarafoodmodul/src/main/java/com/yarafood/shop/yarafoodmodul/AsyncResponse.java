package com.yarafood.shop.yarafoodmodul;

import java.util.ArrayList;
import entities.SalesOrderItem;

public interface AsyncResponse {
        void processFinish(ArrayList<SalesOrderItem> salesOrderItems);
}
