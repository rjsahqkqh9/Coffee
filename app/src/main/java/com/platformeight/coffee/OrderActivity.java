package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private Button add_Button;
    private Button minus_Button;
    private Button cart_Button;
    private Button pay_Button;

    private TextView countText;
    private TextView price_Shot;
    private TextView hot_Price, ice_Price;

    private CheckBox add_Shot;

    private RadioButton hot, ice;
    private RadioGroup radio_Group;

    private int count;
    private int price;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        price = 0;

        /*수량 증감소 시작*/
        add_Button = (Button)findViewById(R.id.button4);
        minus_Button = (Button)findViewById(R.id.button3);
        countText = (TextView)findViewById(R.id.textView4);
        count = 1;/*음료 기본수량*/

        countText.setText("" + count);

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countText.setText("" + count++);
            }
        });

        minus_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 1){
                    countText.setText("" + count--);
                }
            }
        });
        /*수량 증감소 끝*/

        /*샷추가 시작*/
        price_Shot = (TextView)findViewById(R.id.textView7);
        add_Shot = (CheckBox)findViewById(R.id.checkBox);
        add_Shot.setChecked(false);

        String text_Shotprice = price_Shot.toString();

        if(add_Shot.isChecked()){
            price = price + Integer.parseInt(text_Shotprice.substring(0, text_Shotprice.length() - 1));
        }else{
            price = price - Integer.parseInt(text_Shotprice.substring(0, text_Shotprice.length() - 1));
        }
        /*샷추가 끝*/

        /*음료 Hot Ice 선택 시작*/
        radio_Group = (RadioGroup)findViewById(R.id.radioGroup);

        hot_Price = (TextView)findViewById(R.id.textView8);
        ice_Price = (TextView)findViewById(R.id.textView9);

        radio_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioButton){
                    price = Integer.parseInt(hot_Price.toString().substring(0, hot_Price.toString().length() - 1));
                }else if(i == R.id.radioButton2){
                    price = Integer.parseInt(ice_Price.toString().substring(0, ice_Price.toString().length() - 1));
                }
            }
        });
        /*음료 Hot Ice 선택 끝*/

        /*장바구니 버튼 시작*/
        cart_Button = (Button)findViewById(R.id.button7);

        cart_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*장바구니 버튼 끝*/

        /*결제 버튼 시작*/
        pay_Button = (Button)findViewById(R.id.button8);

        pay_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*결제 버튼 끝*/
    }
}