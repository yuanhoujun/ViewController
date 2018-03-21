package viewcontroller.app

import kotlin.reflect.KClass

/**
 * UI control interface
 *
 * @author Scott Smith 2018-03-20 11:42
 */
interface UIControl {
    /**
     * Push to ViewController
     *
     * @param viewControllerCls the target ViewController kclass object
     */
    fun push(viewControllerCls: KClass<in ViewController>)

    /**
     * Pop up from ViewController stack
     */
    fun pop()
}