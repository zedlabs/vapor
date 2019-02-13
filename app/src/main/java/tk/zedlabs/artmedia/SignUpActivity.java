package tk.zedlabs.artmedia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity  {

    TextView gotoLogin;
    EditText emailSignUp,usernameSignUp,passwordSignUp;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailSignUp = findViewById(R.id.signup_email);
        usernameSignUp = findViewById(R.id.signup_username);
        passwordSignUp = findViewById(R.id.signup_password);
        signUpButton = findViewById(R.id.signUp_button);

        passwordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event){

                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signUpClick();
                        }
                    });
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
        final Intent gotoLoginIntent = new Intent(this,LoginActivity.class);
        gotoLoginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        gotoLogin = findViewById(R.id.goto_login_button);
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(gotoLoginIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signUpClick();
           }
        });

    }


    public void signUpClick(){
        if (emailSignUp.getText().toString().equals("")
                || usernameSignUp.getText().toString().equals("")
                || passwordSignUp.getText().toString().equals("")) {

            Toast.makeText(SignUpActivity.this, "Email,username,password cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            final ParseUser appUser = new ParseUser();
            appUser.setEmail(emailSignUp.getText().toString());
            appUser.setUsername(usernameSignUp.getText().toString());
            appUser.setPassword(passwordSignUp.getText().toString());

            final ProgressDialog signUpProgressDialog = new ProgressDialog(SignUpActivity.this);
            signUpProgressDialog.setMessage("Signing Up User");
            signUpProgressDialog.show();

            appUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    signUpProgressDialog.dismiss();
                    if (e == null) {

                        Toast.makeText(SignUpActivity.this, appUser.getUsername() + " is signed up sucessfully!",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(SignUpActivity.this, "There was an error: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
//function to release the keyboard when user clicks on the parent activity
    public void rootLayoutTapped(View view){
        try{
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }catch (Exception e){

            e.printStackTrace();
        }
    }

}
