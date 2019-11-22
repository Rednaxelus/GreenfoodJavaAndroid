package ui;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.greenfoodjava.R;

public class ListAdapter extends ResourceCursorAdapter {
    public ListAdapter(Context context,int layout, Cursor c, int flags) {
        super(context,layout, c,0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView restaurantName = view.findViewById(R.id.restaurant_name);
        TextView restaurantPhone = view.findViewById(R.id.restaurant_phone);
        TextView restaurantAddress = view.findViewById(R.id.restaurant_address);

        String name = cursor.getString(cursor.getColumnIndex("name"));
        int phone = cursor.getInt(cursor.getColumnIndex("phoneNumber"));
        String address = cursor.getString(cursor.getColumnIndex("address"));
        // Populate fields with extracted properties
        restaurantName.setText(name);
        restaurantPhone.setText(String.valueOf(phone));
        restaurantAddress.setText(address);
    }

}
