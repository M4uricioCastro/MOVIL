package com.example.maury.saacloop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maury.saacloop.saac.CrudPictograma;
import com.example.maury.saacloop.saac.Pictograma;
import com.github.snowdream.android.widget.SmartImage;
import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ImagenesActivity extends AppCompatActivity {
    private String ip="170.239.85.176";
    private ListView lv;
    private int id;
    private TextView tvRespuesta;
    CrudPictograma crudPictograma;
    ArrayList nombre = new ArrayList();
    ArrayList descripcion = new ArrayList();
    ArrayList img = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);
        lv = findViewById(R.id.list_imagen);
        tvRespuesta = findViewById(R.id.tvrespuesta);
        crudPictograma = new CrudPictograma(this);
        cargaImagenes();

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("cod"));
        id++;
        Log.e("Categoria",id+"");
    }

    public void cargaImagenes(){
        String url = "http://"+ip+"/index.php/api/pictogramasporcategoria";
        List<Pictograma> pictoList = crudPictograma.pictogramaList();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        RequestParams params = new RequestParams();
        params.put("idCategoria", id);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    progressDialog.dismiss();
                    nombre.clear();
                    descripcion.clear();
                    img.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for(int i =0; i<jsonArray.length();i++){
                            nombre.add(jsonArray.getJSONObject(i).getString("Nombre"));
                            descripcion.add(jsonArray.getJSONObject(i).getString("Descripcion"));
                            img.add(jsonArray.getJSONObject(i).getString("img"));
                        }
                        lv.setAdapter(new ImagenAdapter(getApplicationContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressDialog.dismiss();
                MuestraImagenesOFF();
            }
        });
    }
    private  void MuestraImagenesOFF(){
        List<Pictograma> listPicto = crudPictograma.pictoCat(id);
        nombre.clear();
        descripcion.clear();
        img.clear();
        if (listPicto.isEmpty()){
            Log.e("Pictogramas vacios",listPicto.size()+"");
            tvRespuesta.setText("No hay imagenes");
            tvRespuesta.setVisibility(View.VISIBLE);
        }else{
            for(int i =0; i<listPicto.size();i++){
                nombre.add(listPicto.get(i).nombre);
                descripcion.add(listPicto.get(i).descripcion);
                img.add(listPicto.get(i).img);
            }
            lv.setAdapter(new ImagenAdapter(getApplicationContext()));
        }

    }
     private class ImagenAdapter extends BaseAdapter{
        Context ctx;
        LayoutInflater layoutInflater;
        SmartImageView smartImageView;
        TextView txtnombre, txtdescripcion;

         public ImagenAdapter(Context applicationContext) {
             this.ctx= applicationContext;
             layoutInflater= (LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
         }

         @Override
         public int getCount() {
             return img.size();
         }

         @Override
         public Object getItem(int position) {
             return position;
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.activity_imagenes_item,null);
             smartImageView = (SmartImageView) viewGroup.findViewById(R.id.imagenP);
             txtnombre = viewGroup.findViewById(R.id.tvNombre);
             txtdescripcion = viewGroup.findViewById(R.id.tvDescripcion);
             // en caso de que devuelva el nombre de la imagen con algo mas, aparte del .png, elimar lo extra que trae

             String urlfinal = "http://"+ip+"/"+img.get(position).toString();
             Log.e("Picto",urlfinal);
             Rect rect = new Rect(smartImageView.getLeft(),smartImageView.getTop(),smartImageView.getRight(),smartImageView.getBottom());
             smartImageView.setImageUrl(urlfinal ,rect);
             txtnombre.setText(nombre.get(position).toString());
             txtdescripcion.setText(descripcion.get(position).toString());
             return viewGroup;
         }
     }
}
