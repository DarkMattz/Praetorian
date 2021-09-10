package personal.febry.bcpraetorian;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DescriptionActivity extends AppCompatActivity {

    private ImageView imgClose;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        assignAll();
        imgClose.setOnClickListener(closeListener());
    }

    private View.OnClickListener closeListener() {
        return view -> super.onBackPressed();
    }

    private void assignAll() {
        imgClose = findViewById(R.id.image_close);
        switch (getIntent().getExtras().getInt("IS_ADD")){
            case 0 :
                break;
            case 1 :
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_description, AddFragment.class, null)
                        .commit();
                break;
        }
    }
}