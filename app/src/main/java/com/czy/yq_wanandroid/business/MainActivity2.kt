package com.czy.yq_wanandroid.business

import android.net.Uri
import android.os.Handler
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.lib_base.sp.SpHelpUtils
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.business.home.answer.AnswerFragment
import com.czy.yq_wanandroid.business.home.home.HomeFragment
import com.czy.yq_wanandroid.business.home.mine.MineFragment
import com.czy.yq_wanandroid.business.home.projects.ProjectsFragment
import com.czy.yq_wanandroid.test.PhoneCode
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.activity_main2.*


@Route(path = ArouterConfig.mainActivity)
class MainActivity2 : com.czy.lib_base.BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main2
    }

    private val fragments by lazy {
        ArrayList<Fragment>()
    }

    override fun initView() {
        fragments.add(HomeFragment())
        fragments.add(AnswerFragment())
        fragments.add(ProjectsFragment())
        fragments.add(MineFragment())
        mViewpager.offscreenPageLimit = 3
        val viewPagerFragmentStateAdapter =
            ViewPagerFragmentStateAdapter(supportFragmentManager, fragments)
        mViewpager.adapter = viewPagerFragmentStateAdapter
        mBottomNav.setOnNavigationItemSelectedListener {
            var position = when (it.itemId) {
                R.id.main_home -> 0
                R.id.main_answer -> 1
                R.id.main_project -> 2
                R.id.main_mine -> 3
                else -> -1
            }
            mViewpager.setCurrentItem(position, false)
            true
        }
        mViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val menuItem = mBottomNav.menu[position];
                if (!menuItem.isChecked)
                    menuItem.isChecked = true

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    override fun initData() {
//        ImageLoaderManager.getInstance().displayImageForViewGroup(mBottomNav,"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1353138978,364163918&fm=26&gp=0.jpg")
        SpHelpUtils.put("home","111")
        toast(SpHelpUtils.getString("home"))
    }

    class ViewPagerFragmentStateAdapter(
        fragmentManager: FragmentManager,
        var fragments: List<Fragment>
    ) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

    }
}