package ir.misterdeveloper.githubusers.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.misterdeveloper.githubusers.R;
import ir.misterdeveloper.githubusers.database.AppDatabase;
import ir.misterdeveloper.githubusers.database.Favorite;
import ir.misterdeveloper.githubusers.model.Response.InformationUsers;
import ir.misterdeveloper.githubusers.util.ExtensionFunctionKt;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {

    private final Context context;
    private final List<InformationUsers> informationUsersList;
    private AppDatabase appDatabase;


    public UserAdapter(Context context, List<InformationUsers> informationUsersList,
                       AppDatabase appDatabase) {

        this.context = context;
        this.informationUsersList = informationUsersList;
        this.appDatabase = appDatabase;


    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, null);
        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {

        InformationUsers users = informationUsersList.get(position);

        holder.textUserName.setText(users.getLogin());
        Picasso.get().load(users.getAvatarUrl()).placeholder(R.drawable.github)
                .into(holder.profileImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("userName", users.getLogin());
                bundle.putString("avatarUrl", users.getAvatarUrl());
                Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);


            }
        });


        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder alertReachedGoal = new AlertDialog.Builder(context);
                alertReachedGoal.setMessage(R.string.add_favorite);
                alertReachedGoal.setPositiveButton(R.string.yes, (dialogInterface, i) -> {

                    Favorite favorite = new Favorite();
                    favorite.setUsername(users.getLogin());
                    favorite.setAvatarUrl(users.getAvatarUrl());
                    appDatabase = AppDatabase.getInstance(context);
                    Long result = appDatabase.daoDatabase().insertFavoriteUser(favorite);

                    if (result > 0) {

                        ExtensionFunctionKt.toast(context, context.getApplicationContext(), context.getString(R.string.added_to_favorites));
                    }

                });


                alertReachedGoal.setNegativeButton(R.string.no, (dialogInterface, i) -> {


                });


                alertReachedGoal.show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return informationUsersList.size();
    }

    public class UserVH extends RecyclerView.ViewHolder {

        AppCompatTextView textUserName;
        CircleImageView profileImage;
        CardView cardView;

        public UserVH(@NonNull View itemView) {
            super(itemView);

            textUserName = itemView.findViewById(R.id.textViewUserName);
            cardView = itemView.findViewById(R.id.cardView);
            profileImage = itemView.findViewById(R.id.profile_image);

        }
    }
}
