package com.hoshigaki.angel.menutacos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hoshigaki.angel.menutacos.Apis.APIService;
import com.hoshigaki.angel.menutacos.models.MenuRespuesta;
import com.hoshigaki.angel.menutacos.models.Menus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MENU";

    private Retrofit retrofit;

    private RecyclerView recyclerView;

    private ListaMenuAdapter listaMenuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.listamenu);
        listaMenuAdapter = new ListaMenuAdapter(this);

        //seleccion de datos
        listaMenuAdapter.setOnClickListener(new View.OnClickListener() {
            //implementacion del metodo contruido para la seleccion
            @Override
            public void onClick(View view) {
                //se selecciona?
                Toast.makeText(getApplicationContext(),"Has Seleccionado "+ recyclerView.getChildAdapterPosition(view),Toast.LENGTH_LONG).show();
            }
        });
        //seleccion de datos

        recyclerView.setAdapter(listaMenuAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));//orientacion de la lista

        retrofit=new Retrofit.Builder()
                .baseUrl("http://localhost/bdmenu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerdatos();

    }

    private void obtenerdatos() {
        APIService service = retrofit.create(APIService.class);
        Call<MenuRespuesta> MenusRespuestaCall = service.obtenerListaMenus();

        MenusRespuestaCall.enqueue(new Callback<MenuRespuesta>() {
            @Override
            public void onResponse(Call<MenuRespuesta> call, Response<MenuRespuesta> response) {
                if (response.isSuccessful()){

                    MenuRespuesta menuRespuesta = response.body();
                    ArrayList<Menus> listaMenus = menuRespuesta.getMenus();

                    listaMenuAdapter.adicionarListaMenu(listaMenus);

                }else{
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MenuRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure" + t.getMessage());
            }
        });
    }
}
