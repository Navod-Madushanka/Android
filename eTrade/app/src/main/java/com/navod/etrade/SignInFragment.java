package com.navod.etrade;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.navod.etrade.callback.UserSignInCallback;
import com.navod.etrade.entity.User;
import com.navod.etrade.service.UserService;
import com.navod.etrade.util.NotificationHelper;
import com.navod.etrade.util.SecureApp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    private static final String TAG = SignInFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        fragment.findViewById(R.id.btnSignInFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserAction(fragment);
            }
        });
    }
    private void saveUserAction(View fragment){
        TextView username = fragment.findViewById(R.id.textUsername);
        TextView email = fragment.findViewById(R.id.textEmail);
        TextView password = fragment.findViewById(R.id.textPassword);

        User user = new User();
        user.setUsername(username.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(SecureApp.encrypt(password.getText().toString()));

        UserService userService = new UserService(getContext());
        userService.saveUser(user, new UserSignInCallback() {
            @Override
            public void success(boolean success) {
                if (success){
                    NotificationHelper.createNotificationChannel(getContext());
                    NotificationHelper.sendNotification(getContext(), "Info", "Welcome to eTrade: "+ user.getUsername());
                    reLoad();
                }else{
                    Toast.makeText(getContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void reLoad(){
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
}