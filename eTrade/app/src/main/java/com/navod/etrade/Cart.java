package com.navod.etrade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.navod.etrade.adapter.CartRecyclerViewAdapter;
import com.navod.etrade.callback.DeleteCartItemCallback;
import com.navod.etrade.callback.FetchedUserCartItemCallBack;
import com.navod.etrade.callback.GetCartItemCallback;
import com.navod.etrade.callback.IndividualMessageAddedCallback;
import com.navod.etrade.callback.OrderAddedCallback;
import com.navod.etrade.callback.UserCallback;
import com.navod.etrade.entity.CartItem;
import com.navod.etrade.entity.IndividualMessage;
import com.navod.etrade.entity.Order;
import com.navod.etrade.entity.OrderItem;
import com.navod.etrade.entity.User;
import com.navod.etrade.service.CartService;
import com.navod.etrade.service.MessageService;
import com.navod.etrade.service.OrderService;
import com.navod.etrade.service.UserService;
import com.navod.etrade.util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    private static final String TAG = Cart.class.getName();
    private String userId;
    private List<CartItem> cartItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        userId = SharedPreferencesManager.getUserId(Cart.this);
        cartItemsList = new ArrayList<>();

        setRecyclerView();

       findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteCartItems();
           }
       });
       findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(!cartItemsList.isEmpty()){
                    setOrder();
                }
           }
       });
    }
    private void deleteCartItems(){
        CartService cartService = new CartService();
        cartService.getCartByUserDocumentId(userId, new GetCartItemCallback() {
            @Override
            public void onSuccess(List<CartItem> cartItems) {
                cartService.DeleteCartItem(userId, cartItems, new DeleteCartItemCallback() {
                    @Override
                    public void onSuccess(String successMessage) {
                        Log.i(TAG, successMessage);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                                Log.i(TAG, errorMessage);
                            }
                });
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void setOrder() {
        Order order = createOrder();
        addOrder(order);

        sendOrderSuccessMessage();
    }

    private Order createOrder() {
        Order order = new Order();
        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItemsList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            totalPrice = totalPrice + cartItem.getPrice();
            orderItems.add(orderItem);
        }

        order.setCustomerId(userId);
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        return order;
    }

    private void sendOrderSuccessMessage() {
        MessageService messageService = new MessageService(userId, Cart.this);
        IndividualMessage individualMessage = new IndividualMessage();
        individualMessage.setTitle("Order Added Successfully");
        individualMessage.setContent("Make sure to have money when the order arrives.");

        messageService.addMessage(individualMessage, new IndividualMessageAddedCallback() {
            @Override
            public void onSuccess(IndividualMessage individualMessage) {
                Log.i(TAG, "Message sent successfully.");
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, error);
            }
        });
    }
    private void addOrder(Order order){
        OrderService orderService = new OrderService();
        orderService.addOrder(order, new OrderAddedCallback() {
            @Override
            public void OnSuccess(String successMessage) {
                Log.i(TAG, successMessage);
                deleteCartItems();
            }

            @Override
            public void OnFailure(String failMessage) {
                Log.i(TAG, failMessage);
            }
        });
    }
    private void setRecyclerView(){
        fetchUserCartData(userId, new FetchedUserCartItemCallBack() {
            @Override
            public void onSuccess(List<CartItem> cartItemList) {
                Log.i(TAG, cartItemList.toString());
                RecyclerView recyclerView = findViewById(R.id.cartRecyclerView);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Cart.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                CartRecyclerViewAdapter cartRecyclerViewAdapter = new CartRecyclerViewAdapter(Cart.this, cartItemList);
                recyclerView.setAdapter(cartRecyclerViewAdapter);
            }
        });
    }
    private void fetchUserCartData(String userId, FetchedUserCartItemCallBack callBack) {

        CartService cartService = new CartService();
        cartService.getCartByUserDocumentId(userId, new GetCartItemCallback() {
            @Override
            public void onSuccess(List<CartItem> cartItems) {
                cartItemsList.addAll(cartItems);
                callBack.onSuccess(cartItemsList);
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Error getting cart: " + error);
            }
        });
    }
}