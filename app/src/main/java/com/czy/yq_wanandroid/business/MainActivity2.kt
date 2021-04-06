package com.czy.yq_wanandroid.business

import android.util.Log
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.BaseActivity
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.business.home.answer.AnswerFragment
import com.czy.yq_wanandroid.business.home.home.HomeFragment
import com.czy.yq_wanandroid.business.home.mine.MineFragment
import com.czy.yq_wanandroid.business.home.projects.ProjectsFragment
import com.infoholdcity.basearchitecture.self_extends.log
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main2.*


@Route(path = ArouterConfig.mainActivity)
class MainActivity2 : BaseActivity() {
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
                R.id.main_project -> {
//                    ARouter.getInstance().build(ArouterConfig.test_entry).navigation()
//                    return@setOnNavigationItemSelectedListener false
                    2
                }
                R.id.main_mine -> 3
                else -> -1
            }
            "aaa".log()
            Log.e("","")
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
        fab_test.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.test_entry).navigation()
        }
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

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }

    }
}