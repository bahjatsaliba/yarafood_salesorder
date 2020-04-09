package Models;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;

import androidx.annotation.RequiresApi;
import entities.*;

public class SalesOrderRest {

    private String BASE_URL = "https://yaraproductdb.herokuapp.com/";
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<SalesOrder> find(int id){

        try{
            HttpEntity<String> entity = prepareHeader();
            ResponseEntity<SalesOrder> response = restTemplate.exchange(BASE_URL+"orders/"+id, HttpMethod.GET, entity, SalesOrder.class);
            return  response;
        }catch(Exception e){
            return null;
        }

    }

    public ResponseEntity<ArrayList<SalesOrder>> findAll(){

        try{
            HttpEntity<String> entity = prepareHeader();
            ResponseEntity<ArrayList<SalesOrder>> responseEntity = restTemplate.exchange(
                                        BASE_URL + "orders",
                                            HttpMethod.GET,
                                            entity,
                                            new ParameterizedTypeReference<ArrayList<SalesOrder>>() {
            });
            return responseEntity;
        }catch(Exception e){
            return null;
        }
    }

    public ResponseEntity<ArrayList<SalesOrderItem>> findOrderItems(long orderID){

        try{
            HttpEntity<String> entity = prepareHeader();
            ResponseEntity<ArrayList<SalesOrderItem>> responseEntity = restTemplate.exchange(
                    BASE_URL + "orders" + "/" + orderID + "/items",
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ArrayList<SalesOrderItem>>() {
                    });
            return responseEntity;
        }catch(Exception e){
            return null;
        }

    }

    private HttpEntity<String> prepareHeader(){

        String auth = "yarafood"+ ":"+ "Enschede2019";
        String base64Credntials = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();


        String authHeader = "Basic " + base64Credntials;
        headers.add("Authorization", authHeader);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return entity;
    }
}
