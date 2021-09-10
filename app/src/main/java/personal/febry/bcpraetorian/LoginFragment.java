package personal.febry.bcpraetorian;

import android.content.Intent;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import personal.febry.bcpraetorian.data.UserData;

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
                                UserData user = updateProfile();
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                intent.putExtra("USER", user);
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

    private UserData updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return new UserData(user.getDisplayName(), user.getUid(), user.getEmail());
    }

    private void assignAll(View view) {
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvError = view.findViewById(R.id.tv_error);
    }
}