package com.fayimora.mycontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactEditActivity extends AppCompatActivity {
    public static String EXTRA = "CEA_Contact";

    public Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);
        int position = getIntent().getIntExtra(EXTRA, 0);
        contact = ContactList.getInstance().get(position);

        Toolbar toolbar = (Toolbar)findViewById(R.id.contact_edit_toolbar);
        toolbar.setTitle(getResources().getString(R.string.edit_contact));
        toolbar.setNavigationIcon(R.drawable.ic_done);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editName = (EditText) findViewById(R.id.contact_edit_name);
                contact.setName(editName.getText().toString());

                contact.phoneNumbers = getSectionValues(R.id.phone_number_section);
                contact.emails = getSectionValues(R.id.email_section);

                Toast.makeText(ContactEditActivity.this, "Saved contact", Toast.LENGTH_LONG).show();

                finish();

            }
        });

        EditText editName = (EditText) findViewById(R.id.contact_edit_name);
        editName.setText(contact.getName());

        addToSection(R.id.phone_number_section, contact.phoneNumbers);
        addToSection(R.id.email_section, contact.emails);

        TextView addNewPhoneNumber = (TextView)findViewById(R.id.add_new_phone_number);
        addNewPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.phone_number_section, "");
            }
        });

        TextView addEmail = (TextView)findViewById(R.id.add_new_email);
        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToSection(R.id.email_section, "");
            }
        });
    }

    private ArrayList<String> getSectionValues(int sectionID){
        ArrayList<String> values = new ArrayList<>();
        LinearLayout section = (LinearLayout) findViewById(sectionID);
        for (int i = 0; i < section.getChildCount(); i++) {
            EditText editNumber = (EditText)section.getChildAt(i);
            values.add(editNumber.getText().toString());
        }
        return values;
    }

    private void addToSection(int sectionID, String value){
        LinearLayout section = (LinearLayout)findViewById(sectionID);
        EditText et = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        et.setLayoutParams(lp);
        et.setText(value);
        section.addView(et);
    }

    private void addToSection(int sectionID, ArrayList<String> values) {
        LinearLayout section = (LinearLayout)findViewById(sectionID);
        for (int i = 0; i < values.size(); i++) {
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(lp);
            et.setText(values.get(i));
            section.addView(et);
        }
    }
}
