package com.yarafood.shop.yarafoodmodul;

import java.util.ArrayList;

import entities.SalesOrderItem;

public interface OrderAsyncResponse {
        void processFinish(boolean result);
}
