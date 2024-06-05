package tlu.edu.vn.ht63.mobilshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tlu.edu.vn.ht63.mobilshop.adapter.CartsAdapter;
import tlu.edu.vn.ht63.mobilshop.database.OrderDatabase;
import tlu.edu.vn.ht63.mobilshop.database.UserDAO;
import tlu.edu.vn.ht63.mobilshop.database.UserDatabase;
import tlu.edu.vn.ht63.mobilshop.model.Cart;
import tlu.edu.vn.ht63.mobilshop.model.Order;
import tlu.edu.vn.ht63.mobilshop.model.User;

public class CartsActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private TextView tvTotal, tvResult;
    private ImageView imgexit;
    private EditText etEmployeeId;
    private Button btnCalculate;
    private Cart cart = new Cart();
    private OrderDatabase orderDb;
    private UserDatabase userDb;
    private UserDAO userDAO;
    private User userNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userDb = UserDatabase.getInstance(this);
        userDAO = userDb.userDAO();

        userNow = (User) getIntent().getSerializableExtra("User");
        imgexit = findViewById(R.id.imgexit);
        imgexit.setOnClickListener(v -> {
            Intent intent = new Intent(CartsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tvTotal = findViewById(R.id.tvTotal);
        tvResult = findViewById(R.id.tvResult);
        rvProduct = findViewById(R.id.rvproduct);
        btnCalculate = findViewById(R.id.btnCalculate);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(layoutManager);

        CartsAdapter rvAdapter = new CartsAdapter(this, cart);
        rvProduct.setAdapter(rvAdapter);
        tvTotal.setText(String.valueOf(cart.getTotalPrice()));

        orderDb = Room.databaseBuilder(getApplicationContext(), OrderDatabase.class, "orders-db").build();

        btnCalculate.setOnClickListener(v -> calculateAndSaveOrder());
    }

    private void calculateAndSaveOrder() {
            float totalPrice = cart.calculateTotalPriceWithDiscount();
            saveOrder("", totalPrice, totalPrice);
            Toast.makeText(this, "Đã lưu đơn hàng", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng activity hiện tại
    }


    private void saveOrder(String discountCode, float totalPrice, float totalPriceWithDiscount) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Order order = new Order(discountCode, totalPrice, totalPriceWithDiscount);
            orderDb.orderDao().insert(order);

            runOnUiThread(() ->
                    Toast.makeText(CartsActivity.this, "Order saved successfully", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public void updateData() {
        float totalPrice = cart.getTotalPrice();
        float discount = cart.getDiscount();
        float totalPriceWithDiscount = totalPrice * (1 - discount);

        tvTotal.setText(String.valueOf(totalPrice));
        tvResult.setText(String.valueOf(totalPriceWithDiscount));
    }
}
