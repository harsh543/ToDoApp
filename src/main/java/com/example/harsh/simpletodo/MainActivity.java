package com.example.harsh.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private final  int requestCode =1;
     @Override
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         lvItems =(ListView)findViewById(R.id.lvItems);
         items=new ArrayList<>();
         readItems();
         itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
         lvItems.setAdapter(itemsAdapter);
         items.add("First item");
         items.add("Second item");
         setupListViewListener();
     }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK ) {
            // Extract name value from result extras
            String name = data.getExtras().getString("text");
            int position = data.getExtras().getInt("pos", 0);
            // Toast the name to display temporarily on screen
          Log.e("mESSAGE FROM",name);
            items.set(position,name);
            lvItems.invalidateViews();
            itemsAdapter.notifyDataSetChanged();
            writeItems();

         }
    }
    public void onAddItem(View v){
         EditText etNewItem=(EditText) findViewById(R.id.etNewItem);
         String itemText=etNewItem.getText().toString();
         itemsAdapter.add(itemText);
         etNewItem.setText("");
         writeItems();
     }

     private void setupListViewListener(){
         lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                 items.remove(pos);
                 itemsAdapter.notifyDataSetChanged();
                 writeItems();
                 return true;
             }
         });
         lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                 Intent i=new Intent(MainActivity.this,EditItemActivity.class);
                 i.putExtra("Text",adapterView.getItemAtPosition(pos).toString());
                 i.putExtra("position",pos);
                 startActivityForResult(i, requestCode);
             }
         });
     }
     private void readItems(){
         File filesdirDir=getFilesDir();
         File todoFile=new File(filesdirDir,"todo.txt");
         try{
             items=new ArrayList<String>(FileUtils.readLines(todoFile));
         }
         catch (IOException e){
             e.printStackTrace();
         }
     }
    private void writeItems(){
        File filesdirDir=getFilesDir();
        File todoFile=new File(filesdirDir,"todo.txt");
        try{
           FileUtils.writeLines(todoFile,items);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
