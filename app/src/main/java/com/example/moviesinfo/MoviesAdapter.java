package com.example.moviesinfo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesinfo.databinding.ItemMovieBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    LayoutInflater layoutInflater;

    ArrayList<Movies> movies = new ArrayList<>();
    SimpleDateFormat dateFormat;

    public MoviesAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        dateFormat = new SimpleDateFormat("E MMM d yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(layoutInflater);
        return new MoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movies movie = movies.get(position);
        holder.binding.descriptionTxt.setText(movie.getDescription());
        holder.binding.ratingTxt.setText(movie.getRating());
        holder.binding.releaseYearTxt.setText(movie.getReleaseYear());
        holder.binding.titleTxt.setText(movie.getTitle());
        holder.binding.addedTxt.setText("added ".concat(dateFormat.format(movie.getCreated())));

    }
    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {


        public ItemMovieBinding binding;

        public MoviesViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
