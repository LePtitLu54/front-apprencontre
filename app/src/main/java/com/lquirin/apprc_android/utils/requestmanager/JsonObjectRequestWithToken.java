package com.lquirin.apprc_android.utils.requestmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;


import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lquirin.apprc_android.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class JsonObjectRequestWithToken extends JsonObjectRequest {

    Context context;

    public JsonObjectRequestWithToken(Context context, int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.context = context;
    }

    @Override
    public Map<String, String> getHeaders() {
        SharedPreferences preference = context.getSharedPreferences(
                context.getResources().getString(R.string.fichier_preference), 0); // 0 - for private mode
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json; charset=UTF-8");
        params.put("Authorization", "Bearer " + preference.getString("token",""));
        return params;
    }
}
