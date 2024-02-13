package com.navod.etrade;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.navod.etrade.entity.User;
import com.navod.etrade.callback.UserExistenceCallback;
import com.navod.etrade.service.UserService;
import com.navod.etrade.util.SecureApp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogIn extends Fragment {
    private static final String TAG = LogIn.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogIn.
     */
    // TODO: Rename and change types and number of parameters
    public static LogIn newInstance(String param1, String param2) {
        LogIn fragment = new LogIn();
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
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        fragment.findViewById(R.id.btnLoginFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIngUserAction(fragment);
            }
        });
    }
    private void logIngUserAction(View fragment){
        TextView email = fragment.findViewById(R.id.emailTextView);
        TextView password = fragment.findViewById(R.id.passwordText);

        UserService userService = new UserService(getContext());

        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(SecureApp.encrypt(password.getText().toString()));

        userService.logInUser(user, new UserExistenceCallback() {
            @Override
            public void onResult(boolean userExists, String userId) {
                if (userExists) {
                    Log.i(TAG, "User successfully logged in, userID: " + userId);
                    goToMAinActivity();
                } else {
                    Log.i(TAG, "Log In Failed");
                    showError("Login Failed");
                }
            }
        });
    }
    private void showError(String errorMessage){
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
    private void goToMAinActivity(){
        Intent intent = new Intent((AuthActivity)getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}