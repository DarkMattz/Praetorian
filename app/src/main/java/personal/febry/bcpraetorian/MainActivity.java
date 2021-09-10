package personal.febry.bcpraetorian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import personal.febry.bcpraetorian.data.UserData;

public class MainActivity extends AppCompatActivity {

    private boolean backPressed = false;
    UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.ab_mainactivity));
        assignAll();
    }

    private void assignAll() {
        user = (UserData) getIntent().getSerializableExtra("USER");

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