package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        assignAllWidget();
        btnLogin.setOnClickListener(btnLoginListener());
        btnSignUp.setOnClickListener(btnSignUpListener());
    }

    private View.OnClickListener btnSignUpListener() {
        return view -> {
            btnSignUp.setEnabled(false);
            btnLogin.setEnabled(true);

        };
    }

    private View.OnClickListener btnLoginListener() {
        return view -> {
            btnLogin.setEnabled(false);
            btnSignUp.setEnabled(true);

        };
    }

    private void assignAllWidget() {
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
}