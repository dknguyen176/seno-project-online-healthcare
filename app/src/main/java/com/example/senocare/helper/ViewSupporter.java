package com.example.senocare.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.senocare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class ViewSupporter {

    public static void putByteArrayToImageView(byte[] inputData, ImageView profile_pic, String gender){
        if (inputData == null) {
            if (gender.compareTo("Male") == 0) profile_pic.setImageResource(R.drawable.loid);
            else profile_pic.setImageResource(R.drawable.yor);
            return;
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(inputData, 0, inputData.length);
        bmp = Bitmap.createScaledBitmap(bmp, 100, 100, false);
        profile_pic.setImageBitmap(bmp);
    }

    public static void setDateEditText(EditText editText, String format, Context context, boolean maxDate, boolean minDate) {
        editText.setHint(format.toLowerCase());
        editText.setFocusable(false);
        editText.setClickable(true);
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);

            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            editText.setText(dateFormat.format(myCalendar.getTime()));
        };
        editText.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            if (maxDate) datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            if (minDate) datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }
}
