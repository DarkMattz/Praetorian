package personal.febry.bcpraetorian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private TextInputLayout etEmail, etPassword;
    private Button btnLogin;
    private TextView tvError;

    public LoginFragment(){
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignAll(view);
        btnLogin.setOnClickListener(loginListener());
    }

    private View.OnClickListener loginListener() {
        return view -> {
            tvError.setVisibility(View.GONE);
            FirebaseAuth userAuth = FirebaseAuth.getInstance();
            userAuth.signInWithEmailAndPassword(etEmail.getEditText().getText().toString(), etPassword.getEditText().getText().toString())
                        .addOnCompleteListener(requireActivity(), task -> {
                            if(task.isSuccessful()){
                                updateProfile();
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                tvError.setText(task.getException().getMessage());
                                tvError.setVisibility(View.VISIBLE);
                            }
                        });
        };
    }

    private void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences spUser = requireActivity().getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spUser.edit();
        editor.putString("Name", user.getDisplayName());
        editor.putString("Email", user.getEmail());
        editor.putString("UID", user.getUid());
        editor.apply();
    }

    private void assignAll(View view) {
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvError = view.findViewById(R.id.tv_error);
    }
}