package br.brilhante.gustavo.giphysearch.activity.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageButton
import android.widget.ImageView
import br.brilhante.gustavo.giphysearch.R
import br.brilhante.gustavo.giphysearch.component.GustavoActionBar
import br.brilhante.gustavo.giphysearch.fragment.SearchFragment
import br.brilhante.gustavo.giphysearch.fragment.ShowGiffFragment
import br.brilhante.gustavo.giphysearch.fragment.TrendingFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Gustavo on 10/03/18.
 */

open class MenuNavigationActivity : BaseActivity(), GustavoActionBar.ActionBarListener {
    var selectedUrl:String = ""

    override fun onRightButtonClick(button: ImageButton?, lastIndex: Int, index: Int) {
        manageNavigation(lastIndex, index)
    }

    override fun onLeftButtonClick(button: ImageButton?, lastIndex: Int, index: Int) {
        manageNavigation(lastIndex, index)
    }

    override fun onMiddleTextClick(button: ImageView?, lastIndex: Int, index: Int) {
        manageNavigation(lastIndex, index)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        gustavoActionBar.setSelected(GustavoActionBar.LEFT_SELECTED);
        replaceFragment(TrendingFragment.newInstance())

        gustavoActionBar.setListener(this)
    }

    fun callNavication(lastIndex: Int, index: Int){
        gustavoActionBar.setSelected(index)
        manageNavigation(lastIndex, index)
    }


    fun manageNavigation(lastIndex: Int, index: Int){
        when(index){
            GustavoActionBar.LEFT_SELECTED -> {
                replaceFramentFromLeft(TrendingFragment.newInstance())
            }
            GustavoActionBar.MIDDLE_SELECTED -> {
//                if(!gustavoActionBar.isMiddleButtonEnable)gustavoActionBar.enableMiddleButton(this)
                when(lastIndex){
                    GustavoActionBar.LEFT_SELECTED ->{
                        replaceFramentFromRight(ShowGiffFragment.newInstance(selectedUrl))
                    }
                    GustavoActionBar.RIGHT_SELECTED ->{
                        replaceFramentFromLeft(ShowGiffFragment.newInstance(selectedUrl))
                    }
                }
            }
            GustavoActionBar.RIGHT_SELECTED -> {
                replaceFramentFromRight(SearchFragment.newInstance())
            }
        }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerLayout, fragment)
                .commit();
    }

    fun replaceFramentFromRight(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.animation_right_to_middle,R.anim.animation_middle_to_left)
                .replace(R.id.containerLayout, fragment)
                .commit();
    }

    fun replaceFramentFromLeft(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.animation_left_to_middle,R.anim.animation_middle_to_right)
                .replace(R.id.containerLayout, fragment)
                .commit();
    }



}