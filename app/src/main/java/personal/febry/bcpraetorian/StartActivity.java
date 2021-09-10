package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartActivity extends AppCompatActivity {

    private Button btnLogin, btnSignUp;
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        assignAll();
        instantLogin();
        setOnclickListener();
    }

    private void setOnclickListener() {
        btnSignUp.setOnClickListener(btnSignUpListener());
        btnLogin.setOnClickListener(btnLoginListener());
    }

    private void instantLogin() {
        if(userAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private View.OnClickListener btnLoginListener() {
        return view -> {
            Intent authentication = new Intent(this, AuthActivity.class);
            authentication.putExtra("IS_LOGIN", 1);
            startActivity(authentication);
        };
    }

    private View.OnClickListener btnSignUpListener() {
        return view -> {
            Intent authentication = new Intent(this, AuthActivity.class);
            authentication.putExtra("IS_LOGIN", 0);
            startActivity(authentication);
        };
    }

    private void assignAll() {
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);
        userAuth = FirebaseAuth.getInstance();
    }
}