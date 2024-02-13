package com.navod.networing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.navod.networing.interceptor.RequestInterceptor;
import com.navod.networing.model.AuthResponce;
import com.navod.networing.model.AuthResponse;
import com.navod.networing.model.Product;
import com.navod.networing.model.Repo;
import com.navod.networing.service.EshopService;
import com.navod.networing.service.GitHubService;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callAPI();
    }
    private void callAPI(){
        String token = getPreferences(MODE_PRIVATE).getString("token", "");
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor(token)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jiat.online/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        EshopService eshopService = retrofit.create(EshopService.class);
        Call<AuthResponse> auth = eshopService.auth(new AuthResponce("admin@eshop.com", "password"));
        auth.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                Log.i(TAG, "Token: "+response.body().getToken());
                if(response.isSuccessful()){
                    getPreferences(MODE_PRIVATE).edit()
                            .putString("token", response.body().getToken()).commit();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG,"error:"+t.getMessage());
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String token = getPreferences(MODE_PRIVATE).getString("token", "");
//                String accessToken = "Bearer "+token;
                Call<List<Product>> productsCall = eshopService.getAllProducts();
                productsCall.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if(response.isSuccessful()){
                            List<Product> products = response.body();
                            products.forEach(product -> {
                                Log.i(TAG,"Product:"+product.getTitle());
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG,"error:"+t.getMessage());
                    }
                });
            }
        });
    }
    private void gitHubCalling(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<Repo>> request = service.listRepos("octocat");
        request.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> list = response.body();
                list.forEach(r->{
                    Log.i(TAG, r.getFullName());
                });
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }
}