package com.example.nurulislam.gallery.INTRFACE;

import com.example.nurulislam.gallery.POJO.Gallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * This is Created by Nurul Islam Tipu on 12/13/2018
 */
public interface GalleryApi {
    @GET()
    Call<Gallery> getGalleryUrl(@Url String urlString);
}
