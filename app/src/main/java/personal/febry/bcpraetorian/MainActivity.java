package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private boolean backPressed = false;
    private ImageView imgUser;
    private StorageReference storage;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.ab_mainactivity));
        assignAll();
        onClickListener();
        uploadTest();
    }

    private void uploadTest() {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String extension = mime.getExtensionFromMimeType(null);
    }

    private void onClickListener() {
        imgUser.setOnClickListener(imgUserListener());
        btnAdd.setOnClickListener(addListener());
    }

    private View.OnClickListener addListener() {
        return view -> {
            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("IS_ADD", 1);
            startActivity(intent);
        };
    }

    private View.OnClickListener imgUserListener() {
        return view -> {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        };
    }

    private void assignAll() {
        imgUser = findViewById(R.id.img_user);
        btnAdd = findViewById(R.id.btn_add);
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