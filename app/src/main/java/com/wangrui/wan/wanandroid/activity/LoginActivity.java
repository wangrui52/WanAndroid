package com.wangrui.wan.wanandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wangrui.wan.wanandroid.GetRequest;
import com.wangrui.wan.wanandroid.R;
import com.wangrui.wan.wanandroid.interceptor.ReceivedCookiesInterceptor;
import com.wangrui.wan.wanandroid.utils.RetrofitUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wangrui.wan.wanandroid.Interface.BASE_URL;
import static com.wangrui.wan.wanandroid.utils.RetrofitUtils.TIMEOUT;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(this);
        Button register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                goLogin();
                break;
            case R.id.register:

                break;
        }
    }

    private void goLogin() {
        String name = userName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String password = passWord.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ReceivedCookiesInterceptor(this));

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest getRequest = mRetrofit.create(GetRequest.class);
        Call<ResponseBody> call = getRequest.goLogin(name,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
}
