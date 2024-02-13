package com.navod.networing.service;

import com.navod.networing.model.AuthResponce;
import com.navod.networing.model.AuthResponse;
import com.navod.networing.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EshopService {
    @POST("auth/login")
//    @Headers({
//            "Authorization:Bearer 884|yAzg2xKM7CihdsJ4DPZB0vA4pMsAIa82N2yigSVV751b7104"
//    })
    Call<AuthResponse> auth(@Body AuthResponce authRequest);
    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id, @Header("Authorization") String token);

}
