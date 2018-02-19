package project02.csc214.coucou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import project02.csc214.coucou.Model.Login;


public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mAdapter;
    private static DataAccessObject sDataAccessObject;

    String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_favorites);
        sDataAccessObject = new DataAccessObject(getApplicationContext());

        mAdapter = new NewsFeedAdapter(sDataAccessObject.getFeedItems());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mUserName = sDataAccessObject.getRecentLogin().getUserName();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch(item.getItemId()) {
            case R.id.menu_news_feed:
                intent = new Intent(FavoriteActivity.this, NewsFeedActivity.class);
                break;
            case R.id.menu_log_out:
                intent = new Intent(FavoriteActivity.this, LoginActivity.class);
                sDataAccessObject.insertLoggedIn(new Login(mUserName, false));
                break;
            case R.id.menu_profile:
                intent = new Intent(FavoriteActivity.this, ProfileActivity.class);
                break;
            case R.id.menu_edit_info:
                intent = new Intent(FavoriteActivity.this, EditInfoActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
