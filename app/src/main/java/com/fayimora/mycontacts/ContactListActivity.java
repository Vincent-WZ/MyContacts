package com.fayimora.mycontacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contacts = new ArrayList<>();

        for (int i=0; i<10; i++){
            Contact c1 = new Contact();
            c1.setName("Fayimora "+i);

            c1.emails = new ArrayList<>();
            c1.phoneNumbers = new ArrayList<>();

            c1.emails.add("fayi@a.com");
            c1.emails.add("fayi@b.com");

            c1.phoneNumbers.add("07555551111");
            c1.phoneNumbers.add("08555551111");

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
                if (firstVisibleItem > previousFirstIem) {
                    getSupportActionBar().hide();
                } else if (firstVisibleItem < previousFirstIem) {
                    getSupportActionBar().show();
                }
                previousFirstIem = firstVisibleItem;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact c = contacts.get(position);
                Intent i = new Intent(ContactListActivity.this, ContactViewActivity.class);
                i.putExtra(ContactViewActivity.EXTRA, c);
                startActivity(i);
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
