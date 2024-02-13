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

import com.navod.etrade.adapter.OrderRecyclerViewAdapter;
import com.navod.etrade.callback.GetOrderCallBack;
import com.navod.etrade.model.OrderStatus;
import com.navod.etrade.service.OrderService;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentOrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView currentOrdersRecyclerView;
    private String userId;
    private List<com.navod.etrade.entity.Order> orderListMain;
    private static final String TAG = CurrentOrdersFragment.class.getName();

    public CurrentOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentOrdersFragment newInstance(String param1, String param2) {
        CurrentOrdersFragment fragment = new CurrentOrdersFragment();
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
        return inflater.inflate(R.layout.fragment_current_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        initViews(fragment);
        setAdapter();
    }
    private void getData(GetOrderCallBack callBack){
        OrderService orderService = new OrderService();
        Map<String, Object> condition = new HashMap<>();
        condition.put("orderStatus", Arrays.asList(OrderStatus.PENDING, OrderStatus.PROCESSING, OrderStatus.SHIPPING));

        orderService.getOrder(userId, condition, new GetOrderCallBack() {
            @Override
            public void onSuccess(List<com.navod.etrade.entity.Order> orderList) {
                orderListMain.addAll(orderList);
                callBack.onSuccess(orderListMain);
            }

            @Override
            public void onFailure(String failMessage) {
                Log.i(TAG, failMessage);
                callBack.onFailure(failMessage);
            }
        });
    }
    private void setAdapter(){
        getData(new GetOrderCallBack() {
            @Override
            public void onSuccess(List<com.navod.etrade.entity.Order> orderList) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Order) getActivity());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                currentOrdersRecyclerView.setLayoutManager(linearLayoutManager);
                OrderRecyclerViewAdapter orderRecyclerViewAdapter = new OrderRecyclerViewAdapter((Order) getActivity(), orderList);
                currentOrdersRecyclerView.setAdapter(orderRecyclerViewAdapter);
            }

            @Override
            public void onFailure(String failMessage) {

            }
        });
    }
    private void initViews(View fragment){
        orderListMain = new ArrayList<>();
        userId = SharedPreferencesManager.getUserId((Order) getActivity());
        currentOrdersRecyclerView = fragment.findViewById(R.id.currentOrderRecyclerView);
    }
}