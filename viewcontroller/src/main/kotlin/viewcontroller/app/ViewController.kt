package viewcontroller.app

import android.support.v4.app.Fragment
import viewcontroller.internal.RouterActivity
import kotlin.reflect.KClass

/**
 * View Controller
 *
 * @author Scott Smith 2018-02-10 22:04
 */
open class ViewController: Fragment(), UIControl {

    override fun push(viewControllerCls: KClass<in ViewController>) {
        (activity as? RouterActivity)?.push(viewControllerCls = viewControllerCls)
    }

    override fun pop() {
        (activity as? RouterActivity)?.popViewController()
    }
}