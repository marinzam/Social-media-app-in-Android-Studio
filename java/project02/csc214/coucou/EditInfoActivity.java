package project02.csc214.coucou;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import project02.csc214.coucou.Model.Login;
import project02.csc214.coucou.Model.User;

import static java.lang.Integer.valueOf;

public class EditInfoActivity extends AppCompatActivity {

    public static DataAccessObject sDataAccessObject;
    Uri imageFileUri;

    ImageView profilePic;
    Button cameraButton, uploadButton;
    EditText editFullName, editMonth, editDate, editYear, editHometown, editShortBio;
    Button cancelButton,saveButton;
    String mFullName, mMonth, mDate, mYear, mHomeTown, mShortBio;

    public final static String EDIT_FULL_NAME = "EDIT_FULL_NAME";
    public final static String EDIT_MONTH = "EDIT_MONTH";
    public final static String EDIT_DATE = "EDIT_DATE";
    public final static String EDIT_YEAR = "EDIT_YEAR";
    public final static String EDIT_HOMETOWN = "EDIT_HOMETOWN";
    public final static String EDIT_SHORT_BIO = "EDIT_SHORT_BIO";
    public final static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        sDataAccessObject = new DataAccessObject(getApplicationContext());

        profilePic = (ImageView) findViewById(R.id.view_profile_pic);
        cameraButton = (Button) findViewById(R.id.button_take_pic);
        uploadButton = (Button) findViewById(R.id.button_upload_pic);

        editFullName = (EditText) findViewById(R.id.edit_full_name);

        editMonth = (EditText) findViewById(R.id.edit_month);
        editDate = (EditText) findViewById(R.id.edit_date);
        editYear = (EditText) findViewById(R.id.edit_year);

        editHometown = (EditText) findViewById(R.id.edit_home_town);
        editShortBio = (EditText) findViewById(R.id.edit_short_bio);

        cancelButton = (Button) findViewById(R.id.button_cancel_new);
        saveButton = (Button) findViewById(R.id.button_save_new);

        if(savedInstanceState != null) {
            editFullName.setText(savedInstanceState.getString(EDIT_FULL_NAME));
            editMonth.setText(savedInstanceState.getString(EDIT_MONTH));
            editDate.setText(savedInstanceState.getString(EDIT_DATE));
            editYear.setText(savedInstanceState.getString(EDIT_YEAR));
            editHometown.setText(savedInstanceState.getString(EDIT_HOMETOWN));
            editShortBio.setText(savedInstanceState.getString(EDIT_SHORT_BIO));
        }

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                */
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFullName = editFullName.getText().toString();

                mMonth = editMonth.getText().toString();
                mDate = editDate.getText().toString();
                mYear = editYear.getText().toString();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MONTH, valueOf(mMonth));
                cal.set(Calendar.DATE, valueOf(mDate));
                cal.set(Calendar.YEAR, valueOf(mYear));
                Date date = cal.getTime();

                mHomeTown = editHometown.getText().toString();
                mShortBio = editShortBio.getText().toString();

                Login in  = sDataAccessObject.getRecentLogin();
                User user = sDataAccessObject.getUserFromUserName(in.getUserName());
                user.setFullName(mFullName);
                user.setBirthDate(date);
                user.setHomeTown(mHomeTown);
                user.setShortBio(mShortBio);
                //picture path
                sDataAccessObject.updateUser(user);

                Intent intent = new Intent(EditInfoActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        profilePic.setImageBitmap(bitmap);

        String result = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");

        imageFileUri = Uri.parse(result);
    }
}