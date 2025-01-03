package com.example.macskasilevente_bevasarlobeadando;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface RetrofitApiService {

    @GET("SLY1wq/products")
    Call<List<Product>> getAllProducts();

    @POST("SLY1wq/products")
    Call<Product> createProduct(@Body Product product);

    @DELETE("SLY1wq/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}
