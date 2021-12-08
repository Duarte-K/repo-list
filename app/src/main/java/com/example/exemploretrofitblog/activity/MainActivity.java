package com.example.exemploretrofitblog.activity;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.exemploretrofitblog.Model.Usuario;
import com.example.exemploretrofitblog.R;
import com.example.exemploretrofitblog.ServiceGenerator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private Button btnPesquisar;
    private TextInputEditText textUser;
    public static final String TAG = "haha";
    private ProgressDialog progress;
    Usuario usu = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPesquisar = findViewById(R.id.btnPesquisar);
        textUser = findViewById(R.id.textUser);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = textUser.getText().toString();
                if (user == null || user.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo não preenchido", Toast.LENGTH_SHORT).show();
                }else{
                    progress = new ProgressDialog(MainActivity.this);
                    progress.setTitle("enviando...");
                    progress.show();
                    retrofitUser();

                }
            }
        });
    }

        public void retrofitUser(){

            final String user = textUser.getText().toString();
            ServiceGenerator.RetrofitUser service = ServiceGenerator.createService(ServiceGenerator.RetrofitUser.class);

            Call<Usuario> call = service.buscarUser(user);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    if (response.isSuccessful()) {

                        Usuario usuario = response.body();

                        //verifica aqui se o corpo da resposta não é nulo
                        if (usuario != null) {
                            usu.setName(usuario.getName());
                            usu.setPublic_repos(usuario.getPublic_repos());
                            usu.setAvatar_url(usuario.getAvatar_url());
                            progress.dismiss();
                            Intent intent = new Intent(getApplicationContext(), ListaActivity.class);

                            //passando dados
                            intent.putExtra("usuario", usu);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        ResponseBody errorBody = response.errorBody();
                        Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                        // segura os erros de requisição
                        System.out.print(errorBody);
                    }
                    progress.dismiss();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e(TAG, "Erro: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            });

        }


    }

