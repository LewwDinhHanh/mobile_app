package tlu.edu.vn.ht63.mobilshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import tlu.edu.vn.ht63.mobilshop.adapter.ProductsAdapter;
import tlu.edu.vn.ht63.mobilshop.database.ProductDatabase;
import tlu.edu.vn.ht63.mobilshop.model.Cart;
import tlu.edu.vn.ht63.mobilshop.model.Product;
import tlu.edu.vn.ht63.mobilshop.model.User;
import tlu.edu.vn.ht63.mobilshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProductRepository productRepository;
    RecyclerView rvProduct;
    ImageView mnu_cart;
    private Cart cart = new Cart();
    private User user;

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView viewButton = findViewById(R.id.view);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code để chuyển sang trang View Activity
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });

        // Lấy reference của ImageView
        backButton = findViewById(R.id.back);

        // Thiết lập sự kiện click cho ImageView
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển về trang LoginActivity
                Intent intent = new Intent(MainActivity.this, tlu.edu.vn.ht63.mobilshop.LoginActivity.class);
                startActivity(intent);
            }
        });

        initData();
        user = (User) getIntent().getSerializableExtra("User");
        productRepository = new ProductRepository(this);

        rvProduct = findViewById(R.id.rvproduct);
        mnu_cart = findViewById(R.id.mnu_cart);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2); //1
        rvProduct.setLayoutManager(mLayoutManager);

        ProductsAdapter rvAdapter  = new ProductsAdapter(this, this.productRepository.getProductList(this));
        rvProduct.setAdapter(rvAdapter);

        mnu_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tlu.edu.vn.ht63.mobilshop.CartsActivity.class);
                intent.putExtra("User",user);
                startActivity(intent);
            }
        });

    }

    private void initData(){
        List<Product> list = ProductDatabase.getInstance(this).productDAO().getListProduct();
        if (list ==null || list.isEmpty()){
            ArrayList<Product> alProduct = new ArrayList<>();
            int[] imageResources = {
                    R.drawable.ss_0,
                    R.drawable.ss_1,
                    R.drawable.ss_2,
                    R.drawable.ss_3,
                    R.drawable.ss_4,
                    R.drawable.ss_5
            };
            for (int i = 0; i < 20; i++) {
                Product p = new Product("NamePhone " + i);
                p.setDescreption("This is a unique and quality product, serving all user needs");
                p.setImage(imageResources[i % imageResources.length]);

                String random = String.format("%.2f",new Random().nextFloat() * 1000);

                random = random.replace(",", "");
                float price = Float.parseFloat(random);
                p.setPrice(price);
                alProduct.add(p);
            }
            this.productRepository = new ProductRepository(alProduct,this);
        }
    }

}