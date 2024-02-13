package com.navod.etrade;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navod.etrade.callback.UserCallback;
import com.navod.etrade.entity.User;
import com.navod.etrade.service.UserService;
import com.navod.etrade.util.SecureApp;
import com.navod.etrade.util.SharedPreferencesManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserService userService;
    private User currentUser;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String userId = SharedPreferencesManager.getUserId(getContext());
        userService = new UserService();
        userService.getUserById(userId, new UserCallback() {
            @Override
            public void onUserResult(User user) {
                currentUser = user;
                String username = currentUser.getUsername();
                TextView usernameTxt = view.findViewById(R.id.textView7);
                usernameTxt.setText(username);
            }
        });
        view.findViewById(R.id.textView40).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCart();
            }
        });

        view.findViewById(R.id.textView42).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
        view.findViewById(R.id.textView41).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpdateUser();
            }
        });
        view.findViewById(R.id.textView45).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders();
            }
        });
    }
    private void goToUpdateUser(){
        Intent intent = new Intent(getContext(), UpdateUser.class);
        getActivity().startActivity(intent);
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void logout(){
        SharedPreferencesManager.clearUserId(getContext());
        SecureApp.validUser(getContext());
        goToHome();
    }
    private void goToHome(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        getActivity().startActivity(intent);
    }
    private void goToCart(){
        Intent intent = new Intent(getContext(), Cart.class);
        getActivity().startActivity(intent);
    }
    private void goToOrders(){
        Intent intent = new Intent(getContext(), Order.class);
        getActivity().startActivity(intent);
    }
}