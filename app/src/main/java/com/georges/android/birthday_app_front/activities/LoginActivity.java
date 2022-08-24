package com.georges.android.birthday_app_front.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.georges.android.birthday_app_front.utils.ApiCallBack;
import com.georges.android.birthday_app_front.databinding.ActivityLoginBinding;
import com.georges.android.birthday_app_front.utils.Util;

public class LoginActivity extends AppCompatActivity implements ApiCallBack {

    private ActivityLoginBinding binding;
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mLoginBtnView;
    private ProgressBar mProgressView;

    public Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler = new Handler();

        mEmailView = binding.editTextEmail;
        mPasswordView = binding.editTextPassword;
        mLoginBtnView = binding.btnLogin;
        mProgressView = binding.progressBarLoading;

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                mLoginBtnView.setEnabled(Util.isEmailValid(email) && Util.isPasswordValid(password));
            }
        };






        mLoginBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressView.setVisibility(View.VISIBLE);
                //loginViewModel.login(usernameEditText.getText().toString(),
                        //passwordEditText.getText().toString());
            }
        });
    }

    @Override
    public void fail(String json) {
    }

    @Override
    public void success(String json) {
    }
}