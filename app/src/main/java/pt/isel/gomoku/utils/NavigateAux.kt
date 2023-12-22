package pt.isel.gomoku.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable

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