package com.hoshigaki.angel.menutacos.Apis;

import com.hoshigaki.angel.menutacos.models.MenuRespuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("vermenus.php")
    Call<MenuRespuesta>obtenerListaMenus();

}
