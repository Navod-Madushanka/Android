package com.navod.etrade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.navod.etrade.adapter.MessageRecyclerViewAdapter;
import com.navod.etrade.callback.GetIndividualMessageListCallback;
import com.navod.etrade.entity.IndividualMessage;
import com.navod.etrade.service.MessageService;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private Button myMsgBtn, allMsgBtn;
    private MessageService massageService;
    private static final String TAG = NotificationFragment.class.getName();

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getIndividualMessageList();
    }

    private void getIndividualMessageList(){
        massageService.getMessages(new GetIndividualMessageListCallback() {
            @Override
            public void onSuccess(List<IndividualMessage> individualMessageList) {
                setRecyclerView(individualMessageList);
            }

            @Override
            public void onFailure(String error) {
                Log.i(TAG, error);
                Toast.makeText(getContext(), "Error getting Messages", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setRecyclerView(List<IndividualMessage> individualMessageList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MessageRecyclerViewAdapter messageRecyclerViewAdapter;
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(getContext(), individualMessageList);
        recyclerView.setAdapter(messageRecyclerViewAdapter);

    }
    private void initViews(View fragment){
        myMsgBtn = fragment.findViewById(R.id.button14);
        allMsgBtn = fragment.findViewById(R.id.button15);
        recyclerView = fragment.findViewById(R.id.messageRecyclerView);
        massageService = new MessageService(SharedPreferencesManager.getUserId(getContext()), getContext());
    }
}