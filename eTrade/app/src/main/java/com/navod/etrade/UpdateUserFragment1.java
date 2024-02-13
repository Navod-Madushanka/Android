package com.navod.etrade;

import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.navod.etrade.entity.User;
import com.navod.etrade.model.SharedUserViewModel;
import com.navod.etrade.util.ImageSelector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateUserFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateUserFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView userImage;
    private TextView username, mobile, password, newPassword;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageSelector imageSelector;
    private ImageSelector imageSelector1;
    private static final String TAG = UpdateUserFragment1.class.getName();
    private User user;
    private SharedUserViewModel sharedUserViewModel;


    public UpdateUserFragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateUserFragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateUserFragment1 newInstance(String param1, String param2) {
        UpdateUserFragment1 fragment = new UpdateUserFragment1();
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
        return inflater.inflate(R.layout.fragment_update_user1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        UpdateUser activity = (UpdateUser) getActivity();
        imageSelector = new ImageSelector();

        initViews(fragment);
        imageSelector1 = new ImageSelector(this, userImage);

        sharedUserViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user1) {
                setUserDetails(user1);
                user = user1;
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelector1.launchImageSelection();
            }
        });

        fragment.findViewById(R.id.textView18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, user.toString());
                getUpdatedUser();
                Log.i(TAG, user.toString());
                activity.viewPager.setCurrentItem(1);
            }
        });
    }
    private void getUpdatedUser() {
        if(imageSelector1.getSelectedImageUri() != null){
            user.setProfilePictureUrl(imageSelector1.getSelectedImageUri().toString());
        }
        user.setUsername(username.getText().toString());
        user.setMobileNumber(mobile.getText().toString());
        setNewPassword();
        sharedUserViewModel.setUser(user);
    }

    private void setNewPassword() {
        if (!user.getPassword().isEmpty() && user.getPassword().equals(password.getText().toString())) {
            user.setPassword(newPassword.getText().toString());
        }
    }

    private void setUserDetails(User user) {
        if (user != null) {
            username.setText(user.getUsername());
            if (userImage != null && user.getProfilePictureUrl() != null) {
                imageSelector.loadImageUriIntoImageView(Uri.parse(user.getProfilePictureUrl()), userImage);
            }
            if (mobile != null && user.getMobileNumber() != null) {
                mobile.setText(user.getMobileNumber());
            }
        } else {
            Log.i(TAG, "user is null");
        }
    }

    private void initViews(View fragment) {
        userImage = fragment.findViewById(R.id.imageView16);
        username = fragment.findViewById(R.id.editTextText);
        mobile = fragment.findViewById(R.id.editTextNumber);
        password = fragment.findViewById(R.id.editTextTextPassword);
        newPassword = fragment.findViewById(R.id.editTextTextPassword2);
    }
}