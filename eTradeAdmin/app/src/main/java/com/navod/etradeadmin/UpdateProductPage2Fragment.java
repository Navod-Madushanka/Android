package com.navod.etradeadmin;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.navod.etradeadmin.entity.Product;
import com.navod.etradeadmin.listener.OnProductUpdateListener;
import com.navod.etradeadmin.util.ImageSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProductPage2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProductPage2Fragment extends Fragment {
    private ImageView image1, image2, image3;
    private Button updateBtn, deleteBtn;
    private static final String TAG = UpdateProductPage2Fragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProductPage2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProductPage2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProductPage2Fragment newInstance(String param1, String param2) {
        UpdateProductPage2Fragment fragment = new UpdateProductPage2Fragment();
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
        return inflater.inflate(R.layout.fragment_update_product_page2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        UpdateProductActivity activity = (UpdateProductActivity) getActivity();

        initViews(fragment);
        setCurrentImages(activity.product);
        ImageSelector imageSelector1 = new ImageSelector(this, image1);
        ImageSelector imageSelector2 = new ImageSelector(this, image2);
        ImageSelector imageSelector3 = new ImageSelector(this, image3);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector1.launchImageSelection();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector2.launchImageSelection();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector3.launchImageSelection();
            }
        });
        fragment.findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImages(activity.product, imageSelector1, imageSelector2, imageSelector3);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void updateImages(Product product, ImageSelector imageSelector1,
                              ImageSelector imageSelector2, ImageSelector imageSelector3){
        List<String> imagePaths = new ArrayList<>();
        imagePaths.add(imageSelector1.getSelectedImageUri().toString());
        imagePaths.add(imageSelector2.getSelectedImageUri().toString());
        imagePaths.add(imageSelector3.getSelectedImageUri().toString());

        product.setImages(imagePaths);

        if(getActivity() instanceof OnProductUpdateListener){
            ((OnProductUpdateListener) getActivity()).onProductUpdated(product);
            ((UpdateProductActivity) getActivity()).handleUpdatedProduct(product);
        }
    }
    private void setCurrentImages(Product product){
        ImageSelector imageSelector = new ImageSelector();
        Log.i(TAG, "setCurrentImages: "+product.getImageUriList().size());
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(0)), image1);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(1)), image2);
        imageSelector.loadImageUriIntoImageView(Uri.parse(product.getImageUriList().get(2)), image3);
    }
    private void initViews(View fragment){
        image1 = fragment.findViewById(R.id.imageView12);
        image2 = fragment.findViewById(R.id.imageView13);
        image3 = fragment.findViewById(R.id.imageView14);
        updateBtn = fragment.findViewById(R.id.button5);
        deleteBtn = fragment.findViewById(R.id.button4);
    }
}