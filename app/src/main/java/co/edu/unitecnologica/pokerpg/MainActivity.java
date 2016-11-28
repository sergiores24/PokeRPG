package co.edu.unitecnologica.pokerpg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jugar = new Intent();
                jugar.setClass(getApplicationContext(), PlayActivity.class);
                jugar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                jugar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                jugar.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(jugar);
            }
        };
        findViewById(R.id.start).setOnClickListener(listener);
    }
}
