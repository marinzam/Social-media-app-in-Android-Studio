package project02.csc214.coucou;
/**
 * Problems: how to getItemCount and how to onBindViewHolder
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import project02.csc214.coucou.Model.FeedItem;

import static java.lang.Integer.valueOf;

/**
 * Created by Geraldine
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    private List<FeedItem> mFeedItemList;
    private static DataAccessObject sDataAccessObject;

    public NewsFeedAdapter(List<FeedItem> feedItemList) {
        mFeedItemList = feedItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FeedItem item = mFeedItemList.get(position);

        holder.userName.setText(item.getUserName());
        Date postedDate = item.getPostedDate();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String formattedDate = df.format(postedDate);
        holder.postedDate.setText(formattedDate);
        holder.content.setText(item.getContent());
        //holder.thumbNail.setImageResource(item.getUserPic());

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int starred = item.getStarred();
                if(item.isStarred()) {
                    holder.star.setImageResource(R.mipmap.star_full);
                } else{
                    holder.star.setImageResource(R.mipmap.star_empty);
                }
                starred++;
                holder.starCount.setText(valueOf(starred));
            }
        });

        holder.thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = item.getUserName();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeedItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, content, postedDate, starCount;
        public ImageButton thumbNail, star;

        public MyViewHolder(View view) {
            super(view);
            userName = (TextView) view.findViewById(R.id.item_user_name);
            thumbNail = (ImageButton) view.findViewById(R.id.item_thumbnail);
            content = (TextView) view.findViewById(R.id.item_content);
            star = (ImageButton) view.findViewById(R.id.item_starred) ;
            starCount = (TextView) view.findViewById(R.id.item_starred_count);
            postedDate = (TextView) view.findViewById(R.id.item_posted_date);
        }
    }
}
