package com.fayimora.mycontacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
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
    public int color;
    public Contact contact;
    int contactIndex;
    FieldsAdapter fieldsAdapter;
    TextView contactNameView;

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

        contactIndex = getIntent().getIntExtra(EXTRA, 0);
        contact = ContactList.getInstance().get(contactIndex);
        contactNameView = (TextView) findViewById(R.id.contact_view_name);
        contactNameView.setText(contact.getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_view_toolbar);
        toolbar.inflateMenu(R.menu.menu_contact_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.contact_view_edit){
                    Intent i = new Intent(ContactViewActivity.this, ContactEditActivity.class);
                    i.putExtra(ContactEditActivity.EXTRA, contactIndex);
                    startActivity(i);
                }
                return id == R.id.contact_view_edit;
            }
        });

        ListView listView = (ListView) findViewById(R.id.contact_view_fields);
        fieldsAdapter = new FieldsAdapter(contact.emails, contact.phoneNumbers);
        listView.setAdapter(fieldsAdapter);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunset2);
        Palette palette;
        if (bitmap != null && !bitmap.isRecycled()) {
            palette = Palette.from(bitmap).generate();
            if(palette.getDarkVibrantSwatch() != null){
                color = palette.getDarkVibrantSwatch().getRgb();
            } else if(palette.getDarkMutedSwatch() != null){
                color = palette.getDarkMutedSwatch().getRgb();
            }
        }
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        contactNameView.setText(contact.getName());
        fieldsAdapter.notifyDataSetChanged();
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

            ImageView iv = (ImageView) convertView.findViewById(R.id.field_icon);

            if(isFirst(position)){
                if(isEmail(position))
                    iv.setImageResource(R.drawable.ic_email);
                else
                    iv.setImageResource(R.drawable.ic_call);
            }
            iv.setColorFilter(color);
            return convertView;
        }

        private boolean isFirst(int position) {
            return position == 0 || position ==phoneNumbers.size();
        }

        @Override
        // how many items should be put in the list view?
        public int getCount() {
            return emails.size() + phoneNumbers.size();
        }
    }

}
