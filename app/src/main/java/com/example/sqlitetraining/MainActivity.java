package com.example.sqlitetraining;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;


    Button btn_insert_data,  view_btn, updatedata_btn, btn_deleteData;
    EditText text_name,text_surname,text_marks, text_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        //Button Casting
        btn_insert_data = findViewById(R.id.btn_insert);
        view_btn = findViewById(R.id.btn_view);
        updatedata_btn = findViewById(R.id.btn_update);
        btn_deleteData =findViewById(R.id.btn_delete);

        //Edit Text
        text_name = findViewById(R.id.editTextName);
        text_surname = findViewById(R.id.editTextSurName);
        text_marks = findViewById(R.id.editMarks);
        text_ID = findViewById(R.id.editTextID);

        btn_insert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //boolean to check if data is inserted or not
               boolean isinserted =  myDb.insertData(text_name.getText().toString(), text_surname.getText().toString(), text_marks.getText().toString());

             if(isinserted == true){

                 Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();

             }
             else {

                 Toast.makeText(MainActivity.this,"Data not inserted", Toast.LENGTH_SHORT).show();

             }

            }
        });


        //For View All btn
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cur = myDb.getAllData();

                if (cur.getCount() == 0 ){

                  showMessage("Error", "NO data found");
                  return;
                }

                StringBuffer buffer = new StringBuffer();  //Cursor to show data
                while (cur.moveToNext()){

                    buffer.append("ID: " + cur.getString(0)+"\n");
                    buffer.append("Name: " + cur.getString(1)+"\n");
                    buffer.append("Surname: " + cur.getString(2)+"\n");
                    buffer.append("Marks " + cur.getString(3)+"\n");

                }

               showMessage("Data",buffer.toString());



            }
        });



// Button = Update
updatedata_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        boolean isUpdate = myDb.updateData(text_ID.getText().toString(), text_name.getText().toString(), text_marks.getText().toString(), text_ID.getText().toString());

      if(isUpdate == true){

          Toast.makeText(MainActivity.this,"Data Updated", Toast.LENGTH_SHORT).show();

      }
      else{

          Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();

        }

    }
});

// Button = Delete
btn_deleteData.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Integer deletedRow = myDb.deleteData(text_ID.getText().toString());     //myDb object used to access data

        if(deletedRow>0){

            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();

        }
        else{

            Toast.makeText(MainActivity.this, "No Data Available to delete", Toast.LENGTH_SHORT).show();


        }

    }
        });

    }


public void showMessage(String Title, String Message){

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(Title);
    builder.setMessage(Message);
    builder.show();


}









}
