package com.czy.business_base.flowResult;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 *@time 2021/4/16 15:48
 *@author yangqing
 *@describe 中转作用的fragment
 */
public class VirtualFragment extends Fragment {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(request!=null){
            request.post(requestCode,resultCode, data);
        }
        this.getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    Request request;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (request != null) {
            startActivityForResult(request.intent, request.requestCode);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        request = null;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
