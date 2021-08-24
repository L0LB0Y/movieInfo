package com.example.moviesinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.IDataStore;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.example.moviesinfo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String API_HOST = "https://api.backendless.com";
    private static final String APP_ID = "4E6CE625-786C-CC04-FF9A-CBFAFA87D000";
    private static final String APP_KEY = "43BA1EA9-4E8D-452E-BEE0-189ED8307847";
    private static final String TAG = "RTDatabase";
    private MoviesAdapter adapter;
    private IDataStore<Movies> moviesStore;
    private ArrayList<Movies> moviesList;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data !!");
        progressDialog.show();
        Backendless.initApp(MainActivity.this, APP_ID, APP_KEY);
        moviesStore = Backendless.Data.of(Movies.class);
        initUI();
        initBackendless();
        enableRealTime();
        getMovies();
    }

    private void initBackendless() {
        Backendless.setUrl(API_HOST);
        Backendless.initApp(this, APP_ID, APP_KEY);
    }

    private void enableRealTime() {
        EventHandler<Movies> rtHandlers = moviesStore.rt();

        rtHandlers.addCreateListener(new AsyncCallback<Movies>() {
            @Override
            public void handleResponse(Movies response) {
                moviesList.add(response);
                adapter.setMovies(moviesList);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, fault.getMessage());
            }
        });

        rtHandlers.addUpdateListener(new AsyncCallback<Movies>() {
            @Override
            public void handleResponse(Movies response) {
                for (int i = 0; i < moviesList.size(); i++) {
                    if (moviesList.get(i).getObjectId().equals(response.getObjectId())) {
                        moviesList.set(i, response);
                    }
                }
                adapter.setMovies(moviesList);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, fault.getMessage());
            }
        });

        rtHandlers.addDeleteListener(new AsyncCallback<Movies>() {
            @Override
            public void handleResponse(Movies response) {
                Iterator<Movies> iterator = moviesList.iterator();
                while (iterator.hasNext()) {
                    Movies movie = iterator.next();
                    if (movie.getObjectId().equals(response.getObjectId())) {
                        iterator.remove();
                    }
                }
                adapter.setMovies(moviesList);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(TAG, fault.getMessage());
            }
        });
    }

    private void getMovies() {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(100);
        queryBuilder.setSortBy("created");
        moviesStore.find(queryBuilder, new AsyncCallback<List<Movies>>() {
            @Override
            public void handleResponse(List<Movies> response) {
                progressDialog.dismiss();
                moviesList = new ArrayList<>(response);
                adapter.setMovies(moviesList);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MainActivity.this,
                        "Error occurred: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        binding.moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.moviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MoviesAdapter(getLayoutInflater());
        binding.moviesRecyclerView.setAdapter(adapter);
    }


}