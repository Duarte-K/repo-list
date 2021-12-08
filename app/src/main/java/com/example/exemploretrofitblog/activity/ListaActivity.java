package com.example.exemploretrofitblog.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.exemploretrofitblog.Inter;
import com.example.exemploretrofitblog.Model.Repositorio;
import com.example.exemploretrofitblog.Model.Usuario;
import com.example.exemploretrofitblog.R;
import com.example.exemploretrofitblog.ServiceGenerator;
import com.example.exemploretrofitblog.adapter.AdapterRepositorio;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.exemploretrofitblog.adapter.AdapterRepositorio;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private TextView textUsuario;
    private ImageView fotinha;
    /*private static HorizontalScrollView horizontalScrollView;
    private static ImageButton Button_esquerda;
    private static ImageButton Button_direita;
    private static int displayWidth = 0;
    private int arrowWidth = 0;*/
    public static final String TAG = "haha";
    private int noOfBtns;
    private Button[] btns;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        recyclerView = findViewById(R.id.recyclerView);
        textUsuario = findViewById(R.id.textUsuario);
        fotinha = findViewById(R.id.Fotinha);
        /*horizontalScrollView = findViewById(R.id.scrollHori);
        Button_esquerda = findViewById(R.id.Button_esquerda);
        Button_direita = findViewById(R.id.Button_direita);
        for (int i = 0; i < 15; i++) {
            ImageButton button = new ImageButton(this);
            button.setTag(i);
            linearLayout.addView(button);
        }
        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = linearLayout.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                Display display = getWindowManager().getDefaultDisplay();
                displayWidth = display.getWidth();
                if (linearLayout.getMeasuredWidth() > (displayWidth - 40)) {
                    Button_esquerda.setVisibility(View.VISIBLE);
                    Button_direita.setVisibility(View.VISIBLE);
                }
            }

        });
        Button_esquerda.setOnClickListener(listnerLeftArrowButton);
        horizontalScrollView.setOnTouchListener(listenerScrollViewTouch);*/

        //Recuperar dados
        Bundle dados = getIntent().getExtras();
        String user = dados.getString("user");
        Usuario usuario = (Usuario) dados.getSerializable("usuario");

        //setar valores
        textUsuario.setText(usuario.getName());

        //Fotinha
        Glide.with(this.getApplicationContext())
                .load(usuario.getAvatar_url())
                .into(fotinha);

        //Configurar recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        retrofitRepo(user, 1);
        Btnfooter(usuario.getPublic_repos(), user);
    }

    public void retrofitRepo(String user, int page){

        Inter service = ServiceGenerator.createService(Inter.class);

        Call<ArrayList<Repositorio>> call = service.repos(user, page);

        call.enqueue(new Callback<ArrayList<Repositorio>>() {
            @Override
            public void onResponse(Call<ArrayList<Repositorio>> call, Response<ArrayList<Repositorio>> response) {

                if (response.isSuccessful()) {

                    ArrayList<Repositorio> repositorio = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (repositorio != null) {
                        AdapterRepositorio adapter = new AdapterRepositorio(repositorio, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ResponseBody errorBody = response.errorBody();
                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();

                    // segura os erros de requisição
                    System.out.print(errorBody);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Repositorio>> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Btnfooter(int quant_repo, final String nome) {

        int val = quant_repo % 30;
        val = val==0?0:1;
        noOfBtns=quant_repo / 30 +val;
        
        LinearLayout ll = findViewById(R.id.paginas);

        btns =new Button[noOfBtns];

        for(int i=0;i<noOfBtns;i++)
        {
            btns[i] =   new Button(this);
            btns[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btns[i].setText(""+(i+1));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(btns[i], lp);

            final int j = i;

            btns[j].setOnClickListener(new View.OnClickListener() {

                public void onClick(View v)
                {
                   retrofitRepo(nome, (j+1));
                }
            });
        }

    }
    /*private View.OnTouchListener listenerScrollViewTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            showHideViews();
            return false;
        }
    };

    private View.OnClickListener listnerLeftArrowButton = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            horizontalScrollView.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(0, 0));
        }
    };
    public static void showHideViews() {
        int maxScrollX = horizontalScrollView.getChildAt(0).getMeasuredWidth()- displayWidth;
        Log.e("TestProjectActivity", "scroll X = " +horizontalScrollView.getScrollX() );
        Log.i("TestProjectActivity", "scroll Width = " +horizontalScrollView.getMeasuredWidth() );
        Log.d("TestProjectActivity", "Max scroll X = " + maxScrollX);

        if (horizontalScrollView.getScrollX() == 0) {
            hideLeftArrow();
        } else {
            showLeftArrow();
        }
        if (horizontalScrollView.getScrollX() == maxScrollX) {
            showRightArrow();
        } else {
            hideRightArrow();
        }
    }
    private static void hideLeftArrow() {
        Button_esquerda.setVisibility(View.GONE);
    }

    private static void showLeftArrow() {
        Button_esquerda.setVisibility(View.VISIBLE);
    }

    private static void hideRightArrow() {
        Button_direita.setVisibility(View.GONE);
    }

    private static void showRightArrow() {
        Button_direita.setVisibility(View.VISIBLE);
    }*/
}
