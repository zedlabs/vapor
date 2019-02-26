package tk.zedlabs.artmedia.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import tk.zedlabs.artmedia.R;

public class UserImages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_images);
        Intent receivedIntentObject = getIntent();
        final String currentUsername = receivedIntentObject.getStringExtra("username");

        Toast.makeText(this,currentUsername,Toast.LENGTH_SHORT).show();

        setTitle(currentUsername + "'s Photos");

        final LinearLayout ll = findViewById(R.id.linearLayout);

        ParseQuery<ParseObject> pq = new ParseQuery<ParseObject>("Photo");
        pq.whereEqualTo("username", currentUsername);
        pq.orderByDescending("createdAt");

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("loading images");
        pd.show();

        pq.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() > 0 && e == null){
                    for (ParseObject post : objects) {

                        final TextView postDescription = new TextView(UserImages.this);
                        postDescription.setText(post.get("image_des") + "");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null) {

                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UserImages.this);
                                    LinearLayout.LayoutParams imageView_params =
                                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5, 5, 5, 5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5, 5, 5, 15);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    ll.addView(postImageView);
                                    ll.addView(postDescription);

                                }

                            }
                        });


                    }

                }else {

                    Toast.makeText(UserImages.this, currentUsername + " doesn't have any posts!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            pd.dismiss();
            }
        });
    }
}
