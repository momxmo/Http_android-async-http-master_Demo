package com.mo.http_android_async_http_master_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

//使用框架对服务器端的get和post访问


public class MainActivity extends AppCompatActivity {
    private static final int SUCCESS = 0;
    private static final int FAILE = 1;
    private static final int NET_ERROR = 3;
    private static final String TAG = "MainActivity";
    EditText et_username;
    EditText et_password;
    TextView show_result;
    String username;
    String password;

    final String path = "http://188.188.7.85/Android_Server/Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        show_result = (TextView) findViewById(R.id.show_result);

        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
    }

    public void login(View view) {
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }


        //使用android-async-http-maste框架API get方式的请求服务器
//        get();


        // 使用android-async-http-maste框架API post方式请求服务器
        post();

    }

    //使用post方式请求
    public  void post(){
        //1.定义一个异步请求的http请求客户端
        AsyncHttpClient client = new AsyncHttpClient();

        //定义传递的参数数据
        RequestParams params = new RequestParams();
        params.add("username",username);
        params.add("password",password);

        //执行一个请求,将参数带过去
        client.post(path,params, new AsyncHttpResponseHandler() {

            //请求执行成功的时候调用，能访问到服务器，但是有可能返回404 或者5001内部错误
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    show_result.setText(new String(responseBody,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            //请i去执行失败的时候调用
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                try {
                    show_result.setText(new String(responseBody, "UTF-8"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    //使用get方式请求
    public void get() {
        try {
            String commitPath = path + "?username="
                    + URLEncoder.encode(username, "UTF-8") +
                    "&password="
                    + URLEncoder.encode(password, "UTF-8");

            //1.定义一个异步请求的http请求客户端
            AsyncHttpClient client = new AsyncHttpClient();

            //执行一个get请求
            client.get(commitPath, new AsyncHttpResponseHandler() {

                //请求执行成功的时候调用，能访问到服务器，但是有可能返回404 或者5001内部错误
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        show_result.setText(new String(responseBody,"UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

                //请i去执行失败的时候调用
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    try {
                        show_result.setText(new String(responseBody, "UTF-8"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}















