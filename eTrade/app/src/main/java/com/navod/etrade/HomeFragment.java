package com.navod.etrade;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.navod.etrade.util.ImageColorUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ImageColorUtil imageColorUtil;
    private ImageView phone, computer, accessories, cart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentManager fragmentManager;

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
        imageColorUtil = new ImageColorUtil(getContext());
        initViews(fragment);
        setEventListeners();
    }
    private void setEventListeners(){
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity) getActivity(), Cart.class);
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageView(0);
                Class<Phones> phonesClass = Phones.class;
                changeFragment(phonesClass);
            }
        });
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageView(1);
                Class<Computers> computersClass = Computers.class;
                changeFragment(computersClass);
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageView(2);
                Class<Accessories> accessoriesClass = Accessories.class;
                changeFragment(accessoriesClass);
            }
        });
    }
    private void changeFragment(Class fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment, null, "tag")
                .setReorderingAllowed(true)
                .addToBackStack("replacement")
                .commit();
    }
    private void changeImageView(int position){
        resetImageViews();
        switch (position){
            case 0:
                imageColorUtil.changeColor(phone);
                break;
            case 1:
                imageColorUtil.changeColor(computer);
                break;
            case 2:
                imageColorUtil.changeColor(accessories);
                break;
        }
    }
    private void resetImageViews(){
        imageColorUtil.resetImages(phone, computer, accessories);
    }
    private void initViews(View fragment){
        phone = fragment.findViewById(R.id.imageView8);
        computer = fragment.findViewById(R.id.imageView9);
        accessories = fragment.findViewById(R.id.imageView10);
        cart = fragment.findViewById(R.id.imageView7);
    }
}