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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.activities.MainActivity;
import com.georges.android.birthday_app_front.models.Birthday;
import com.georges.android.birthday_app_front.utils.ApiCallBack;
import com.georges.android.birthday_app_front.utils.Util;
import com.georges.android.birthday_app_front.utils.UtilApi;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BirthdayAdaptater extends RecyclerView.Adapter<BirthdayAdaptater.ViewHolder> implements ApiCallBack {

    private Context mContext;
    private List<Birthday> mBirthdays;
    private Long mUserLoggedId;
    private String json;


    public BirthdayAdaptater(Context mContext, List<Birthday> mBirthdays, Long mUserLoggedId){
        this.mContext = mContext;
        this.mBirthdays = mBirthdays;
        this.mUserLoggedId = mUserLoggedId;
        this.json = json;
    }

    @NonNull
    @Override
    public BirthdayAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_birthday, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("userIDloggedAdaptater",mUserLoggedId.toString());
        Log.d("tb birthays", mBirthdays.toString());
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
        Log.d("FAIL ADD BIRTHDAY", "fail: " + json);
    }

    @Override
    public void success(String json) {
        Log.d("SUCCES ADD BIRTHDAY", "success: " + json);
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
            String selectedAnniversaire = mTexteViewItemName.getText().toString();
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Anniversaire de : "+selectedAnniversaire);
            builder.setPositiveButton("MODIFIER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View v = LayoutInflater.from(mContext).inflate(R.layout.add_new_birthday, null);
                    EditText newFirstname = (EditText) v.findViewById(R.id.edit_text_add_birthday_firstname);
                    EditText newLastname = (EditText) v.findViewById(R.id.edit_text_add_birthday_lastname);
                    EditText newDate = (EditText) v.findViewById(R.id.edit_text_add_birthday_date);
                    builder.setView(v);
                    builder.setTitle("Nouvelles valeurs : " );
                    builder.setPositiveButton("MODIFIER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Date date = null;
                            try {
                                date = Util.initDateFromEditText(newDate.getText().toString());
                                Birthday birthday = new Birthday(null,date,newFirstname.getText().toString(), newLastname.getText().toString());
                                Map<String, String> map = new HashMap<>();
                                map.put("id", mBirthday.id);
                                map.put("date", Util.printDate(birthday.date));
                                map.put("firstname", birthday.firstname);
                                map.put("lastname", birthday.lastname);
                                String urlPut = UtilApi.URL_BIRTHDAY+"/"+mUserLoggedId.toString()+"/birthdays";
                                UtilApi.put(urlPut,map,BirthdayAdaptater.this);

                                for (Birthday selectedBirthday : mBirthdays
                                ) {
                                    if (selectedBirthday.getId() == mBirthday.id){
                                        selectedBirthday.setDate(birthday.date);
                                        selectedBirthday.setFirstname(birthday.firstname);
                                        selectedBirthday.setLastname(birthday.lastname);
                                    }
                                }
                                Toast.makeText(mContext, "Anniversaire modifié", Toast.LENGTH_SHORT).show();
                                Util.createSortedListItems(mBirthdays);
                                notifyDataSetChanged();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("ANNULER", null);
                    builder.create().show();
                }
            });
            builder.setNegativeButton("SUPPRIMER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String urlDelete = UtilApi.URL_BIRTHDAY+"/"+mUserLoggedId.toString()+"/birthdays";
                    UtilApi.delete(urlDelete, mBirthday.id,BirthdayAdaptater.this);
                    for (Birthday selectedBirthday : mBirthdays
                    ) {
                        if (selectedBirthday.getId() == mBirthday.id){
                            mBirthdays.remove(selectedBirthday);
                        }
                    }
                    Toast.makeText(mContext, "Anniversaire supprimé", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
            builder.create().show();
            return true;
        }
    }



}
