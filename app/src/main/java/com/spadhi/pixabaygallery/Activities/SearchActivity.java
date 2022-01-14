package com.spadhi.pixabaygallery.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.spadhi.pixabaygallery.Adapters.ImageAdapter;
import com.spadhi.pixabaygallery.Helper.Constants;
import com.spadhi.pixabaygallery.Helper.ImageJsonParser;
import com.spadhi.pixabaygallery.Models.Image;
import com.spadhi.pixabaygallery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RequestQueue mRequestQueue;
    List<Image> listOfImages=new ArrayList<>();
    RecyclerView recyclerView;
    EditText searchEditText;
    Button searchButton;
    ImageAdapter imageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView=findViewById(R.id.searchPageRV);
        searchEditText=findViewById(R.id.searchET);
        searchButton=findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> {
            listOfImages.clear();
            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            String query=searchEditText.getText().toString().trim();
            if(query.isEmpty()){
                Toast.makeText(SearchActivity.this, "Please Enter Something in Search Field", Toast.LENGTH_SHORT).show();
            }else{
                searchForQuery(query);
            }
        });
    }

    private void searchForQuery(String query) {
        //RequestQueue initialized
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mRequestQueue = Volley.newRequestQueue(this);
        String url = Constants.baseUrl + "&q="+query;

        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("hits");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listOfImages.add(ImageJsonParser.convertJSONObjectToImageObject(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SearchActivity.this, "Error Parsing Data from API", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            imageAdapter = new ImageAdapter(listOfImages, SearchActivity.this);
            recyclerView.setAdapter(imageAdapter);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            imageAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }, error -> {
            progressDialog.cancel();
            Toast.makeText(SearchActivity.this, "Error Occurred while Getting Images", Toast.LENGTH_SHORT).show();
        });
        mRequestQueue.add(jsonObjectRequest);
    }
}