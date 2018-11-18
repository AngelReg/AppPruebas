package com.hoshigaki.angel.menutacos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoshigaki.angel.menutacos.models.Menus;

import java.util.ArrayList;

public class ListaMenuAdapter
        extends RecyclerView.Adapter<ListaMenuAdapter.ViewHolder>
        implements View.OnClickListener {

    private ArrayList<Menus> dataset;
    private Context context;
    private View.OnClickListener listener;  //seleccion

    public ListaMenuAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menus, viewGroup,false);
        view.setOnClickListener(this); //escuchar el evento de seleccion
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menus p = dataset.get(position);
        holder.nombrenenu.setText(p.getNombre());
        holder.descripcionmenu.setText(p.getDescripcion());

        Glide.with(context)
                .load("http://192.168.43.21/bdmenu/"+p.getRuta())
                .into(holder.fotoImage);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaMenu(ArrayList<Menus> listaMenus) {
        dataset.addAll(listaMenus);
        notifyDataSetChanged();
    }
    //seleccion de objeto
    public void setOnClickListener(View.OnClickListener listener){
        this.listener= listener;
    }

    //formando cuerpo de la selecion
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotoImage;
        private TextView nombrenenu;
        private TextView descripcionmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoImage = (ImageView) itemView.findViewById(R.id.fotoImage);
            nombrenenu = (TextView) itemView.findViewById(R.id.nombremenu);
            descripcionmenu = (TextView) itemView.findViewById(R.id.descripcionmenu);

        }
    }

}
