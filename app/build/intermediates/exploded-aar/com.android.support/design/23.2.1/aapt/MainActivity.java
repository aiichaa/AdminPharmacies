package com.example.info.adminpharmacies;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editLatLng,editAllNight,editId;
    Button btnAddData ,btnViewAll,btnUpdate,btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //call db
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editName);
        editLatLng = (EditText) findViewById(R.id.editLatLng);
        editAllNight = (EditText) findViewById(R.id.editAllNight);
        editId = (EditText) findViewById(R.id.editId);
        btnAddData = (Button) findViewById(R.id.button);
        btnViewAll = (Button) findViewById(R.id.button2);
        btnUpdate = (Button) findViewById(R.id.button3);
        btnDelete = (Button) findViewById(R.id.button4);



        addData();
        viewAll();
        UpdateData();
        DeleteDate();


    }

    public void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editLatLng.getText().toString(),
                        editAllNight.getText().toString());

                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

                }

                editName.setText("");
                editLatLng.setText("");
                editAllNight.setText("");
            }
        });
    }

    public void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor res = myDb.getAllDate();
                if (res.getColumnCount() == 0) {
                    //show message
                    showMessage("Error", "No Data Found :/");
                    return;
                }
                StringBuffer buffer = new StringBuffer();

                while (res.moveToNext()) {
                    buffer.append("ID : " + res.getString(0) + "\t");
                    buffer.append("Name : " + res.getString(1) + "\t");
                    buffer.append("LatLng : " + res.getString(2) + "\t");
                    buffer.append("allNight : " + res.getString(3) + "\n\n");
                }
                //show all date
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void UpdateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateDate(editId.getText().toString(), editName.getText().toString(), editLatLng.getText().toString(), editAllNight.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public void DeleteDate(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteDate(editId.getText().toString());

                if(deleteRows > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
