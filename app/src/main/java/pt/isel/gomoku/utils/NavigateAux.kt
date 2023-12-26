package pt.isel.gomoku.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.activity.ComponentActivity
import pt.isel.gomoku.R

class NavigateAux {
    companion object {
        inline fun <reified T> navigateTo(
            ctx: Activity,
            argumentName: String? = null,
            obj: Parcelable? = null
        ) {
            val intent = Intent(ctx, T::class.java)

            if (obj != null && argumentName != null)
                intent.putExtra(argumentName, obj)
            ctx.startActivity(intent)
        }
    }
}

fun Activity.overrideTransition(enterAnim: Int, exitAnim: Int) {
    // if build is 34 or higher, use the following line instead:
    if (android.os.Build.VERSION.SDK_INT >= 34) {
        overrideActivityTransition(
            ComponentActivity.OVERRIDE_TRANSITION_OPEN,
            R.anim.pop_up_in,
            R.anim.pop_up_out
        )
    } else {
        overridePendingTransition(enterAnim, exitAnim)
    }
}