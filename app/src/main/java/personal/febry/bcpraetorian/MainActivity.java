package personal.febry.bcpraetorian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import personal.febry.bcpraetorian.data.ImageData;

public class MainActivity extends AppCompatActivity implements MainRVAdapter.ClickListener {

    private boolean backPressed = false;
    private ImageView imgUser;
    private FloatingActionButton btnAdd;

    private RecyclerView rvLayout;
    private MainRVAdapter rvAdapter;

    private DatabaseReference database;
    private List<ImageData> imageDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.ab_mainactivity));
        assignAll();
        onClickListener();
        database.addValueEventListener(valueListener());
    }

    private ValueEventListener valueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i : snapshot.getChildren()){
                    ImageData image = i.getValue(ImageData.class);
                    imageDatas.add(image);
                }
                rvAdapter = new MainRVAdapter(MainActivity.this, imageDatas, MainActivity.this);
                rvLayout.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
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
        rvLayout = findViewById(R.id.rv_contents);
        rvLayout.setHasFixedSize(true);
        rvLayout.setLayoutManager(new LinearLayoutManager(this));
        imageDatas = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://praetorian-23516-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("emojis");
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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("Name",imageDatas.get(position).getName());
        intent.putExtra("Author",imageDatas.get(position).getAuthor());
        intent.putExtra("Description",imageDatas.get(position).getDescription());
        intent.putExtra("URL",imageDatas.get(position).getUrl());
        startActivity(intent);
    }
}