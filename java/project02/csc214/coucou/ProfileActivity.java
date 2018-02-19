package project02.csc214.coucou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import project02.csc214.coucou.Model.Login;
import project02.csc214.coucou.Model.User;


public class ProfileActivity extends AppCompatActivity {

    private static DataAccessObject sDataAccessObject;
    String userName;
    TextView textFullName, textBirthDate, textHometown, textShortBio;
    ImageView profilePic;

    Button newsFeed, editInfo, logout;

    public final static String FULL_NAME = "FULL_NAME";
    public final static String BIRTH_DATE = "BIRTH_DATE";
    public final static String HOME_TOWN = "HOME_TOWN";
    public final static String SHORT_BIO = "SHORT_BIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sDataAccessObject = new DataAccessObject(getApplicationContext());

        profilePic = (ImageView) findViewById(R.id.view_profile_pic);

        textFullName = (TextView) findViewById(R.id.text_full_name);
        textBirthDate = (TextView) findViewById(R.id.text_birth_date);
        textHometown = (TextView) findViewById(R.id.text_home_town);
        textShortBio = (TextView) findViewById(R.id.text_short_bio);
        editInfo = (Button) findViewById(R.id.button_edit_info);

        Login in = sDataAccessObject.getRecentLogin();
        userName = in.getUserName();

        User user = sDataAccessObject.getUserFromUserName(userName);
        //fill info
        textFullName.setText(user.getFullName());
        Date birthDate = user.getBirthDate();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df.format(birthDate);
        textBirthDate.setText(formattedDate);
        textHometown.setText(user.getHomeTown());
        textShortBio.setText(user.getShortBio());

        newsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, NewsFeedActivity.class);
                startActivity(intent);
            }
        });

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditInfoActivity.class);
                startActivity(intent);
            }
        });

        if(savedInstanceState != null) {
            textFullName.setText(savedInstanceState.getString(FULL_NAME));
            textBirthDate.setText(savedInstanceState.getString(BIRTH_DATE));
            textHometown.setText(savedInstanceState.getString(HOME_TOWN));
            textShortBio.setText(savedInstanceState.getString(SHORT_BIO));
        }
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
                intent = new Intent(ProfileActivity.this, NewsFeedActivity.class);
                break;
            case R.id.menu_log_out:
                intent = new Intent(ProfileActivity.this, LoginActivity.class);
                sDataAccessObject.insertLoggedIn(new Login(userName, false));
                break;
            case R.id.menu_profile:
                intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                break;
            case R.id.menu_edit_info:
                intent = new Intent(ProfileActivity.this, EditInfoActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putString(FULL_NAME, textFullName.getText().toString());
        savedState.putString(BIRTH_DATE, textBirthDate.getText().toString());
        savedState.putString(HOME_TOWN, textHometown.getText().toString());
        savedState.putString(SHORT_BIO, textShortBio.getText().toString());
    }
}
