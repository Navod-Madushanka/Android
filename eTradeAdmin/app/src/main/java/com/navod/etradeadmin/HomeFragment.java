package com.navod.etradeadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import com.navod.etradeadmin.CallBack.ModelFetchCallback;
import com.navod.etradeadmin.CallBack.ProductFetchedCallback;
import com.navod.etradeadmin.adapter.ProductViewAdapter;
import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.service.ProductService;
import com.navod.etradeadmin.util.ReloadHome;
import com.navod.etradeadmin.util.SecureApp;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private List<String> modelList;
    private static final String TAG = HomeActivity.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

//        search
        MultiAutoCompleteTextView searchTxt = fragment.findViewById(R.id.txtSearch);
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProductService service = new ProductService();
                        service.getAllProductModels(new ModelFetchCallback() {
                            @Override
                            public void onModelsFetched(List<String> models) {
                                modelList = models;
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>((HomeActivity) getActivity(),
                        android.R.layout.simple_dropdown_item_1line, modelList);
                searchTxt.setAdapter(adapter);
                searchTxt.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChang");
                setDataToRecyclerViewSearch(fragment,s.toString());
            }
        });

//        Logout
        fragment.findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecureApp.setAccess(false);
                new Handler().postDelayed(new ReloadHome((HomeActivity) getActivity()), 0);
            }
        });

        setDataToRecyclerView(fragment);

//        Add Product
        fragment.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((HomeActivity) getActivity(), ProductFormActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setDataToRecyclerViewSearch(View fragment,String model) {
        ProductService productService = new ProductService();
        RecyclerView recyclerView = fragment.findViewById(R.id.productRecyclerView);
        recyclerView.removeAllViews();
        productService.getAllProductsByModel(model, new ProductFetchedCallback() {
            @Override
            public void onProductsFetched(List<Product> productList) {
                ProductViewAdapter productViewAdapter = new ProductViewAdapter(productList, (HomeActivity) getActivity());
                recyclerView.setAdapter(productViewAdapter);
            }
        });
    }
    private void setDataToRecyclerView(View fragment) {
        ProductService productService = new ProductService();
        RecyclerView recyclerView = fragment.findViewById(R.id.productRecyclerView);

        productService.getAllProducts(new ProductFetchedCallback() {
            @Override
            public void onProductsFetched(List<Product> productList) {
                ProductViewAdapter productViewAdapter = new ProductViewAdapter(productList, (HomeActivity) getActivity());
                recyclerView.setAdapter(productViewAdapter);
            }
        });
    }
}