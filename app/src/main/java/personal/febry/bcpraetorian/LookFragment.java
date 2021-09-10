package personal.febry.bcpraetorian;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LookFragment extends Fragment {
    private ImageView imgContent;
    private TextView tvName, tvDesc, tvAuth;

    public LookFragment(){
        super(R.layout.fragment_look);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignAll(view);
        correctAllView();
    }

    private void correctAllView() {
        tvName.setText(getActivity().getIntent().getStringExtra("Name"));
        tvDesc.setText(getActivity().getIntent().getStringExtra("Description"));
        tvAuth.setText(getActivity().getIntent().getStringExtra("Author"));
        Glide.with(getActivity()).load(getActivity().getIntent().getStringExtra("URL")).into(imgContent);
    }

    private void assignAll(View view) {
        imgContent = view.findViewById(R.id.image_content);
        tvName = view.findViewById(R.id.tv_name_look);
        tvDesc = view.findViewById(R.id.tv_desc);
        tvAuth = view.findViewById(R.id.tv_author);
    }
}