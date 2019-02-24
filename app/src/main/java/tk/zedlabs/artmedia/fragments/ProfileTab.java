package tk.zedlabs.artmedia.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import tk.zedlabs.artmedia.R;

public class ProfileTab extends Fragment {

private EditText nameEditText, hobbiesEditText, bioEditText;
private Button updateButton;

    public ProfileTab(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        hobbiesEditText = view.findViewById(R.id.hobbiesEditText);
        bioEditText = view.findViewById(R.id.bioEditText);
        updateButton = view.findViewById(R.id.updateButton);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        String nickname = parseUser.get("nickname")+"";
        String hobbies = parseUser.get("hobbies")+"";
        String bio = parseUser.get("bio")+"";

        if(parseUser.get("nickname") == null){
            nameEditText.setText("");
        }
         else {
            nameEditText.setText(nickname);
              }

        if(parseUser.get("hobbies") == null){
            hobbiesEditText.setText("");
        }
          else {
            hobbiesEditText.setText(hobbies);
               }

        if(parseUser.get("bio") == null){
            bioEditText.setText("");
        }
          else {
            bioEditText.setText(bio);
               }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("nickname",nameEditText.getText()+"");
                parseUser.put("hobbies",hobbiesEditText.getText()+"");
                parseUser.put("bio",bioEditText.getText()+"");

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating User info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            progressDialog.hide();
                            Toast.makeText(getContext(),"profile updated!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

}
