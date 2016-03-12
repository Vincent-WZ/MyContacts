package com.fayimora.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        ArrayList<Contact> contacts = new ArrayList<>();

        Contact c1 = new Contact();
        c1.setName("Fayimora");
        contacts.add(c1);

        ListView listView = (ListView) findViewById(R.id.contact_list_view);
        listView.setAdapter(new ContactAdapter(contacts));
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
