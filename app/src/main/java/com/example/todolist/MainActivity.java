package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        EditText task;
        Button add;
        ListView listView;
        ArrayList<String> itemList = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = findViewById(R.id.taskToDo);
        add = findViewById(R.id.Add);
        listView = findViewById(R.id.list_item);

        itemList = FileHelper.readData(this);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,itemList);
        listView.setAdapter(arrayAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ItemName =  task.getText().toString();
                itemList.add(ItemName);
                task.setText("");
                FileHelper.writeData(itemList, getApplicationContext());
                arrayAdapter.notifyDataSetChanged();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("delete task");
                        alert.setMessage("Are you sure want to delete ?");
                        alert.setCancelable(false);
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                itemList.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                FileHelper.writeData(itemList, getApplicationContext());
                            }
                        });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });


    }
}