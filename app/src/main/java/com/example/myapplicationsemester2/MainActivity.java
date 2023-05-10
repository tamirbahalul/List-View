package com.example.myapplicationsemester2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listViewData;
    ArrayAdapter<Product> adapter;
    ArrayList arrayContent = new ArrayList<Product>();
    //String arrayContent[] = {"Android", "React", "native", "Java"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new CustomAdapter(getBaseContext(),arrayContent);
        listViewData = findViewById(R.id.listviewData);

        readItems();

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,arrayContent);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.item_done){
            String itemSelected = "Selected Items: \n";
            for(int i=0; i < listViewData.getCount();i++){
                try {
                    CheckBox cb = listViewData.getChildAt(i).findViewById(R.id.checkBox);
                    if(cb.isChecked())
                    {
                        String temp = ((Product)listViewData.getItemAtPosition(i)).getName();
                        itemSelected += temp+" ";
                    }
                }catch (Exception e) {

                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        if(item.getItemId() == R.id.item_delete) {
            for(int i=0;i<listViewData.getCount();i++) {
                try {
                    CheckBox cb = listViewData.getChildAt(i).findViewById(R.id.checkBox);
                    if (cb.isChecked()) {
                        myRef.child(((Product)listViewData.getItemAtPosition(i)).getName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "Item/s removed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }catch(Exception e) {

                }
                }
            }
        return super.onOptionsItemSelected(item);
    }

    public void addItem(View view)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        EditText et_item = findViewById(R.id.et_item);
        String item = et_item.getText().toString().trim();
        EditText et_price = findViewById(R.id.et_price);
        double price = Double.parseDouble(et_price.getText().toString().trim());
        EditText et_Qty = findViewById(R.id.et_Qty);
        int qty = Integer.parseInt(et_Qty.getText().toString().trim());
        EditText et_info = findViewById(R.id.et_info);
        String info = et_info.getText().toString().trim();
        Product p = new Product(item,price,info,qty);
        if(item.equals("")){
            Toast.makeText(this, "please enter a item", Toast.LENGTH_SHORT).show();
            return;
        }
        myRef.child(item).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    et_item.setText("");
                    et_price.setText("");
                    et_Qty.setText("");
                    et_info.setText("");
                }
            }
        });
    }

    public void readItems()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayContent.clear();
                for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                    Product temp = new Product(
                            itemSnapShot.getValue(Product.class).getName(),
                            itemSnapShot.getValue(Product.class).getPrice(),
                            itemSnapShot.getValue(Product.class).getInfo(),
                            itemSnapShot.getValue(Product.class).getQty()
                    );
                    arrayContent.add(temp);
                }

                listViewData.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("cancel");
            }
        });
    }

    public void updateProduct(View view)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        EditText et_price = findViewById(R.id.et_price);
        if(et_price.getText().toString().equals("")) {
            Toast.makeText(this, "Must enter price", Toast.LENGTH_SHORT).show();
            return;
        }
        double price = Double.parseDouble(et_price.getText().toString());


        EditText et_qty = findViewById(R.id.et_Qty);
        if(et_qty.getText().toString().equals("")) {
            Toast.makeText(this, "Must enter quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        int qty = Integer.parseInt(et_qty.getText().toString());

        HashMap update = new HashMap();
        update.put("price",price);
        update.put("qty",qty);

        EditText et_name = findViewById(R.id.et_item);
        if(et_name.getText().toString().equals("")) {
            Toast.makeText(this, "Must enter item name", Toast.LENGTH_SHORT).show();
            return;
        }

        myRef.child(et_name.getText().toString()).updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Update Done", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}