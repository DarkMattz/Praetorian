package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import personal.febry.bcpraetorian.data.UserData;

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
            UserData user = updateUser();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            finish();
        }
    }

    private UserData updateUser() {
        FirebaseUser user = userAuth.getCurrentUser();
        return new UserData(user.getDisplayName(), user.getUid(), user.getEmail());
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