package com.example.exemploretrofitblog;

import com.example.exemploretrofitblog.Model.Repositorio;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Inter {
    @GET("{user}/repos?")
    Call<ArrayList<Repositorio>> repos(@Path("user") String user, @Query("page") int page);

}
