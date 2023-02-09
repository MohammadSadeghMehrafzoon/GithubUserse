package ir.misterdeveloper.githubusers.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.misterdeveloper.githubusers.R;
import ir.misterdeveloper.githubusers.database.AppDatabase;
import ir.misterdeveloper.githubusers.database.Favorite;
import ir.misterdeveloper.githubusers.model.Response.InformationUsers;
import ir.misterdeveloper.githubusers.util.ExtensionFunctionKt;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteVH> {


    private final Context context;
    private final List<Favorite> favoriteList;
    private final AppDatabase appDatabase;

    public FavoriteAdapter(Context context, List<Favorite> favoriteList, AppDatabase appDatabase) {

        this.context = context;
        this.favoriteList = favoriteList;
        this.appDatabase = appDatabase;


    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, null);
        return new FavoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteVH holder, @SuppressLint("RecyclerView") int position) {

        Favorite users = favoriteList.get(position);

        holder.textUserName.setText(users.getUsername());
        Picasso.get().load(users.getAvatarUrl()).placeholder(R.drawable.github)
                .into(holder.profileImage);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder alertReachedGoal = new AlertDialog.Builder(context);
                alertReachedGoal.setMessage(R.string.text_delete_favorite);
                alertReachedGoal.setPositiveButton(R.string.yes, (dialogInterface, i) -> {

                    int favoriteId = users.getId();
                    appDatabase.daoDatabase().deleteFavoriteById(favoriteId);
                    ExtensionFunctionKt.toast(context, context.getApplicationContext(), context.getString(R.string.removed_favorites));
                    refresh(position);


                });


                alertReachedGoal.setNegativeButton(R.string.no, (dialogInterface, i) -> {


                });

                alertReachedGoal.show();
                return false;
            }


        });


    }


    public void refresh(int position) {

        favoriteList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, favoriteList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public class FavoriteVH extends RecyclerView.ViewHolder {

        AppCompatTextView textUserName;
        CircleImageView profileImage;
        CardView cardView;

        public FavoriteVH(@NonNull View itemView) {
            super(itemView);

            textUserName = itemView.findViewById(R.id.textViewUserName);
            cardView = itemView.findViewById(R.id.cardView);
            profileImage = itemView.findViewById(R.id.profile_image);

        }
    }
}
