package tlu.edu.vn.ht63.mobilshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tlu.edu.vn.ht63.mobilshop.repository.ProductRepository;

public class Cart {
    public static Map<Integer, Integer> cartList = new HashMap<>();
    public ProductRepository productRepository = new ProductRepository();
    private static float totalPrice;
    private float discount;  // Discount percentage (e.g., 0.15 for 15%)

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        Cart.totalPrice = totalPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public void addCart(Product product) {
        Integer quantity = cartList.getOrDefault(product.getId(), 0);
        if (quantity >= 10) return;
        cartList.put(product.getId(), quantity + 1);

        float productPrice = product.getPrice();
        if (discount > 0) {
            productPrice -= productPrice * discount;
        }

        totalPrice += productPrice;
    }

    public Product getProductByOrder(Integer pos) {
        List<Integer> keys = new ArrayList<>(cartList.keySet());
        return productRepository.getProduct(keys.get(pos));
    }

    public float getLinePrice(Product p) {
        return p.getPrice() * cartList.getOrDefault(p.getId(), 0);
    }

    public void removeCart(Product p) {
        Integer quantity = cartList.getOrDefault(p.getId(), 0);
        if (quantity <= 0) return;
        cartList.put(p.getId(), quantity - 1);

        float productPrice = p.getPrice();
        if (discount > 0) {
            productPrice -= productPrice * discount;
        }

        totalPrice -= productPrice;
    }

    public void clearCart() {
        cartList.clear();
        totalPrice = 0;
    }

    // Hàm tính toán lại tổng giá sau khi áp dụng giảm giá
    public float calculateTotalPriceWithDiscount() {
        float total = 0;
        for (Map.Entry<Integer, Integer> entry : cartList.entrySet()) {
            Product product = productRepository.getProduct(entry.getKey());
            if (product != null) {
                total += product.getPrice() * entry.getValue();
            }
        }
        if (discount > 0) {
            total = total - (total * discount);
        }
        return total;
    }
}
