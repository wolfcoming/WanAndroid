package com.czy.lib_base.flowResult;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;


public class VirtualFragment extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        request.post(resultCode, data);
        request = null;
        this.getActivity().getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    Request request;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (request != null) {
            startActivityForResult(request.intent, 0);
        }
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
