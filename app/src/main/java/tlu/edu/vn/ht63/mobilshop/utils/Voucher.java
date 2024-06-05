package tlu.edu.vn.ht63.mobilshop.utils;

import android.util.Log;

import tlu.edu.vn.ht63.mobilshop.model.User;

public class Voucher {
    public static String generateDiscountCode(User user) {
        String employeeName = user.getEmployeename(); // Không cần chuyển đổi sang chuỗi
        String discountCode = employeeName + "DC";

        // In ra mã giảm giá được tạo ra
        Log.d("Voucher", "Discount code generated: " + discountCode);

        return discountCode;
    }
}

