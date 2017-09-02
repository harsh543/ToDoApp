package com.example.harsh.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText itemName;
    Button savebtn;
    String text;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        itemName=(EditText)findViewById(R.id.etItemName);
        savebtn=(Button)findViewById(R.id.btnSave);
         pos=getIntent().getIntExtra("position",-99);
         text=getIntent().getStringExtra("Text");
       itemName.setText(text.toString());
       savebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent data=new Intent();
               text=itemName.getText().toString();
               data.putExtra("text",text);
               data.putExtra("pos",pos);
               setResult(RESULT_OK,data);
               finish();
           }
       });
       }

}
