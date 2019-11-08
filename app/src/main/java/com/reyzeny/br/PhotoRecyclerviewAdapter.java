package com.reyzeny.br;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.reyzeny.br.Api.Response.PhotoResponse;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class PhotoRecyclerviewAdapter extends RecyclerView.Adapter<PhotoRecyclerviewAdapter.PhotoSearchViewHolder> {
    private List<PhotoResponse.Photos.Photo> photoList;
    @NonNull
    @Override
    public PhotoSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_list, parent, false);
        return new PhotoSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoSearchViewHolder holder, int position) {
        String photoUrl = createPhotoUrl(photoList.get(position).farm, photoList.get(position).id, photoList.get(position).server, photoList.get(position).secret);
        String title = photoList.get(position).title;
        holder.setInfo(photoUrl, title);
    }

    @Override
    public int getItemCount() {
        return photoList==null ? 0 : photoList.size();
    }

    public void setPhotoList(List<PhotoResponse.Photos.Photo> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    // example: https://farm9.staticflickr.com/8187/8432423659_dd1b834ec5.jpg
    private String createPhotoUrl(int farm_id, String id, String server, String secret){
        return String.format(Locale.ENGLISH,"https://farm%d.staticflickr.com/%s/%s_%s.jpg", farm_id, server, id, secret);
    }

    public class PhotoSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView photoImageView;
        private TextView photoTitle;
        String imageUrl;
        public PhotoSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.imageview_thumbnail);
            photoTitle = itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener(this);
        }
        void setInfo(String imageUrl, String title) {
            this.imageUrl = imageUrl;
            photoTitle.setText(title);

            Picasso.get().load(imageUrl).resize(100, 100).centerCrop().into(photoImageView);
            System.out.println("image url here is " + imageUrl);
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), FullPhotoDisplay.class);
            intent.putExtra(FullPhotoDisplay.IMAGE_URL, imageUrl);
            view.getContext().startActivity(intent);
        }
    }
}
