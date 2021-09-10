package personal.febry.bcpraetorian;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import personal.febry.bcpraetorian.data.ImageData;

public class AddFragment extends Fragment {

    private Uri imageUri;
    private ImageView addPhoto;
    private Button btnUpload;
    private TextInputLayout etName, etDescription;

    ImageData image = null;
    private DatabaseReference database;
    private StorageReference storage;

    public AddFragment() {
        // Required empty public constructor
        super(R.layout.fragment_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignAll(view);
        onClickListener();
    }

    private void onClickListener() {
        addPhoto.setOnClickListener(addListener());
        btnUpload.setOnClickListener(uploadListener());
    }

    private View.OnClickListener uploadListener() {
        return view -> {
            image = fetchAlldata();
            if(image == null){
                Toast.makeText(getContext(), "Please input name, description, and picture", Toast.LENGTH_SHORT).show();
            } else {
                StorageReference fileReference = storage.child(image.getUrl());
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            image.setUrl(downloadUrl.toString());
                            database.child(database.push().getKey()).setValue(image);
                            requireActivity().onBackPressed();
                        }).addOnFailureListener(
                                e -> Toast.makeText(getContext(), "Upload failed", Toast.LENGTH_SHORT).show());
            }
        };
    }

    private ImageData fetchAlldata() {
        String name = etName.getEditText().getText().toString(),
                description = etDescription.getEditText().getText().toString();
        if(name.equals("") || description.equals("") || imageUri == null){
            return null;
        }
        SharedPreferences sp = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        return new ImageData(name, description, name + "." + getFileExtension(imageUri), sp.getString("Name", null));
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private View.OnClickListener addListener() {
        return view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 0); //Yang baru lebih ribet malah gak ngerti :'(
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == requireActivity().RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            addPhoto.setImageURI(imageUri);
            addPhoto.clearColorFilter();
        }
    }

    private void assignAll(View view) {
        btnUpload = view.findViewById(R.id.btn_upload);
        addPhoto = view.findViewById(R.id.image_add);
        etName = view.findViewById(R.id.et_name);
        etDescription = view.findViewById(R.id.et_description);
        storage = FirebaseStorage.getInstance().getReference("images");
        database = FirebaseDatabase.getInstance("https://praetorian-23516-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("emojis");
    }
}