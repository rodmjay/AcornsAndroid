package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ViewAccounts extends Activity {

    private AQuery aq;
    private String Token;
    private static final String AccountSummaryUrl = "https://api.acorns.com/v1/account_summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accounts);
        aq = new AQuery(this);
        Intent intent = getIntent();
        Token = intent.getStringExtra("token");
       // Toast.makeText(this, Token, Toast.LENGTH_LONG).show();

        SetupGrid();

    }

    public void SetupGrid(){

        String url = AccountSummaryUrl + "?token=" + this.Token;

        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.weakHandler(this, "AccountsCallback");
        cb.url(url);

        cb.header("X-Client-App", "mint");
        cb.header("X-Client-Build", "mint");
        cb.header("X-Client-Os", "mint");
        cb.header("X-Client-Platform", "mint");
        cb.header("User-Agent", "mint");

        aq.ajax(url, JSONObject.class, cb);
    }

    public void AccountsCallback(String url, JSONObject obj, AjaxStatus status) throws Exception {

        JSONArray array = obj.getJSONArray("investments");

        AccountModel[] models = new AccountModel[array.length()];

        for(int i = 0; i<array.length(); i++){
            JSONObject subObj = array.getJSONObject(i);
            AccountModel model = new AccountModel();
            model.AccountName = subObj.getString("id");

            models[i] = model;
        }

        GridView gridview = (GridView)findViewById(R.id.gridView);
        gridview.setAdapter(new AccountAdapter(this,this.getLayoutInflater(),this, models));
    }
}
