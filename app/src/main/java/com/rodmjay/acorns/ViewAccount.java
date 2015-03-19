package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class ViewAccount extends Activity {

    private static final String AccountSummaryUrl = "https://api.acorns.com/v1/account_summary";
    private AQuery aq;
    private String Token;

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

    public void SetupGrid() {

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

        TransactionModel[] models = new TransactionModel[array.length()];

        for (int i = 0; i < array.length(); i++) {
            JSONObject subObj = array.getJSONObject(i);
            TransactionModel model = new TransactionModel();
            model.AccountName = subObj.getString("id");

            models[i] = model;
        }

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new AccountAdapter(this, this.getLayoutInflater(), this, models));

        JSONObject accountObject = obj.getJSONObject("user");

        String firstName = accountObject.getString("first_name");
        String lastName = accountObject.getString("last_name");
        double amount = accountObject.getDouble("account_balance");

        AccountModel model = new AccountModel(firstName, lastName, amount);

        TextView userNameLabel = (TextView)findViewById(R.id.userNameLabel);
        userNameLabel.setText(model.FirstName + " " + model.LastName);

        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView accountBalanceLabel = (TextView)findViewById(R.id.totalAmountLabel);
        accountBalanceLabel.setText(formatter.format(model.AccountBalance));
    }
}
