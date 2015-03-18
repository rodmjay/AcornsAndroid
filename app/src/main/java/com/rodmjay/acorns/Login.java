package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.view.View.OnClickListener;

public class Login extends Activity {

    EditText EmailText;
    EditText PasswordText;
    Button LoginButton;

    public final static String apiUrl = "https://api.acorns.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button)findViewById(R.id.login);
        EmailText = (EditText)findViewById(R.id.username);
        PasswordText = (EditText)findViewById(R.id.username);

        SetupUiEvents();
    }

    void SetupUiEvents(){

        LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleLoginClick((Button)v);
            }
        });
    }

    void handleLoginClick(Button button){

      String username = EmailText.getText().toString();
      String password = PasswordText.getText().toString();

        if(username != null && !username.isEmpty()){

            new CallAPI().execute(apiUrl);

        }

      Intent intent = new Intent(this, ViewAccounts.class);
        startActivity(intent);
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

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0];

            String resultToDisplay = "";

            InputStream in = null;

            try{
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch(Exception e){
                System.out.println(e.getMessage());

                return e.getMessage();
            }

            return resultToDisplay;
        }

        protected void onPostExecute(String result){

        }
    }

    private class LoginResult {
        public String Token;
        public String PersistentToken;
    }
}
