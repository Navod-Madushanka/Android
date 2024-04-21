package com.navod.etrade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.navod.etrade.adapter.ProductRecycleViewAdapter;
import com.navod.etrade.callback.ProductFetchedCallback;
import com.navod.etrade.entity.Product;
import com.navod.etrade.service.ProductService;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllProducts extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProductService productService;

    public AllProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static AllProducts newInstance(String param1, String param2) {
        AllProducts fragment = new AllProducts();
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
        return inflater.inflate(R.layout.fragment_all_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        setDataToRecyclerView(fragment);
    }
    private void setDataToRecyclerView(View fragment){
        productService = new ProductService();
        RecyclerView recyclerView = fragment.findViewById(R.id.recyclerView2);
        productService.getAllProduct(Collections.emptyList(),new ProductFetchedCallback() {
            @Override
            public void onProductFetched(List<Product> productList) {
                ProductRecycleViewAdapter productRecycleViewAdapter;
                productRecycleViewAdapter = new ProductRecycleViewAdapter((MainActivity) getActivity(), productList);
                recyclerView.setAdapter(productRecycleViewAdapter);
            }
        });
    }
}