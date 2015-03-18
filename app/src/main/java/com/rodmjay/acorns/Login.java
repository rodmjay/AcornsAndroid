package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
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
        PasswordText = (EditText) findViewById(R.id.username);

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

        cb.url(apiUrl).type(JSONObject.class).weakHandler(this, "jsonCb");

        cb.header("X-Client-App", "mint");
        cb.header("X-Client-Build", "mint");
        cb.header("X-Client-Os", "mint");
        cb.header("X-Client-Platform", "mint");
        cb.header("User-Agent", "mint");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("udid", "mint");
        params.put("email", "shelby.rae.801@gmail.com");
        params.put("password", "Macu1234");

        aq.ajax(apiUrl, params, JSONObject.class, cb);

    }

    public void jsonCb(String url, JSONObject obj, AjaxStatus status) throws Exception {

        switch (status.getCode()) {
            case 201:
                Intent intent = new Intent(this, ViewAccounts.class);

                String token = obj.getString("token");

                intent.putExtra("token", token);

                startActivity(intent);

                //Toast.makeText(this, token, Toast.LENGTH_LONG).show();

            default:
                Toast.makeText(this, status.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
