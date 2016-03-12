package com.fayimora.mycontacts;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

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
        headerSection.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * (9.0 / 16.0))));

        Contact c = (Contact) getIntent().getSerializableExtra(EXTRA);
        TextView tv = (TextView) findViewById(R.id.contact_view_name);
        tv.setText(c.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                return id == R.id.contact_view_edit;
            }
        });

        ListView listView = (ListView) findViewById(R.id.contact_view_fields);
        listView.setAdapter(new FieldsAdapter(c.emails, c.phoneNumbers));
    }

    private class FieldsAdapter extends BaseAdapter {

        ArrayList<String> emails;
        ArrayList<String> phoneNumbers;

        protected FieldsAdapter(ArrayList<String> emails, ArrayList<String> phoneNumbers) {
            this.emails = emails;
            this.phoneNumbers = phoneNumbers;
        }
        @Override
        public Object getItem(int position) {
            if(isEmail(position)){
                return emails.get(position-phoneNumbers.size());
            }else {
                return phoneNumbers.get(position);
            }
        }

        private boolean isEmail(int position){
            return position > phoneNumbers.size()-1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = ContactViewActivity.this.getLayoutInflater().inflate(R.layout.contact_view_field_row, parent, false);
            }

            String value = (String) getItem(position);
            TextView fieldValue = (TextView) convertView.findViewById(R.id.contact_view_row_value);
            fieldValue.setText(value);
            return convertView;
        }

        @Override
        // how many items should be put in the list view?
        public int getCount() {
            return emails.size() + phoneNumbers.size();
        }
    }

}
