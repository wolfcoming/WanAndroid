package com.czy.yq_wanandroid.base

import android.os.Bundle
import com.infoholdcity.basearchitecture.self_extends.log

open abstract class LazyFragment : BaseFragment() {

    private var mIsFirstVisible = true
    private var mUserVisible = true

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (mViewCreated) {
            if (isVisibleToUser) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser) {
                dispatchUserVisibleHint(false)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isHidden && userVisibleHint) {
            dispatchUserVisibleHint(true)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            dispatchUserVisibleHint(false)
        } else {
            dispatchUserVisibleHint(true)
        }
    }

    private fun dispatchUserVisibleHint(visible: Boolean) {
        if (visible && !isParentVisible()) {
            return
        }
        mUserVisible = visible
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false
                onVisible(true)
            } else {
                onVisible(false)
            }
            dispatchChildVisibleState(true)
        } else {
            dispatchChildVisibleState(false)
            onInVisible()
        }
    }

    private fun dispatchChildVisibleState(visible: Boolean) {
        for (fragment in childFragmentManager.fragments) {
            if (fragment is LazyFragment && !fragment.isHidden && fragment.userVisibleHint) {
                fragment.dispatchUserVisibleHint(visible)
            }
        }
    }

    private fun isParentVisible(): Boolean {
        val fragment = parentFragment
        if (fragment == null) return true
        if (fragment is LazyFragment) {
            return fragment.mUserVisible
        }
        return fragment.isVisible
    }


    override fun onResume() {
        super.onResume()
        if(!mIsFirstVisible){
            if(!isHidden && userVisibleHint){
                dispatchUserVisibleHint(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(userVisibleHint){
            dispatchUserVisibleHint(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsFirstVisible = true
    }

    open fun onVisible(isFirstVisible: Boolean) {
        "onVisible: $isFirstVisible".log("QQQQQQ")
    }

    open fun onInVisible() {
        "onInVisible".log("QQQQQQ")
    }
}