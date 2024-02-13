package com.navod.etrade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.navod.etrade.adapter.ProductRecycleViewAdapter;
import com.navod.etrade.callback.ProductFetchedCallback;
import com.navod.etrade.entity.Product;
import com.navod.etrade.service.SearchProductService;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = SearchFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private TextView searchTxt, goBack, minPrice, maxPrice;
    private Button searchBtn, filterSearchBtn;
    private ImageView filter, sort;
    private SearchProductService searchProductService;
    private NavigationView navigationView;
    private MultiAutoCompleteTextView category, brand, model;
    private CheckBox discountCheck;
    private String categoryValue, brandValue, modelValue, minPriceValue, maxPriceValue;
    private boolean isDiscountChecked;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        searchProductService = new SearchProductService();
        initViews(fragment);
        loadAllProduct();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationView.getVisibility() == View.GONE) {
                    navigationView.setVisibility(View.VISIBLE);
                } else {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchTxt.getText().toString();
                if(!searchQuery.isEmpty()){
                    searchProductService.getProductByModelOrBrand(searchQuery, new ProductFetchedCallback() {
                        @Override
                        public void onProductFetched(List<Product> productList) {
                            setRecyclerView(productList);
                        }
                    });
                }
            }
        });
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setVisibility(View.GONE);
            }
        });

        filterSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchProductWithFilters();
            }
        });
    }
    private void searchProductWithFilters(){
        List<Pair<String,Object>> conditions = new ArrayList<>();

        categoryValue = category.getText().toString();
        brandValue = brand.getText().toString();
        modelValue = model.getText().toString();
        minPriceValue = minPrice.getText().toString();
        maxPriceValue = maxPrice.getText().toString();
        isDiscountChecked = discountCheck.isChecked();

        if (!categoryValue.isEmpty()) {
            conditions.add(Pair.of("category", categoryValue));
        }
        if (!brandValue.isEmpty()) {
            conditions.add(Pair.of("brand", brandValue));
        }
        if (!modelValue.isEmpty()) {
            conditions.add(Pair.of("model", modelValue));
        }
        if (isDiscountChecked) {
            conditions.add(Pair.of("discount", true));
        }

        searchProductService.getProductByListConditions(conditions, new ProductFetchedCallback() {
            @Override
            public void onProductFetched(List<Product> productList) {
                filterAndDisplayProducts(productList);
            }
        });
    }
    private void filterAndDisplayProducts(List<Product> productList) {
        List<Product> filteredList = new ArrayList<>(productList);

        if (!minPriceValue.isEmpty() && !maxPriceValue.isEmpty()) {
            double minPrice = Double.parseDouble(minPriceValue);
            double maxPrice = Double.parseDouble(maxPriceValue);

            filteredList = filteredList.stream()
                    .filter(product -> product.getPrice() > minPrice && product.getPrice() < maxPrice)
                    .collect(Collectors.toList());
        }

        if (isDiscountChecked) {
            filteredList = filteredList.stream()
                    .filter(product -> product.getDiscount() != 0)
                    .collect(Collectors.toList());
        }
        setRecyclerView(filteredList);
        navigationView.setVisibility(View.GONE);
    }
    private void loadAllProduct(){
        searchProductService.getProductByListConditions(Collections.emptyList(),new ProductFetchedCallback() {
            @Override
            public void onProductFetched(List<Product> productList) {
                setRecyclerView(productList);
            }
        });
    }
    private void setRecyclerView(List<Product> productList){
        recyclerView.removeAllViews();
        ProductRecycleViewAdapter productRecycleViewAdapter;
        productRecycleViewAdapter = new ProductRecycleViewAdapter((MainActivity) getActivity(), productList);
        recyclerView.setAdapter(productRecycleViewAdapter);
    }
    private void setMultiAutomaticTxtViews(){
        List<String> brands = new ArrayList<>();
        List<String> models = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        searchProductService.getProductByListConditions(Collections.emptyList(), new ProductFetchedCallback() {
            @Override
            public void onProductFetched(List<Product> productList) {
                for(Product product:productList){
                    brands.add(product.getBrand());
                    models.add(product.getModel());
                    categories.add(product.getCategory());
                }
            }
        });
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>((MainActivity) getActivity(),
                android.R.layout.simple_dropdown_item_1line, brands);
        brand.setAdapter(brandAdapter);
        brand.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>((MainActivity) getActivity(),
                android.R.layout.simple_dropdown_item_1line, models);
        model.setAdapter(modelAdapter);
        model.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>((MainActivity) getActivity(),
                android.R.layout.simple_dropdown_item_1line, categories);
        category.setAdapter(categoryAdapter);
        category.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    private void initMenuView(View fragment){
        goBack = fragment.findViewById(R.id.textView25);
        category = fragment.findViewById(R.id.multiAutoCompleteTextView);
        brand = fragment.findViewById(R.id.multiAutoCompleteTextView2);
        model = fragment.findViewById(R.id.multiAutoCompleteTextView3);
        minPrice = fragment.findViewById(R.id.editTextNumber2);
        maxPrice = fragment.findViewById(R.id.editTextNumber3);
        discountCheck = fragment.findViewById(R.id.checkBox);
        filterSearchBtn = fragment.findViewById(R.id.button9);

        setMultiAutomaticTxtViews();
    }
    private void initViews(View fragment){
        recyclerView = fragment.findViewById(R.id.recyclerViewSearch);
        searchTxt = fragment.findViewById(R.id.editTextText2);
        searchBtn = fragment.findViewById(R.id.button8);
        filter = fragment.findViewById(R.id.imageView18);
        sort = fragment.findViewById(R.id.imageView19);
        navigationView = fragment.findViewById(R.id.navigationMenu);
        initMenuView(fragment);
    }
}