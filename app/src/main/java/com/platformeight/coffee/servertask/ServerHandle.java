/*
 * Create by platform eight on 2019. 10. 22.
 * Copyright (c) 2019. platform eight. All rights reserved.
 */

package com.platformeight.coffee.servertask;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.platformeight.coffee.MemberData;
import com.platformeight.coffee.MyApplication;
import com.platformeight.coffee.OrderData;
import com.platformeight.coffee.ShopData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.platformeight.coffee.Constant.local_name;
import static com.platformeight.coffee.Constant.member_select;
import static com.platformeight.coffee.Constant.order_insert_failure;
import static com.platformeight.coffee.Constant.order_insert_success;
import static com.platformeight.coffee.Constant.orderlist_user;
import static com.platformeight.coffee.Constant.register_failure;
import static com.platformeight.coffee.Constant.register_success;
import static com.platformeight.coffee.Constant.server_name;
import static com.platformeight.coffee.Constant.shoplist_select;

public class ServerHandle {

    static final String TAG = "ServerHandle";

    String url;
    JSONObject json;

    public ServerHandle(){
        url = "";
        json = new JSONObject();
    }
    /*
     * 회원정보관련
     */
    public int findID(String email) {
        url = local_name+"member_id_check.php";
        json = new JSONObject();
        try {
            json.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parserCount(result);
    }
    int parserCount(String str){
        int cnt = -1;
        try{
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(member_select);
            JSONObject tt = index.getJSONObject(0);
            cnt = tt.getInt("cnt");
        }
        catch (JSONException e){
            Log.d(TAG, "parserCount: "+str);
        }
        return cnt;
    }
    public String login(String email,String pass) { //email, pass
        url = local_name+"member_select.php";
        json = new JSONObject();
        try {
            json.put("email", email);
            json.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return parserUser(result);
    }
    String parserUser(String str){
        String result = "";
        MyApplication.user = new MemberData();
        try{
            if (str==null) return "값없음";
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(member_select);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                MyApplication.user.setNo(tt.getInt("no"));
                MyApplication.user.setName(tt.getString("name"));
                MyApplication.user.setPhone(tt.getString("phone"));
                MyApplication.user.setState(tt.getInt("state"));
                MyApplication.user.setPoint(tt.getInt("point"));
            }
            if(result.equals("")) return "값없음";
        }
        catch (JSONException e){
            Log.d(TAG, "parserUser: "+str);
        }
        return result;
    }

    public boolean register(MemberData user) {
        url = local_name+"member_insert.php";
        json = new JSONObject();
        try {
            json.put("id", user.getEmail());
            json.put("pass", user.getPass());
            json.put("name", user.getName());
            json.put("phone", user.getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains(register_failure)){
            Log.d(TAG, "register sql query: "+result);
            return false;
        }else if (result.contains("failure")){
            Log.d(TAG, "register connection : "+result);
            return false;
        }
        return result.contains(register_success);
    }

    public String setToken(int no, String table, String token){
        url = local_name+"token_update.php";
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("table", table);
            json.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains(register_failure)){
            Log.d(TAG, "register sql query: "+result);
        }else if (result.contains("failure")){
            Log.d(TAG, "register connection : "+result);
        }
        return result;
    }

    /*
    * 가게관련
     */

    public List<ShopData> getShopList(String location, LatLng latLng) { //
        url = local_name+"shoplist_gps.php";
        json = new JSONObject();
        try {
            json.put("location", location);
            json.put("mapx", latLng.longitude);
            json.put("mapy", latLng.latitude);
            Log.d(TAG, "getShopList: mapx long"+latLng.longitude+" mapy lat" + latLng.latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return parserShop(result);
    }

    List<ShopData> parserShop(String str){
        String result = "";
        List<ShopData> mValues = new ArrayList<ShopData>();
        Log.d(TAG, "parserShop: "+str);
        try{
            if (str==null) return null;
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(shoplist_select);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                Log.d(TAG, "parserShop: "+String.valueOf(tt.getInt("no")) + tt.getString("name"));
                ShopData data= new ShopData(String.valueOf(tt.getInt("no")), tt.getString("image"), tt.getString("name"), tt.getString("menu"),tt.getInt("state"), tt.getString("shop_open"), tt.getString("shop_close"), tt.getDouble("mapx"), tt.getDouble("mapy"), tt.getDouble("distance"));
                //LocalTime testTime = LocalTime.of(6,0);
                if (data.getShopOpen().isAfter(LocalTime.now()) || data.getShopClose().isBefore(LocalTime.now())){
                    //data.setState(0);
                }
                mValues.add(data);

            }
            if(result.equals("")) return null;
        }
        catch (JSONException e){
            Log.d(TAG, "parserShop: "+str);
        }
        Log.d(TAG, "parserShop: "+mValues.size());
        return mValues;
    }

    /*
     * 주문 처리
     */
    public boolean sendOrder(JSONObject js_menu) {
        url = local_name+"order_insert.php";
        NetworkTask networkTask = new NetworkTask(url, js_menu);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains(order_insert_failure)){
            Log.d(TAG, "order sql query: "+result);
            return false;
        }else if (result.contains("failure")){
            Log.d(TAG, "order connection : "+result);
            return false;
        }
        return result.contains(order_insert_success);
    }

    public boolean sendFCM(int no, String table) {
        url = server_name+"fcm test.php";
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("table", table);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "sendFCM: "+result);
        return !result.contains("failure");
    }
    public String getOrderList(int no, int state) {
        url = local_name+"orderlist_user.php";
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("state", state);
            //Log.d(TAG, "getShopList: mapx long"+latLng.longitude+" mapy lat" + latLng.latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkTask networkTask = new NetworkTask(url, json);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains("failure")){
            Log.d(TAG, "orderlist connection error : "+result);
        }
        JSONObject order = null;
        try {
            order = new JSONObject(result);
            return order.getJSONArray(orderlist_user).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    List<OrderData> parserMyOrder(String str){
        String result = "";
        List<OrderData> mValues = new ArrayList<OrderData>();
        Log.d(TAG, "parserMyOrder: "+str);
        try{
            if (str==null) return null;
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(orderlist_user);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                Log.d(TAG, "parserMyOrder: "+String.valueOf(tt.getInt("no")) + tt.getString("name"));
                OrderData data= new OrderData(tt.getInt("no"), tt.getInt("shop_no"), tt.getString("shop_name"), tt.getString("address"), tt.getInt("total_price"), tt.getInt("order_price"),tt.getInt("order_point"),tt.getInt("order_amount"),tt.getString("detail"), tt.getString("order_time"), tt.getInt("state"));
                mValues.add(data);

            }
            if(result.equals("")) return null;
        }
        catch (JSONException e){
            Log.d(TAG, "parserShop: "+str);
        }
        Log.d(TAG, "parserShop: "+mValues.size());
        return mValues;
    }

}
