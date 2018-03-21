package viewcontroller.internal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.FrameLayout
import viewcontroller.R
import viewcontroller.app.ViewController
import kotlin.reflect.KClass

/**
 * Just for routing to a ViewController.
 *
 * @author Scott Smith 2018-03-20 12:02
 */
class RouterActivity : AppCompatActivity() {
    private val KEY_CUR_VIEW_CONTROLLER = "android:viewcontroller:current"
    private var mCurrentViewControllerTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewcontrollerContainer = FrameLayout(this)
        viewcontrollerContainer.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        viewcontrollerContainer.id = R.id.viewcontroller_container
        setContentView(viewcontrollerContainer)
    }

    fun push(viewControllerCls: KClass<in ViewController>) {
        if (viewControllerCls.qualifiedName == mCurrentViewControllerTag) return

        val viewControllerManager = supportFragmentManager
        val viewControllerTransaction = viewControllerManager.beginTransaction()

        if (!TextUtils.isEmpty(mCurrentViewControllerTag)) {
            val currentViewController = viewControllerManager.findFragmentByTag(mCurrentViewControllerTag)

            if (null != currentViewController) {
                viewControllerTransaction.hide(currentViewController)
            }
        }

        var targetViewController = viewControllerManager.findFragmentByTag(viewControllerCls.qualifiedName)
                as? ViewController
        if (null != targetViewController) {
            viewControllerTransaction.show(targetViewController)
        } else {
            targetViewController = viewControllerCls.java.getConstructor().newInstance() as? ViewController
            if (null != targetViewController) {
                viewControllerTransaction.add(R.id.viewcontroller_container, targetViewController, viewControllerCls.qualifiedName)
            }
        }
        viewControllerTransaction.commitAllowingStateLoss()
    }

    fun popViewController() {
        val viewControllerManager = supportFragmentManager
        if(!viewControllerManager.popBackStackImmediate()) {
            onBackPressed()
        }
    }
}