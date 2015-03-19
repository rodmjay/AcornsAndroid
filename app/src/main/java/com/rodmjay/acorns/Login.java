package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

    public final static String apiUrl = "https://api.acorns.com/v1/sessions";
    AQuery aq;
    EditText EmailText;
    EditText PasswordText;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login);
        EmailText = (EditText) findViewById(R.id.username);
        PasswordText = (EditText) findViewById(R.id.password);

        aq = new AQuery(this);

        SetupUiEvents();
    }

    void SetupUiEvents() {

        LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    handleLoginClick((Button) v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void handleLoginClick(Button button) throws Exception {

        String username = EmailText.getText().toString();
        String password = PasswordText.getText().toString();

        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();

        cb.url(apiUrl).type(JSONObject.class).weakHandler(this, "LoginCallback");

        cb.header("X-Client-App", "mint");
        cb.header("X-Client-Build", "mint");
        cb.header("X-Client-Os", "mint");
        cb.header("X-Client-Platform", "mint");
        cb.header("User-Agent", "mint");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("udid", "mint");
        params.put("email", username);
        params.put("password", password);

        aq.ajax(apiUrl, params, JSONObject.class, cb);

    }

    public void LoginCallback(String url, JSONObject obj, AjaxStatus status) throws Exception {

        switch (status.getCode()) {
            case 201:
                Intent intent = new Intent(this, ViewAccount.class);

                String token = obj.getString("token");

                intent.putExtra("token", token);

                startActivity(intent);

                //Toast.makeText(this, token, Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, status.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
