package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //For better display, the app will be locked into portrait mode.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        assignAllView();
        btnSignUp.setOnClickListener(btnSignUpListener());
        btnLogin.setOnClickListener(btnLoginListener());
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

    private void assignAllView() {
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
    }
}