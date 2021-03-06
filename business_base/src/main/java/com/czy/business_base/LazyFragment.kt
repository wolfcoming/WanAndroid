package com.czy.business_base

import android.os.Bundle
import com.infoholdcity.basearchitecture.self_extends.log

open abstract class LazyFragment : BaseFragment() {

    private var mIsFirstVisible = true
    private var mUserVisible = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (mViewCreated) {
            if (isVisibleToUser && !mUserVisible) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && mUserVisible) {
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
        if (mUserVisible == visible) {
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
        if (!mIsFirstVisible&&!mUserVisible ) {
            if (!isHidden && userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint&&mUserVisible) {
            dispatchUserVisibleHint(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsFirstVisible = true
        mViewCreated = false
    }

    open fun onVisible(isFirstVisible: Boolean) {
        "${this::class.java.simpleName} ${
            Thread.currentThread()
                .getStackTrace()[2].getMethodName()
        }: $isFirstVisible".log("QQQQQQ")
    }

    open fun onInVisible() {
        "${this::class.java.simpleName} ${
            Thread.currentThread()
                .getStackTrace()[2].getMethodName()
        }".log("QQQQQQ")

    }
}