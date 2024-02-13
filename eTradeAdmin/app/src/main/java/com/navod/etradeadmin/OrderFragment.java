package com.navod.etradeadmin;

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

import com.navod.etradeadmin.CallBack.GetOrderCallBack;
import com.navod.etradeadmin.adapter.OrderRecyclerViewAdapter;
import com.navod.etradeadmin.entity.Order;
import com.navod.etradeadmin.models.OrderStatus;
import com.navod.etradeadmin.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private OrderService orderService;
    private static  final String TAG = OrderFragment.class.getName();

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);
        initViews(fragment);
        setRecyclerView();
    }
    private void getOrderList(GetOrderCallBack callBack){
        List<OrderStatus> statusList = Arrays.asList(OrderStatus.PENDING, OrderStatus.PROCESSING);

        Map<String, List<OrderStatus>> conditions = new HashMap<>();
        conditions.put("orderStatus", statusList);
        orderService.getOrders(conditions, new GetOrderCallBack() {
            @Override
            public void onSuccess(List<Order> orderList) {
                Log.i(TAG, orderList.toString());
                callBack.onSuccess(orderList);
            }

            @Override
            public void onFailure(String failMessage) {
                Log.i(TAG, failMessage);
                callBack.onFailure(failMessage);
            }
        });
    }
    private void setRecyclerView(){
        getOrderList(new GetOrderCallBack() {
            @Override
            public void onSuccess(List<Order> orderList) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager((HomeActivity) getActivity());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                OrderRecyclerViewAdapter orderRecyclerViewAdapter = new OrderRecyclerViewAdapter(getContext(), orderList);
                recyclerView.setAdapter(orderRecyclerViewAdapter);
            }

            @Override
            public void onFailure(String failMessage) {

            }
        });
    }
    private void initViews(View fragment){
        orderService = new OrderService();
        recyclerView = fragment.findViewById(R.id.orderRecyclerView);
    }
}