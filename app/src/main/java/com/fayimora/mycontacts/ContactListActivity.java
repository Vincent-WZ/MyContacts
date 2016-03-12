package com.fayimora.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<Contact> contacts = new ArrayList<>();

        for (int i=0; i<30; i++){
            Contact c1 = new Contact();
            c1.setName("Fayimora");
            contacts.add(c1);
        }

        ListView listView = (ListView) findViewById(R.id.contact_list_view);
        listView.setAdapter(new ContactAdapter(contacts));
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int previousFirstIem = 0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem > previousFirstIem){
                    getSupportActionBar().hide();
                } else if(firstVisibleItem < previousFirstIem) {
                    getSupportActionBar().show();
                }
                previousFirstIem = firstVisibleItem;
            }
        });
    }

    private class ContactAdapter extends ArrayAdapter<Contact> {
        public ContactAdapter(ArrayList<Contact> contacts){
            super(ContactListActivity.this, R.layout.contact_list_row, R.id.contact_row_name, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.contact_row_name);
            Contact c = getItem(position);
            nameTextView.setText(c.getName());
            return convertView;
        }
    }
}
