package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean backPressed = false;
    private ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.ab_mainactivity));
        assignAll();
        onClickListener();
    }

    private void onClickListener() {
        imgUser.setOnClickListener(imgUserListener());
    }

    private View.OnClickListener imgUserListener() {
        return view -> {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        };
    }

    private void assignAll() {
        imgUser = findViewById(R.id.img_user);
    }

    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press \"BACK\" again to exit the application", Toast.LENGTH_SHORT).show();
            backPressed = true;
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                backPressed = false;
            }, "DoublePress Thread").start();
        }
    }
}