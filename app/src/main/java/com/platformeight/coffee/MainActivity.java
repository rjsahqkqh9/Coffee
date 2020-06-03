package com.platformeight.coffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.platformeight.coffee.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{
    private ToolBarCustom toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = new ToolBarCustom(getSupportActionBar());
        toolBar.setTitleText((TextView) findViewById(R.id.toolbar_title), "ㅇㅇ동");
        toolBar.setTitleColor(Constant.colorBlack);

    }
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) { //list1 app_state=2

        //Intent intent = new Intent(this, ProjectActivity.class);
        //intent.putExtra("project_no", item.getProjectNo());
        //startActivity(intent);
        Toast.makeText(this, "event "+item.content, Toast.LENGTH_SHORT).show();
    }
}
