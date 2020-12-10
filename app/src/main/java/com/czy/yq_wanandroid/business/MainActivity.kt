package com.czy.yq_wanandroid.business

import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseActivity
import com.czy.yq_wanandroid.business.home.answer.AnswerFragment
import com.czy.yq_wanandroid.business.home.home.HomeFragment
import com.czy.yq_wanandroid.business.home.mine.MineFragment
import com.czy.yq_wanandroid.business.home.projects.ProjectsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private val fragments by lazy {
        ArrayList<Fragment>()
    }

    override fun initView() {
        fragments.add(HomeFragment())
        fragments.add(AnswerFragment())
        fragments.add(ProjectsFragment())
        fragments.add(MineFragment())
        mViewpager.offscreenPageLimit = 1
        val viewPagerFragmentStateAdapter = ViewPagerFragmentStateAdapter(this, fragments)
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
        mViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val menuItem = mBottomNav.menu[position];
                if (!menuItem.isChecked)
                    menuItem.setChecked(true)
            }
        })

    }

    override fun initData() {

    }

    class ViewPagerFragmentStateAdapter(
        fragmentActivity: FragmentActivity,
        var fragments: List<Fragment>
    ) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}