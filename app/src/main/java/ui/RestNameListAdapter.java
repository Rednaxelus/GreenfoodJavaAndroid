package ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.greenfoodjava.R;

public class RestNameListAdapter extends ResourceCursorAdapter {
    int mode; //0 for restaurant, 1 for dish

    public RestNameListAdapter(Context context, int layout, Cursor c, int flags, int mode) {
        super(context, layout, c, flags);
        this.mode = mode;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        if (mode == 0) {
            TextView restaurantName = view.findViewById(R.id.dish_name);
            TextView restaurantPhone = view.findViewById(R.id.dish_price);
            TextView restaurantAddress = view.findViewById(R.id.restaurant_address);

            String name = cursor.getString(cursor.getColumnIndex("name"));
            int phone = cursor.getInt(cursor.getColumnIndex("phoneNumber"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            // Populate fields with extracted properties
            restaurantName.setText(name);
            restaurantPhone.setText(String.valueOf(phone));
            restaurantAddress.setText(address);
        } else {
            TextView productName = view.findViewById(R.id.product_name);
            TextView productPrice = view.findViewById(R.id.product_price);
            TextView productStock = view.findViewById(R.id.product_stock);

            String name = cursor.getString(cursor.getColumnIndex("name"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            double stock = cursor.getDouble(cursor.getColumnIndex("stock"));

            productName.setText(name);
            productPrice.setText(String.valueOf(price));
            productStock.setText(String.valueOf(stock));
        }


    }

}
