package co.edu.unitecnologica.pokerpg;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends AppCompatActivity {
    private ImageLoader imageLoader;
    NetworkImageView img1, img2;
    TextView tx1,tx2,vd1,vd2;
    Button attack;
    Boolean rd1=false,rd2=false;
    int vida1=100,vida2=100;
    Random rand = new Random();
    public final String url = "http://pokeapi.co/api/v2/pokemon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RequestQueue queue = Volley.newRequestQueue(this);
        int pokemon1, pokemon2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        attack=(Button) findViewById(R.id.Atacar);
        img1 = (NetworkImageView) findViewById(R.id.pk1);
        img2 = (NetworkImageView) findViewById(R.id.pk2);
        tx1 = (TextView) findViewById(R.id.textView1);
        tx2 = (TextView) findViewById(R.id.textView2);
        vd1 = (TextView) findViewById(R.id.textView3);
        vd2 = (TextView) findViewById(R.id.textView4);
        pokemon1 = rand.nextInt(721) + 1;
        pokemon2 = rand.nextInt(721) + 1;
        while (pokemon1 == pokemon2)
            pokemon2 = rand.nextInt(721) + 1;
        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url + String.valueOf(pokemon1)+"/",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadImage(img1,response.getJSONObject("sprites").getString("back_default"));
                            tx1.setText(response.getString("name"));
                            vd1.setText("Vida: "+String.valueOf(vida1));
                            rd1=true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        JsonObjectRequest getRequest2 = new JsonObjectRequest(Request.Method.GET, url + String.valueOf(pokemon2)+"/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadImage(img2,response.getJSONObject("sprites").getString("front_default"));
                            tx2.setText(response.getString("name"));
                            vd2.setText("Vida: "+String.valueOf(vida2));
                            rd2=true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        attack.setEnabled(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attack.setEnabled(false);
                vida2=vida2-rand.nextInt(51);
                if(vida2<=0){
                    vd2.setText("Perdedor");
                    vd1.setText("Ganador");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent principal = new Intent();
                            principal.setClass(getApplicationContext(), MainActivity.class);
                            principal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            principal.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(principal);
                        }
                    }, 2500);
                } else {
                    vd2.setText("Vida: "+String.valueOf(vida2));
                    turnoMaquina();}
            }
        };
        queue.add(getRequest1);
        queue.add(getRequest2);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask()
        {
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (rd1 && rd2) {
                            attack.setEnabled(true);
                            timer.cancel();
                        }
                    }
                });
            }
        }, 0, 500);
        findViewById(R.id.Atacar).setOnClickListener(listener);
    }

    private void turnoMaquina(){
        vida1=vida1-rand.nextInt(51);
        if(vida1<=0){
            vd1.setText("Perdedor");
            vd2.setText("Ganador");
            Intent principal = new Intent();
            principal.setClass(getApplicationContext(), MainActivity.class);
            principal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            principal.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent principal = new Intent();
                    principal.setClass(getApplicationContext(), MainActivity.class);
                    principal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    principal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    principal.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(principal);
                }
            }, 5000);
            startActivity(principal);
        } else {
            vd1.setText("Vida: "+String.valueOf(vida1));
            attack.setEnabled(true);
        }
    }

    private void loadImage(NetworkImageView imageView, String urlImg){
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(urlImg, ImageLoader.getImageListener(imageView,
                R.drawable.white, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(urlImg, imageLoader);
    }
}