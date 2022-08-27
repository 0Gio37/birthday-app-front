package com.georges.android.birthday_app_front.adaptater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.georges.android.birthday_app_front.activities.MainActivity;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.utils.ApiCallBack;
import com.georges.android.birthday_app_front.utils.Util;
import com.georges.android.birthday_app_front.utils.UtilApi;

import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;

public class BirthdayAdaptater extends RecyclerView.Adapter<BirthdayAdaptater.ViewHolder> implements ApiCallBack {

    private Context mContext;
    private List<Birthday> mBirthdays;
    private Long id;
    private MainActivity mainActivity;

    public BirthdayAdaptater(Context mContext, List<Birthday> mBirthdays, Long id){
        this.mContext = mContext;
        this.mBirthdays = mBirthdays;
        this.id = id;
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

    @Override
    public void fail(String json) {

    }

    @Override
    public void success(String json) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
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

            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            String selectedAnniversaire = mBirthday.firstname +" "+mBirthday.lastname+mBirthday+mBirthday;
            String idSelectedBirthday = mBirthday.id;
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Anniversaire de "+selectedAnniversaire);
            builder.setPositiveButton("MODIFIER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
            //TODO
                }
            });
            builder.setNegativeButton("SUPPRIMER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String urlDelete = UtilApi.URL_BIRTHDAY+"/"+id.toString()+"/birthdays";

                    UtilApi.delete(urlDelete, mBirthday.id,BirthdayAdaptater.this);
                    Log.d("urlDelete", urlDelete);
                    Log.d("id birthday", mBirthday.id);

                }
            });
            builder.create().show();
            return true;
        }
    }



}
