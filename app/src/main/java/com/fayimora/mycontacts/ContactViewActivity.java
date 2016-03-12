package com.fayimora.mycontacts;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactViewActivity extends AppCompatActivity {
    public static String EXTRA = "CVA_Contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int width = point.x;

        RelativeLayout headerSection = (RelativeLayout) findViewById(R.id.contact_view_header);
        headerSection.setLayoutParams(new RelativeLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));

        Contact c = (Contact) getIntent().getSerializableExtra(EXTRA);
        TextView tv = (TextView) findViewById(R.id.contact_view_name);
        tv.setText(c.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
//        setSupportActionBar(toolbar);
    }

}
