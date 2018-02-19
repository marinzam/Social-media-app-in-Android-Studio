package project02.csc214.coucou;
/**
 * Problems: how to getItemCount and how to onBindViewHolder
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

import project02.csc214.coucou.Model.Favorite;
import project02.csc214.coucou.Model.User;


/**
 * Created by Geraldine
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    private List<User> mUsers;
    private List<Favorite> mFavorites;
    private static DataAccessObject sDataAccessObject;

    public FavoritesAdapter(List<User> userList, List<Favorite> favoriteList) {
        mUsers = userList;
        mFavorites = favoriteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final User item = mUsers.get(position);
        String username = item.getUserName();

        holder.userName.setText(username);

        Favorite temp = new Favorite(sDataAccessObject.getRecentLogin().getUserName());
        temp.setFavorite(username);
        if(mFavorites.contains(temp)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public CheckBox checkBox;
        boolean isFavorite;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.text_favorite);

            checkBox = (CheckBox) view.findViewById(R.id.checkbox_favorite);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()) {
                        isFavorite = false;
                    } else {
                        isFavorite = true;
                    }
                }
            });
        }
    }
}
