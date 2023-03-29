package com.pedrycz.phonedb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pedrycz.phonedb.entities.Phone;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PhoneViewModel phoneViewModel;
    private PhoneAdapter adapter;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent addActivity = new Intent(MainActivity.this, AddActivity.class);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new PhoneAdapter(this);
        adapter.setOnItemClickListener(position -> {
                    Intent updateActivity = new Intent(MainActivity.this, AddActivity.class);
                    Phone phone = adapter.getPhones().get(position);
                    updateActivity.putExtra("ID", phone.getId());
                    updateActivity.putExtra("BRAND", phone.getBrand());
                    updateActivity.putExtra("MODEL", phone.getModel());
                    updateActivity.putExtra("VERSION", phone.getAndroid());
                    updateActivity.putExtra("URL", phone.getUrl());
                    addUpdateResultLauncher.launch(updateActivity);
                }
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        phoneViewModel.getAllPhones().observe(this, phoneListUpdateObserver);
        Executors.newSingleThreadExecutor().execute(() -> {
            if (phoneViewModel.getDataCount() == 0) {
                List<Phone> phones = List.of(
                        new Phone("Samsung", "Galaxy S23 Ultra", "13", "https://www.samsung.com/pl/smartphones/galaxy-s23-ultra/buy/"),
                        new Phone("Huawei", "Mate 20 lite", "10", "https://www.mgsm.pl/pl/katalog/huawei/mate20lite/"),
                        new Phone("Nokia", "C12 PRO", "12 Go", "https://www.mgsm.pl/pl/katalog/nokia/c12pro/"),
                        new Phone("Sony", "Xperia J", "4.0 Ice Cream Sandwich", "https://www.mgsm.pl/pl/katalog/sony/xperiaj/"),
                        new Phone("OnePlus", "Nord 2T 5G", "12", "https://www.gadgets360.com/oneplus-nord-2t-5g-price-in-india-106885")
                );
                for (Phone p : phones) {
                    phoneViewModel.addPhone(p);
                }
            }
        });
        FloatingActionButton addPhoneButton = findViewById(R.id.addButton);
        addPhoneButton.setOnClickListener(view ->
                addActivityResultLauncher.launch(addActivity));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Executors.newSingleThreadExecutor().execute(() -> phoneViewModel.deletePhone(adapter.getPhones().get(viewHolder.getAdapterPosition())));
                Toast toast = Toast.makeText(getApplicationContext(), "Phone deleted", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    ActivityResultLauncher<Intent> addUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast toast = Toast.makeText(getApplicationContext(), "CANCELED", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bundle bundle = data.getExtras();
                        Executors.newSingleThreadExecutor().execute(() -> phoneViewModel.updatePhone(bundle.getLong("ID"),
                                bundle.getString("BRAND"),
                                bundle.getString("MODEL"),
                                bundle.getString("VERSION"),
                                bundle.getString("URL")));
                    }
                }
            });

    ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast toast = Toast.makeText(getApplicationContext(), "CANCELED", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bundle bundle = data.getExtras();
                        Phone newPhone = new Phone(
                                bundle.getString("BRAND"),
                                bundle.getString("MODEL"),
                                bundle.getString("VERSION"),
                                bundle.getString("URL")
                        );
                        Executors.newSingleThreadExecutor().execute(() -> phoneViewModel.addPhone(newPhone));

                    }
                }
            });
    Observer<List<Phone>> phoneListUpdateObserver = new Observer<>() {
        @Override
        public void onChanged(List<Phone> phones) {
            adapter.setPhones(phones);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clearRecords) {
            phoneViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
}