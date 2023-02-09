package ir.misterdeveloper.githubusers.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.misterdeveloper.githubusers.R;
import ir.misterdeveloper.githubusers.model.Response.InformationUsers;


public class FollowingAndFollowersAdapter extends RecyclerView.Adapter<FollowingAndFollowersAdapter.UserFVH> {

    Context context;
    List<InformationUsers> informationUsersList;

    public FollowingAndFollowersAdapter(Context context, List<InformationUsers> informationUsersList) {

        this.context = context;
        this.informationUsersList = informationUsersList;


    }

    @NonNull
    @Override
    public UserFVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, null);
        return new UserFVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFVH holder, int position) {

        InformationUsers users = informationUsersList.get(position);

        holder.textUserName.setText(users.getLogin());
        Picasso.get().load(users.getAvatarUrl()).placeholder(R.drawable.github)
                .into(holder.profileImage);


    }

    @Override
    public int getItemCount() {
        return informationUsersList.size();
    }

    public class UserFVH extends RecyclerView.ViewHolder {

        AppCompatTextView textUserName;
        CircleImageView profileImage;
        CardView cardView;

        public UserFVH(@NonNull View itemView) {
            super(itemView);

            textUserName = itemView.findViewById(R.id.textViewUserName);
            cardView = itemView.findViewById(R.id.cardView);
            profileImage = itemView.findViewById(R.id.profile_image);

        }
    }
}

