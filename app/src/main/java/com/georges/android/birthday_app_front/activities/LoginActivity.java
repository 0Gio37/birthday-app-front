package com.georges.android.birthday_app_front.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.georges.android.birthday_app_front.R;
import com.georges.android.birthday_app_front.utils.ApiCallBack;
import com.georges.android.birthday_app_front.databinding.ActivityLoginBinding;
import com.georges.android.birthday_app_front.utils.Util;
import com.georges.android.birthday_app_front.utils.UtilApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ApiCallBack {

    private ActivityLoginBinding binding;
    private EditText mUsernameView;
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

        mUsernameView = binding.editTextUsername;
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
                String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();
                mLoginBtnView.setEnabled(Util.isUsernameValid(username) && Util.isPasswordValid(password));
            }
        };

        //a chaque frappe on check si les conditions sont remplies pour activer le btn
        mUsernameView.addTextChangedListener(textWatcher);
        mPasswordView.addTextChangedListener(textWatcher);

        //valid btn connexion 1 (keybord)
        mPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin();
            }
            return false;
        });
        //valid btn connexion 2 (click)
        mLoginBtnView.setOnClickListener(v -> {
            attemptLogin();
        });
    }

    //methode de validation de login
    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!Util.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!Util.isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.invalid_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            Map<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);

            UtilApi.post(UtilApi.URL_LOGIN,map,this);

        }
    }

    private void showProgress(boolean visible) {
        mProgressView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void fail(final String json) {
        mProgressView.setVisibility(View.INVISIBLE);
        handler.post(() -> {
            Log.d("lol", "fail: " + json);
            Toast.makeText(this, "Utilisateur ou mot de passe érroné", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void success(final String json) {

        handler.post(() -> {
            Log.d("INTENT DU LOGIN", "success: " + json);
            Toast.makeText(this, "connexion...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("userLogged",json);
            startActivity(intent);

        });
    }
}