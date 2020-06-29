package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Objects;

import static com.platformeight.coffee.Constant.CART_CODE;
import static com.platformeight.coffee.Constant.CART_ITEMS;
import static com.platformeight.coffee.Constant.SHOP_NAME;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    private static final DecimalFormat format = new DecimalFormat("###,###");

    private Button plus_Button;
    private Button minus_Button;
    private Button cart_Button;
    private Button pay_Button;

    private TextView title;
    private TextView menuName;
    private TextView countText;

    private RadioGroup radio_Group;
    private LinearLayout radio_price;
    private LinearLayout check_group;
    private LinearLayout check_price;

    //private List<String> radio_id;


    private int count = 1;/*음료 기본수량*/
    private int base_num = 0;
    private int opt_num = 0;

    private JSONArray cart_list;
    private JSONObject menu;
    private JSONObject base;
    private JSONObject opt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initialView();
        initialData();
    }

    private void initialData() {
        title.setText(getIntent().getStringExtra(SHOP_NAME));
        //radio_id=new ArrayList<String>();
        try {
            menu = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra(Constant.MENU)));
            cart_list = new JSONArray(Objects.requireNonNull(getIntent().getStringExtra(CART_ITEMS)));

            menuName.setText(menu.getString("name"));
            base_num = menu.getInt("bnum");
            opt_num = menu.getInt("onum");

            JSONArray ja = new JSONArray(menu.getString("base"));
            base = ja.getJSONObject(0);
            //Log.d(TAG, "initialData: "+ base.names().getString(0));
            int index=0;
            for(Iterator<String> itr = base.keys(); itr.hasNext();){
                String str = itr.next();
                TextView tv = new TextView(this);
                tv.setText(String.format("- "+str+" : %s원", format.format(base.getInt(str))));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setGravity(Gravity.CENTER);
                lp.gravity=Gravity.END;
                lp.weight=1;
                tv.setLayoutParams(lp);
                //tv.setTag(str);
                radio_price.addView(tv);

                RadioButton rb = new RadioButton(this);
                rb.setText(str);
                rb.setId(index);
                Log.d(TAG, "initialData: "+rb.getId());
                //radio_id.add(String.valueOf(rb.getId()));
                radio_Group.addView(rb);
                index++;
            }
            radio_Group.check(radio_Group.getChildAt(0).getId());
            Log.d(TAG, "initialData: "+radio_Group.getChildAt(0).getId());
            //Log.d(TAG, "initialData: "+radio_id.get(0));

            ja = new JSONArray(menu.getString("opt"));
            opt = ja.getJSONObject(0);
            for(Iterator<String> itr = opt.keys(); itr.hasNext();){
                String str = itr.next();
                TextView tv = new TextView(this);
                tv.setText(String.format("- "+str+" : +%s원", format.format(opt.getInt(str))));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setGravity(Gravity.CENTER);
                lp.gravity=Gravity.END;
                lp.weight=1;
                tv.setLayoutParams(lp);
                //tv.setTag(str);
                check_price.addView(tv);

                CheckBox cb = new CheckBox(this);
                cb.setText(str);
                check_group.addView(cb);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initialView() {
        title = findViewById(R.id.shop_title);
        menuName = findViewById(R.id.order_menu_name);
        /*수량 증감소 시작*/
        plus_Button = (Button)findViewById(R.id.order_btn_plus);
        minus_Button = (Button)findViewById(R.id.order_btn_minus);
        countText = (TextView)findViewById(R.id.order_count);


        countText.setText(String.valueOf(count));

        plus_Button.setOnClickListener(view -> countText.setText("" + ++count));

        minus_Button.setOnClickListener(view -> {
            if(count > 1){
                countText.setText("" + --count);
            }
        });
        /*수량 증감소 끝*/

        /*음료 Hot Ice 선택 시작*/
        radio_Group = (RadioGroup)findViewById(R.id.radioGroup);
        radio_price = findViewById(R.id.order_radio_price);
        /*음료 Hot Ice 선택 끝*/
        /*옵션추가 시작*/
        check_group = findViewById(R.id.order_check_group);
        check_price = findViewById(R.id.order_check_price);
        /*옵션추가 끝*/

        /*장바구니 버튼 시작*/
        cart_Button = (Button)findViewById(R.id.order_btn_cart);
        cart_Button.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //장바구니 상품정보 전송후 뒤로가기
                //Log.d(TAG, "cart_Button: " +store_cart());
                sendCart(false);
            }
        });
        /*장바구니 버튼 끝*/

        /*결제 버튼 시작*/
        pay_Button = (Button)findViewById(R.id.order_btn_pay);
        pay_Button.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //장바구니로
                //if (!radio_Group.isSelected()) return;
                //Log.d(TAG, "pay_Button: " +store_cart());
                sendCart(true);
            }
        });
        /*결제 버튼 끝*/
    }
    private String store_cart(){
        try {
            JSONObject js_cart = new JSONObject();
            js_cart.put("no",cart_list.length()+1);
            js_cart.put("name",(String) menuName.getText());
            js_cart.put("onum",0);
            js_cart.put("amount",count);
            js_cart.put("price",0);
            int i = radio_Group.getCheckedRadioButtonId();
            //i = radio_id.indexOf(String.valueOf(radio_Group.getCheckedRadioButtonId()));
            String str = base.names().getString(i);
            js_cart.put("base",str);
            js_cart.put( str,base.getInt(str));
            JSONArray ja = new JSONArray();
            JSONObject js1 = new JSONObject();
            int onum=0;
            int price=base.getInt(str);
            for(i=1;i<=opt_num;i++){
                CheckBox box = (CheckBox) check_group.getChildAt(i);
                if (box.isChecked()) {
                    onum++;
                    str = opt.names().getString(i-1);
                    js1.put(str,opt.getInt(str));
                    price+=opt.getInt(str);
                }
            }
            js_cart.put("onum",onum);
            js_cart.put("price",price);
            //js1.put("샷추가",500);
            //js1.put("휘핑크림",300);
            ja.put(js1);
            js_cart.put("opt",ja);
            cart_list.put(js_cart);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cart_list.toString();
    }
    private void sendCart(boolean run_cart){
        Intent result = new Intent();
        String str = store_cart();
        Log.d(TAG, run_cart+" cart_Button: " + str);
        result.putExtra(CART_ITEMS,str);
        result.putExtra(CART_CODE,run_cart);
        setResult(RESULT_OK,result);
        finish();
    }

    private String store_cart_sample(){
        String result = "";
        try {
            result = (String) menuName.getText();
            int i = radio_Group.getCheckedRadioButtonId();
            result = result + " base: "+ base.names().getString(i-1);

            for(i=1;i<=opt_num;i++){
                CheckBox box = (CheckBox) check_group.getChildAt(i);
                if (box.isChecked()) {
                    result= result +" opt "+box.getText();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        result+= " 수량: "+count;
        return result;
    }
}