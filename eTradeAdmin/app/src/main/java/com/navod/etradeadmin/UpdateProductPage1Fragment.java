package com.navod.etradeadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.listener.OnProductUpdateListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProductPage1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProductPage1Fragment extends Fragment {
    private static final String TAG = UpdateProductPage1Fragment.class.getName();

    private TextView description, price, discount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProductPage1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProductPage1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProductPage1Fragment newInstance(String param1, String param2) {
        UpdateProductPage1Fragment fragment = new UpdateProductPage1Fragment();
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
        return inflater.inflate(R.layout.fragment_update_product_page1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        UpdateProductActivity activity = (UpdateProductActivity) getActivity();
        initFields(fragment);
        setData(activity.product);
        fragment.findViewById(R.id.textView30).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataToProduct(activity.product);
                navigateToNextPage(activity);
            }
        });
    }
    private void navigateToNextPage(UpdateProductActivity activity){
        activity.viewPager.setCurrentItem(1);
    }
    private void setDataToProduct(Product product){
        product.setDescription(description.getText().toString());
        product.setPrice(Double.parseDouble(price.getText().toString()));
        product.setPrice(Double.parseDouble(discount.getText().toString()));

        if(getActivity() instanceof OnProductUpdateListener){
            ((OnProductUpdateListener) getActivity()).onProductUpdated(product);
        }
    }
    private void setData(Product product){
        description.setText(product.getDescription());
        price.setText(String.valueOf(product.getPrice()));
        discount.setText(String.valueOf(product.getDiscount()));
    }

    private void initFields(View fragment){
        description = fragment.findViewById(R.id.editTextTextMultiLine);
        price = fragment.findViewById(R.id.editTextNumberDecimal);
        discount = fragment.findViewById(R.id.editTextNumberDecimal2);
    }
}