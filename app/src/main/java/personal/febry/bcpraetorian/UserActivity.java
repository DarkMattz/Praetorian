package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    private ImageView imgClose;
    private TextView tvName, tvEmail, tvUID;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        assignAll();
        changeValue();
        onClickListener();
    }

    private void onClickListener() {
        imgClose.setOnClickListener(closeListener());
        btnSignOut.setOnClickListener(signOutListener());
    }

    private View.OnClickListener signOutListener() {
        return view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        };
    }

    private View.OnClickListener closeListener() {
        return view -> super.onBackPressed();
    }

    private void changeValue() {
        SharedPreferences spUser = getSharedPreferences("user",Context.MODE_PRIVATE);
        tvName.setText(spUser.getString("Name", null));
        tvEmail.setText(spUser.getString("Email", null));
        tvUID.setText(spUser.getString("UID", null));
    }

    private void assignAll() {
        imgClose = findViewById(R.id.image_close);
        btnSignOut = findViewById(R.id.btn_sign_out);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvUID = findViewById(R.id.tv_UID);
    }
}