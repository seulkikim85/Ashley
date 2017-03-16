package com.isp1004.ashley.myinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isp1004.ashley.R;

/**
 * Created by SEULKI on 2017-03-16.
 */

public class MyInfoTab1Profile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_info_tab1_profile, container, false);



        return rootView;
    }
}
