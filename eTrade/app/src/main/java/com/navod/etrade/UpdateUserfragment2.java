package com.navod.etrade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.navod.etrade.entity.User;
import com.navod.etrade.listener.OnUserUpdateListener;
import com.navod.etrade.model.SharedUserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateUserfragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateUserfragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = UpdateUserfragment2.class.getName();
    private User user;
    private SharedUserViewModel sharedUserViewModel;
    private TextView address;

    public UpdateUserfragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateUserfragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateUserfragment2 newInstance(String param1, String param2) {
        UpdateUserfragment2 fragment = new UpdateUserfragment2();
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
        sharedUserViewModel = new ViewModelProvider(requireActivity()).get(SharedUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_userfragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        sharedUserViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user1) {
                Log.i(TAG, "fragment2: "+user1.toString());
                user = user1;
            }
        });

        initView(fragment);
        setExistingAddress();


        Button btn = getActivity().findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdatedAddress();
                if(getActivity() instanceof OnUserUpdateListener){
                    ((OnUserUpdateListener) getActivity()).onUserUpdated(user);
                    ((OnUserUpdateListener) getActivity()).handleUpdatedUser(user);
                }
            }
        });
    }
    private void getUpdatedAddress(){
        user.setAddress(address.getText().toString());
    }

    private void setExistingAddress(){
        if(user != null){
            if(user.getAddress() != null){
                address.setText(user.getAddress());
            }
        }
    }
    private void initView(View fragment){
        address = fragment.findViewById(R.id.editTextTextMultiLine);
    }
}