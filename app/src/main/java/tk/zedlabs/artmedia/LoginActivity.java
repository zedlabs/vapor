package tk.zedlabs.artmedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView gotoSignUp;
    EditText emailLogin,passwordLogin;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.login_email);
        passwordLogin = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        gotoSignUp = findViewById(R.id.goto_signup_button);

        passwordLogin.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event){

                if(keyCode == KeyEvent.KEYCODE_ENTER &&
                        event.getAction() == KeyEvent.ACTION_DOWN){

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loginClick();
                        }
                    });
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }

        final Intent gotoSignUpIntent = new Intent(this,SignUpActivity.class);
        gotoSignUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(gotoSignUpIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClick();
            }
        });
    }

    public void loginClick() {
        if (emailLogin.getText().toString().equals("")
                || passwordLogin.getText().toString().equals("")) {

            Toast.makeText(LoginActivity.this, "Email,password cannot be empty",
                    Toast.LENGTH_SHORT).show();
        } else {
            ParseUser.logInInBackground(emailLogin.getText().toString(), passwordLogin.getText().toString(),
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                Toast.makeText(LoginActivity.this, user.getUsername() + " is Logged in!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "unable to login: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    public void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){

            e.printStackTrace();
        }
    }
}
