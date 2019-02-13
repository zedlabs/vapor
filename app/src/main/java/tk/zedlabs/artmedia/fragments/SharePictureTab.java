package tk.zedlabs.artmedia.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tk.zedlabs.artmedia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment {


    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
    }

}
