package com.example.macskasilevente_bevasarlobeadando;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public EditText nameEditText;
    public EditText priceEditText;
    public EditText quantityEditText;
    public EditText unitEditText;
    private Button addButton;
    private Button showAddFormButton;
    private LinearLayout formLinearLayout;
    private ListView productListView;
    private List<Product> productList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        RetrofitApiService apiService = RetrofitClient.getInstance().create(RetrofitApiService.class);

        loadProducts(apiService);

        showAddFormButton.setOnClickListener(view -> {
            formLinearLayout.setVisibility(View.VISIBLE);
            showAddFormButton.setVisibility(View.GONE);
        });

        addButton.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String price = priceEditText.getText().toString();
            String quantity = quantityEditText.getText().toString();
            String unit = unitEditText.getText().toString();

            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || unit.isEmpty()) {
                Toast.makeText(MainActivity.this, "Minden mezőt ki kell tölteni!", Toast.LENGTH_SHORT).show();
            } else {
                Product product = new Product(name, Integer.parseInt(price), Double.parseDouble(quantity), unit);
                addProduct(apiService, product);
            }
        });
    }

    public void init() {
        nameEditText = findViewById(R.id.nameEditText);
        priceEditText = findViewById(R.id.priceEditText);
        quantityEditText = findViewById(R.id.quantityEditText);
        unitEditText = findViewById(R.id.unitEditText);
        formLinearLayout = findViewById(R.id.formLinearLayout);
        addButton = findViewById(R.id.addButton);
        showAddFormButton = findViewById(R.id.showAddFormButton);
        productListView = findViewById(R.id.productListView);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this);
        productListView.setAdapter(productAdapter);
    }

    public void loadProducts(RetrofitApiService apiService) {
        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "A termékek betöltése sikertelen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hiba a termékek betöltésében", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addProduct(RetrofitApiService apiService, Product product) {
        apiService.createProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList.add(response.body());
                    productAdapter.notifyDataSetChanged();
                    formLinearLayout.setVisibility(View.GONE);
                    showAddFormButton.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Termék hozzáadva", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Hiba történt a termék hozzáadásakor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hiba történt a termék hozzáadásakor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
