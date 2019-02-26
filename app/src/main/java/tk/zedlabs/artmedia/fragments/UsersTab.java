package tk.zedlabs.artmedia.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import tk.zedlabs.artmedia.R;
import tk.zedlabs.artmedia.activities.UserImages;

public class UsersTab extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;

    public UsersTab(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView = view.findViewById(R.id.userList);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, arrayList);
        listView.setOnItemClickListener(UsersTab.this);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> userList, ParseException e) {
                if(e == null){
                    if(userList.size()>0){

                        for(ParseUser user : userList){
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);             //adapter is added here because arrayList was not populated earlier
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(getContext(), UserImages.class);
        i.putExtra("username",arrayList.get(position)+"");
        startActivity(i);

    }
}
