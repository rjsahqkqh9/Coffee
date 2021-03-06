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

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.platformeight.coffee.Constant.fcm_basic;
import static com.platformeight.coffee.Constant.MEMBER_SELECT;
import static com.platformeight.coffee.Constant.ORDER_INSERT_FAILURE;
import static com.platformeight.coffee.Constant.ORDER_INSERT_SUCCESS;
import static com.platformeight.coffee.Constant.ORDERLIST_USER;
import static com.platformeight.coffee.Constant.REGISTER_FAILURE;
import static com.platformeight.coffee.Constant.REGISTER_SUCCESS;
import static com.platformeight.coffee.Constant.member_id_check;
import static com.platformeight.coffee.Constant.member_login;
import static com.platformeight.coffee.Constant.member_pass_check;
import static com.platformeight.coffee.Constant.member_refresh;
import static com.platformeight.coffee.Constant.member_register;
import static com.platformeight.coffee.Constant.member_update;
import static com.platformeight.coffee.Constant.order_cancel;
import static com.platformeight.coffee.Constant.order_insert;
import static com.platformeight.coffee.Constant.orderlist_user;
import static com.platformeight.coffee.Constant.SHOPLIST_SELECT;
import static com.platformeight.coffee.Constant.shoplist_select;
import static com.platformeight.coffee.Constant.token_update;
import static com.platformeight.coffee.MyApplication.user;

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
        url = member_id_check;
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
    public int checkPassword(int no, String pass) {
        url = member_pass_check;
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("pass", pass);
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
            JSONArray index = order.getJSONArray(MEMBER_SELECT);
            JSONObject tt = index.getJSONObject(0);
            cnt = tt.getInt("cnt");
        }
        catch (JSONException e){
            Log.d(TAG, "parserCount: "+str);
        }
        return cnt;
    }

    public String login(String email,String pass) { //email, pass
        url = member_login;
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
    public String refresh(int no) { //email, pass
        url = member_refresh;
        json = new JSONObject();
        try {
            json.put("no", no);
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
        user = new MemberData();
        try{
            if (str==null) return "값없음";
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(MEMBER_SELECT);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                user.setNo(tt.getInt("no"));
                user.setName(tt.getString("name"));
                user.setPhone(tt.getString("phone"));
                user.setState(tt.getInt("state"));
                user.setPoint(tt.getInt("point"));
                user.setEmail(tt.getString("email"));
            }
            if(result.equals("")) return "값없음";
        }
        catch (JSONException e){
            Log.d(TAG, "parserUser: "+str);
        }
        return result;
    }
    public boolean register(MemberData user) {
        url = member_register;
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
        if (result.contains(REGISTER_FAILURE)){
            Log.d(TAG, "register sql query: "+result);
            return false;
        }else if (result.contains("failure")){
            Log.d(TAG, "register connection : "+result);
            return false;
        }
        return result.contains(REGISTER_SUCCESS);
    }
    public String setMember(boolean pass){
        url = member_update;
        json = new JSONObject();
        try {
            json.put("no", user.getNo());
            json.put("pass", (pass)?user.getPass():"");
            json.put("phone", user.getPhone());
            json.put("name", user.getName());
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
        if (result.contains(REGISTER_FAILURE)){
            Log.d(TAG, "register sql query: "+result);
            result="정보수정 실패";
        }else if (result.contains("failure")){
            Log.d(TAG, "register connection : "+result);
            result="서버연결 실패";
        }
        return result;
    }
    public String setToken(int no, String table, String token){
        url = token_update;
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
        if (result.contains(REGISTER_FAILURE)){
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
        url = shoplist_select;
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
        String day = getDateDay();
        try{
            if (str==null) return null;
            JSONObject order = new JSONObject(str);
            JSONArray index = order.getJSONArray(SHOPLIST_SELECT);
            Log.d(TAG, "parserShop: size "+index.length() +" data "+str);
            for (int i = 0; i < index.length(); i++) {
                JSONObject tt = index.getJSONObject(i);
                result += "nm : " + tt.getString("name")+"\n";
                Log.d(TAG, "=========================");
                Log.d(TAG, String.valueOf(tt.getInt("no")) + tt.getString("name"));
                ShopData data= new ShopData(String.valueOf(tt.getInt("no")), tt.getString("image"), tt.getString("name"), tt.getString("address"), tt.getString("phone"), tt.getString("menu"),tt.getInt("state"), tt.getString("shop_open"), tt.getString("shop_close"),tt.getString("rest_date"), tt.getDouble("mapx"), tt.getDouble("mapy"), tt.getDouble("distance"));
                /*
                // 영업시간
                LocalTime now = LocalTime.now();
                //LocalTime now = LocalTime.of(4,59);
                Log.d(TAG, "parserShop: times : " +data.getShopOpen()+"  "+now+ "   "+data.getShopClose());
                if (data.getShopOpen().isAfter(data.getShopClose())){
                    if (!data.getShopOpen().isAfter(now) && !data.getShopClose().isAfter(now))
                        data.setState(0);
                    Log.d(TAG, "parserShop: times : night"+!data.getShopOpen().isBefore(now) + !data.getShopClose().isAfter(now));
                } else if (data.getShopOpen().isBefore(now) && data.getShopClose().isAfter(now)){
                    Log.d(TAG, "parserShop: times");
                }else {
                    data.setState(0);
                    Log.d(TAG, "parserShop: times : timeout");
                }
                Log.d(TAG, "=========================");
                // 휴무일
                if (data.getRest_date().contains(day)){
                    data.setState(0);
                }
                 */
                mValues.add(data);
            }
            if(result.equals("")) return null;
        }
        catch (JSONException e){
            Log.d(TAG, "parserShop: "+str);
        }
        return mValues;
    }

    private static String getDateDay() {
        String day = "";
        Calendar cal = Calendar.getInstance();
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }
        return day;
    }

    /*
     * 주문 처리
     */
    public boolean sendOrder(JSONObject js_menu) {
        url = order_insert;
        NetworkTask networkTask = new NetworkTask(url, js_menu);
        String result = null;
        try {
            result = networkTask.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (result.contains(ORDER_INSERT_FAILURE)){
            Log.d(TAG, "order sql query: "+result);
            return false;
        }else if (result.contains("failure")){
            Log.d(TAG, "order connection : "+result);
            return false;
        }
        return result.contains(ORDER_INSERT_SUCCESS);
    }
    public boolean cancelOrder(int order_no) {
        url = order_cancel;
        json = new JSONObject();
        try {
            json.put("order_no", order_no);
            Log.d(TAG, "cancel order_no :"+ order_no);
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
        if (result.contains(ORDER_INSERT_FAILURE)){
            Log.d(TAG, "order sql query: "+result);
            return false;
        }else if (result.contains("failure")){
            Log.d(TAG, "order connection : "+result);
            return false;
        } else Log.d(TAG, "cancelOrder: "+result);
        return result.contains(ORDER_INSERT_SUCCESS);
    }

    public String getOrderList(int no, int state) {
        url = orderlist_user;
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
            return order.getJSONArray(ORDERLIST_USER).toString();
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
            JSONArray index = order.getJSONArray(ORDERLIST_USER);
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

    public boolean sendFCM(int no, String table, String testcode) {
        url = fcm_basic;
        json = new JSONObject();
        try {
            json.put("no", no);
            json.put("table", table);
            json.put("testcode", testcode);
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
}
