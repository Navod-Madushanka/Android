package com.navod.etradeadmin;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProduct1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProduct1Fragment extends Fragment {

    private int categoryNo;
    private ImageView mobileCategory, computerCategory, accessoryCategory;
    private TextView brand, model, description;

    String [] brands = {
            "Apple",
            "Samsung",
            "Google",
            "Microsoft",
            "Dell",
            "HP",
            "Lenovo",
            "Asus",
            "Acer",
            "Sony",
            "Huawei",
            "Xiaomi",
            "OnePlus",
            "Toshiba",
            "LG",
            "Sony",
            "IBM",
            "Gateway",
            "MSI",
            "Razer"
    };
    MultiAutoCompleteTextView multiAutoCompleteTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProduct1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProduct1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProduct1Fragment newInstance(String param1, String param2) {
        AddProduct1Fragment fragment = new AddProduct1Fragment();
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
        return inflater.inflate(R.layout.fragment_add_product1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        setBrands(fragment);

        mobileCategory = fragment.findViewById(R.id.categoryMobile);
        computerCategory = fragment.findViewById(R.id.categoryComputer);
        accessoryCategory = fragment.findViewById(R.id.categoryAccessories);

        mobileCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeColor(fragment);
                categoryNo = 1;
                onCategoryClicked(mobileCategory, fragment);
            }
        });

        computerCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeColor(fragment);
                categoryNo = 2;
                onCategoryClicked(computerCategory, fragment);
            }
        });

        accessoryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeColor(fragment);
                categoryNo = 3;
                onCategoryClicked(accessoryCategory, fragment);
            }
        });

        fragment.findViewById(R.id.txtNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFormActivity formActivity = (ProductFormActivity) getActivity();


                if(formActivity != null){
                    setDataToProduct(formActivity, fragment);
                    formActivity.viewPager.setCurrentItem(1);
                }
            }
        });
    }
    private void setDataToProduct(ProductFormActivity formActivity, View fragment){

        brand = fragment.findViewById(R.id.txtBrandSelecter);
        model = fragment.findViewById(R.id.txtModel);
        description = fragment.findViewById(R.id.txtDescription);

        if(categoryNo == 1){
            formActivity.product.setCategory("Smart Phone");
        }else if(categoryNo == 2){
            formActivity.product.setCategory("Computer");
        }else{
            formActivity.product.setCategory("Accessories");
        }
        formActivity.product.setBrand(brand.getText().toString());
        formActivity.product.setModel(model.getText().toString());
        formActivity.product.setDescription(description.getText().toString());
    }
    private void removeColor(View fragment){
        Drawable drawable = mobileCategory.getDrawable();
        Drawable drawable1 = computerCategory.getDrawable();
        Drawable drawable2 = accessoryCategory.getDrawable();


        int color = ContextCompat.getColor(fragment.getContext(), R.color.buttonText);
        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);

        drawable.setColorFilter(colorFilter);
        drawable1.setColorFilter(colorFilter);
        drawable2.setColorFilter(colorFilter);

        mobileCategory.invalidate();
        computerCategory.invalidate();
        accessoryCategory.invalidate();
    }
    private void onCategoryClicked(ImageView image, View fragment){
        Drawable drawable = image.getDrawable();
        int color = ContextCompat.getColor(fragment.getContext(), R.color.colorPrimary);
        ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        drawable.setColorFilter(colorFilter);
        image.invalidate();
    }

    private void setBrands(View fragment){
        multiAutoCompleteTextView = fragment.findViewById(R.id.txtBrandSelecter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(fragment.getContext(), android.R.layout.simple_dropdown_item_1line, brands);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}