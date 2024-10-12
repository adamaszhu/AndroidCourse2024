package com.adamas.androidcourse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UniversityService {

    @GET("search?country=Australia")
    Call<List<University>> getUniversities();
}
