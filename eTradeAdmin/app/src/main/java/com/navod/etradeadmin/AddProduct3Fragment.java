package com.navod.etradeadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.navod.etradeadmin.CallBack.AddProductCallback;
import com.navod.etradeadmin.service.ProductService;
import com.navod.etradeadmin.util.ImageSelector;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProduct3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProduct3Fragment extends Fragment {
    private static final String TAG = AddProduct3Fragment.class.getName();
    ProductFormActivity formActivity;

    private ImageView image1, image2, image3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProduct3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProduct3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProduct3Fragment newInstance(String param1, String param2) {
        AddProduct3Fragment fragment = new AddProduct3Fragment();
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
        return inflater.inflate(R.layout.fragment_add_product3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        formActivity = (ProductFormActivity) getActivity();

        image1 = fragment.findViewById(R.id.imageOne);
        ImageSelector imageSelector1 = new ImageSelector(this, image1);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector1.launchImageSelection();
            }
        });

        image2 = fragment.findViewById(R.id.imageTwo);
        ImageSelector imageSelector2 = new ImageSelector(this, image2);

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector2.launchImageSelection();
            }
        });

        image3 = fragment.findViewById(R.id.imageThree);
        ImageSelector imageSelector3 = new ImageSelector(this, image3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector3.launchImageSelection();
            }
        });

        fragment.findViewById(R.id.txtPrevFragment3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formActivity != null){
                    formActivity.viewPager.setCurrentItem(1);
                }
            }
        });

        fragment.findViewById(R.id.btnAddProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "On Click");
                ProgressDialog dialog = new ProgressDialog(fragment.getContext());
                dialog.setMessage("Adding New Product");
                dialog.setCancelable(false);
                dialog.show();

                List<String> imagePaths = new ArrayList<>();
                imagePaths.add(imageSelector1.getSelectedImageUri().toString());
                imagePaths.add(imageSelector2.getSelectedImageUri().toString());
                imagePaths.add(imageSelector3.getSelectedImageUri().toString());
                formActivity.product.setImages(imagePaths);

                dialog.setMessage("Setting images");
                ProductService productService = new ProductService(fragment.getContext());
                dialog.setMessage("Adding product and uploading images");
                Log.i(TAG, "Calling the add product");

                productService.addProduct(formActivity.product, new AddProductCallback() {
                    public void onSuccess(String productId) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "On success");
                                Log.i(TAG, "Product Id: " + productId);
                                dialog.dismiss();
                                Log.i(TAG, "Before intent");
                                Intent intent = new Intent(fragment.getContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "On failure");
                                Log.i(TAG, error);
                                dialog.dismiss();  // Dismiss the dialog on failure
                            }
                        });
                    }
                });

            }
        });
    }
}