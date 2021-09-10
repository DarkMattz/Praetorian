package personal.febry.bcpraetorian;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import personal.febry.bcpraetorian.data.UserData;


public class SignUpFragment extends Fragment {

    private FirebaseAuth userAuth;
    private TextInputLayout etName, etEmail, etPassword, etConfirmPassword;
    private TextView tvError;
    private Button btnSignUp;

    public SignUpFragment(){
        super(R.layout.fragment_sign_up);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignAll(view);
        btnSignUp.setOnClickListener(signUpListener());
    }

    private View.OnClickListener signUpListener() {
        return view -> {
            tvError.setVisibility(View.GONE);
            String name = etName.getEditText().getText().toString(),
                    email = etEmail.getEditText().getText().toString(),
                    password = etPassword.getEditText().getText().toString(),
                    confirmPassword = etConfirmPassword.getEditText().getText().toString();
            String error = checkCorrectness(name, email, password, confirmPassword);
            if(error == null){
                userAuth = FirebaseAuth.getInstance();
                userAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), task -> {
                            if(task.isSuccessful()){
                                UserData user = updateProfile(name);
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
            } else {
                tvError.setText(error);
                tvError.setVisibility(View.VISIBLE);
            }
        };
    }

    private UserData updateProfile(String name) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateProfile((new UserProfileChangeRequest.Builder()).setDisplayName(name).build());
        return new UserData(user.getDisplayName(), user.getUid(), user.getEmail());
    }

    private String checkCorrectness(String name, String email, String password, String confirmPassword) {
        if(name.length() < 5){
            return "Name must be at least 5 character";
        } else if(!email.contains("@") && !email.endsWith(".com")){
            return "Email must contains @ and ends with .com";
        } else if (!password.equals(confirmPassword) && password.length() > 0){
            return "Password and confirm password must be same";
        }
        return null;
    }

    private void assignAll(View view) {
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        tvError = view.findViewById(R.id.tv_error);
    }
}