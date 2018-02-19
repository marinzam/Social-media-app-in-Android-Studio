package project02.csc214.coucou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import project02.csc214.coucou.Model.FeedItem;
import project02.csc214.coucou.Model.Login;
import project02.csc214.coucou.Model.User;

/**
 * Problems:
 * how to get the date???
 */
public class NewsFeedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsFeedAdapter mAdapter;
    private static DataAccessObject sDataAccessObject;

    private static final String EDIT_USER_NAME = "EDIT_USER_NAME";
    String mUserName;
    String newStatus;
    Button okB, cancelB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sDataAccessObject = new DataAccessObject(getApplicationContext());

        mAdapter = new NewsFeedAdapter(sDataAccessObject.getFeedItems());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mUserName = sDataAccessObject.getRecentLogin().getUserName();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(NewsFeedActivity.this).inflate(R.layout.alert_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeedActivity.this);
                builder.setView(v);

                final EditText editText = (EditText) findViewById(R.id.insert_content);
                okB = (Button) v.findViewById(R.id.okay_button1);
                cancelB = (Button) v.findViewById(R.id.cancel_button1);

                okB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createNewFeedItem(mUserName,editText.getText().toString());
                    }
                });
                cancelB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    private void createNewFeedItem(String username, String status) {
        FeedItem item = new FeedItem();
        User user = sDataAccessObject.getUserFromUserName(username);
        item.setUserName(username);
        item.setContent(status);
        //is this how you get the date?
        /*
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        */
        Date date = new Date();
        date.getTime();
        item.setPostedDate(date);
        item.setUserPic(user.getProfilePic());
        item.setStarred(0);

        sDataAccessObject.insertFeedItem(item);
        mAdapter.notifyDataSetChanged();
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
                intent = new Intent(NewsFeedActivity.this, NewsFeedActivity.class);
                break;
            case R.id.menu_log_out:
                intent = new Intent(NewsFeedActivity.this, LoginActivity.class);
                sDataAccessObject.insertLoggedIn(new Login(mUserName, false));
                break;
            case R.id.menu_profile:
                intent = new Intent(NewsFeedActivity.this, ProfileActivity.class);
                break;
            case R.id.menu_edit_info:
                intent = new Intent(NewsFeedActivity.this, EditInfoActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
