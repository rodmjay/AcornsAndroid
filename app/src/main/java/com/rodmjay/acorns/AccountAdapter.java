package com.rodmjay.acorns;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Rod on 3/19/2015.
 */
public class AccountAdapter extends BaseAdapter {

    private Context Context;
    private LayoutInflater Inflater;
    private Activity Activity;
    private TransactionModel[] Model;

    public AccountAdapter(Context context, LayoutInflater inflater, Activity activity, TransactionModel[] model) {
        this.Context = context;
        this.Inflater = inflater;
        this.Activity = activity;
        this.Model = model;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {

            gridView = new View(this.Context);

            // get layout from account_item
            gridView = Inflater.inflate(R.layout.account_item, null);

            // set value into textview
            TextView accountTextView = (TextView) gridView.findViewById(R.id.accountNameTextView);
            accountTextView.setText(Model[position].AccountName);

        } else {
            gridView = (View) convertView;
        }

        return gridView;

    }

    @Override
    public int getCount() {
        return Model.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
