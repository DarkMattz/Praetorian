package personal.febry.bcpraetorian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import personal.febry.bcpraetorian.data.ImageData;

public class MainRVAdapter extends RecyclerView.Adapter<MainRVAdapter.ImageViewHolder> {

    private ClickListener clickListener;
    private Context context;
    private List<ImageData> imgData;

    public MainRVAdapter(Context context, List<ImageData> imgData, ClickListener clickListener) {
        this.context = context;
        this.imgData = imgData;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false);
        return new ImageViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageData data = imgData.get(position);
        holder.tvContent.setText(data.getName());
        Glide.with(context).load(data.getUrl()).into(holder.imgContent);
    }

    @Override
    public int getItemCount() {
        return imgData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvContent;
        public ImageView imgContent;
        ClickListener clickListener;

        public ImageViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            imgContent = itemView.findViewById(R.id.img_content);
            itemView.setOnClickListener(this);
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(getAdapterPosition());
        }
    }

    public interface ClickListener {
        void onClick(int position);
    }
}
