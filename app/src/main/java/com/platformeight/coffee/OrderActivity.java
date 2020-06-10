package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import static com.platformeight.coffee.Constant.cart;
import static com.platformeight.coffee.Constant.cart_code;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    private static final DecimalFormat format = new DecimalFormat("###,###");

    private Button add_Button;
    private Button minus_Button;
    private Button cart_Button;
    private Button pay_Button;

    private TextView menuName;
    private TextView countText;
    private TextView price_Shot;

    private CheckBox add_Shot;

    private RadioButton hot, ice;
    private RadioGroup radio_Group;
    private LinearLayout radio_price;
    private LinearLayout check_group;
    private LinearLayout check_price;


    private int count = 1;/*음료 기본수량*/
    private int price = 0;
    private int base_num = 0;
    private int opt_num = 0;

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
        try {
            menu = new JSONObject(Objects.requireNonNull(getIntent().getStringExtra("menu")));
            menuName.setText(menu.getString("name"));
            base_num = menu.getInt("bnum");
            opt_num = menu.getInt("onum");

            JSONArray ja = new JSONArray(menu.getString("base"));
            base = ja.getJSONObject(0);
            //Log.d(TAG, "initialData: "+ base.names().getString(0));
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
                radio_Group.addView(rb);
            }
            radio_Group.check((int) 1);

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

        menuName = findViewById(R.id.order_menu_name);
        /*수량 증감소 시작*/
        add_Button = (Button)findViewById(R.id.button4);
        minus_Button = (Button)findViewById(R.id.button3);
        countText = (TextView)findViewById(R.id.textView4);


        countText.setText(String.valueOf(count));

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countText.setText("" + ++count);
            }
        });


        minus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 1){
                    countText.setText("" + --count);
                }
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
        cart_Button = (Button)findViewById(R.id.button7);
        cart_Button.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //장바구니 상품정보 전송후 뒤로가기
                Log.d(TAG, "cart_Button: " +store_cart());
                intent(false);
            }
        });
        /*장바구니 버튼 끝*/

        /*결제 버튼 시작*/
        pay_Button = (Button)findViewById(R.id.button8);
        pay_Button.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                //장바구니로
                //if (!radio_Group.isSelected()) return;
                Log.d(TAG, "pay_Button: " +store_cart());
                intent(true);
            }
        });
        /*결제 버튼 끝*/
    }
    private String store_cart(){  //TODO:카트리스트에 현재 주문 추가
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
    private void intent(boolean run_cart){
        Intent result = new Intent();
        result.putExtra(cart,store_cart());
        result.putExtra(cart_code,run_cart);
        setResult(RESULT_OK,result);
        finish();
    }
}