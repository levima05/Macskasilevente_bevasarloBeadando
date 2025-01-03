package com.example.macskasilevente_bevasarlobeadando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.product_list_item, viewGroup, false);

        TextView nameTextView = view.findViewById(R.id.productNameTextView);
        TextView priceTextView = view.findViewById(R.id.productPriceTextView);
        TextView quantityTextView = view.findViewById(R.id.productQuantityTextView);
        TextView unitTextView = view.findViewById(R.id.productUnitTextView);

        Product product = productList.get(i);
        nameTextView.setText(product.getName());
        priceTextView.setText(String.valueOf(product.getPrice()) + " Ft");
        quantityTextView.setText(String.valueOf(product.getQuantity()));
        unitTextView.setText(product.getUnit());

        return view;
    }
}
