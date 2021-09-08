package personal.febry.bcpraetorian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity {

    TextView tvQuestion, tvContext;
    ImageView imgClose;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        assignAllView();
        imgClose.setOnClickListener(closeListener());
    }

    private View.OnClickListener closeListener() {
        return view -> startActivity(new Intent(this, StartActivity.class));
    }

    private void assignAllView() {
        imgClose = findViewById(R.id.image_close);
        tvQuestion = findViewById(R.id.tv_question);
        tvContext = findViewById(R.id.tv_context);
        fragmentHandling();
    }

    private void fragmentHandling() {
        switch(getIntent().getExtras().getInt("IS_LOGIN")){
            case 0:
                //Commit sign up Fragment
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.input_fragment_view, SignUpFragment.class, null)
                        .commit();
                //View adjustment for sign up
                tvContext.setText("Sign Up");
                SpannableString loginString = new SpannableString("Already have an account? Login");
                ClickableSpan clickLogin = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        //Change fragment
                        getIntent().putExtra("IS_LOGIN", 1);
                        fragmentHandling();
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(getResources().getColor(R.color.pepe));
                    }
                };
                loginString.setSpan(
                        clickLogin,
                        loginString.length() - ("Login").length(),
                        loginString.length(),
                        0
                );
                tvQuestion.setText(loginString);
                tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
                tvQuestion.setHighlightColor(Color.TRANSPARENT);
                break;
            case 1:
                //Login Fragment
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.input_fragment_view, LoginFragment.class, null)
                        .commit();
                //View adjustment for login
                tvContext.setText("Login");
                SpannableString signUpString = new SpannableString("Don't have an account? Sign Up");
                ClickableSpan clickSignUp = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        //Change fragment
                        getIntent().putExtra("IS_LOGIN", 0);
                        fragmentHandling();
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(getResources().getColor(R.color.pepe));
                    }
                };
                signUpString.setSpan(
                        clickSignUp,
                        signUpString.length() - ("Sign Up").length(),
                        signUpString.length(),
                        0
                );
                tvQuestion.setText(signUpString);
                tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
                tvQuestion.setHighlightColor(Color.TRANSPARENT);
                break;
        }
    }

}