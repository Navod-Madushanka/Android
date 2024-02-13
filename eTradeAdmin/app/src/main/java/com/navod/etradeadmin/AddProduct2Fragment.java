package com.navod.etradeadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProduct2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProduct2Fragment extends Fragment {
    private TextView price, discount, quantity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProduct2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProduct2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProduct2Fragment newInstance(String param1, String param2) {
        AddProduct2Fragment fragment = new AddProduct2Fragment();
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
        return inflater.inflate(R.layout.fragment_add_product2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        fragment.findViewById(R.id.txtNextFragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFormActivity formActivity = (ProductFormActivity) getActivity();


                if(formActivity != null){
                    setDataTOProduct(formActivity, fragment);
                    formActivity.viewPager.setCurrentItem(2);
                }
            }
        });

        fragment.findViewById(R.id.txtPrevFragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFormActivity formActivity = (ProductFormActivity) getActivity();

                if(formActivity != null){
                    formActivity.viewPager.setCurrentItem(0);
                }
            }
        });
    }
    private void setDataTOProduct(ProductFormActivity formActivity, View fragment){
        price = fragment.findViewById(R.id.txtPrice);
        discount = fragment.findViewById(R.id.txtDiscount);
        quantity = fragment.findViewById(R.id.txtQuantity);

        formActivity.product.setPrice(Double.parseDouble(price.getText().toString()));
        formActivity.product.setDiscount(Double.parseDouble(discount.getText().toString()));
        formActivity.product.setQuantity(Integer.parseInt(quantity.getText().toString()));
    }
}