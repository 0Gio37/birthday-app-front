package com.georges.android.birthday_app_front.adaptater;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.utils.Util;

import java.util.Calendar;
import java.util.List;

public class BirthdayAdaptater extends RecyclerView.Adapter<BirthdayAdaptater.ViewHolder> {

    private Context mContext;
    private List<Birthday> mBirthdays;

    public BirthdayAdaptater(Context mContext, List<Birthday> mBirthdays){
        this.mContext = mContext;
        this.mBirthdays = mBirthdays;
    }

    @NonNull
    @Override
    public BirthdayAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_birthday, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayAdaptater.ViewHolder holder, int position) {
        Birthday birthday = mBirthdays.get(position);
        String fullName = birthday.firstname+" "+birthday.lastname;
        String dayDate = Util.getDayFromDate(birthday.date);
        String monthDate = Util.getMonthfromDate(birthday.date);
        holder.mTexteViewItemName.setText(fullName);
        holder.mTexteViewItemAge.setText(Util.getAge(birthday.date)+" ans");
        holder.mTexteViewItemDateNumber.setText(dayDate);
        holder.mTexteViewItemDateMonth.setText(monthDate);
        holder.mBirthday = birthday;
    }

    @Override
    public int getItemCount() {
        return mBirthdays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTexteViewItemDateNumber;
        public TextView mTexteViewItemDateMonth;
        public TextView mTexteViewItemName;
        public TextView mTexteViewItemAge;
        public Birthday mBirthday;


        public ViewHolder(View view) {
            super(view);
            mTexteViewItemDateNumber = (TextView) view.findViewById(R.id.text_view_item_date_number);
            mTexteViewItemDateMonth = (TextView) view.findViewById(R.id.text_view_item_date_month);
            mTexteViewItemName = (TextView) view.findViewById(R.id.text_view_item_name);
            mTexteViewItemAge = (TextView) view.findViewById(R.id.text_view_item_age);
        }
    }



}
