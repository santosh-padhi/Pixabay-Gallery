package com.spadhi.pixabaygallery.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

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


public class MainActivity extends AppCompatActivity {
    RequestQueue mRequestQueue;
    List<Image> listOfImages=new ArrayList<>();
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.homePageRV);
        sendAndRequestResponse();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void sendAndRequestResponse() {
        //RequestQueue initialized
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        mRequestQueue = Volley.newRequestQueue(this);
        String url = Constants.baseUrl + "&q=yellow+flowers";
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("hits");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listOfImages.add(ImageJsonParser.convertJSONObjectToImageObject(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error Parsing Data from API", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            imageAdapter = new ImageAdapter(listOfImages, MainActivity.this);
            recyclerView.setAdapter(imageAdapter);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            imageAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }, error -> {
            progressDialog.cancel();
            Toast.makeText(MainActivity.this, "Error Occurred while Getting Images", Toast.LENGTH_SHORT).show();
        });
        mRequestQueue.add(jsonObjectRequest);
    }
}