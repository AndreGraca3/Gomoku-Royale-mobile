package pt.isel.gomoku.ui.screens.about

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import pt.isel.gomoku.R

class AboutActivity : ComponentActivity() {

    companion object {
        fun navigate(origin: Activity) {
            with(origin) {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AboutScreen(
                authors,
                onSendEmailRequest = ::openSendEmail,
                onOpenWebsiteRequest = { openWebsite(it) }
            )
        }
    }

    private fun openSendEmail(email: String = STUDIO_EMAIL) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
            }

            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                R.string.no_suitable_app,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openWebsite(url: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                R.string.no_suitable_app,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

private val authors = listOf(
    AuthorInfo(
        "André Graça",
        "A47224@alunos.isel.pt",
        R.drawable.andre_avatar,
    ),
    AuthorInfo(
        "Diogo Santos",
        "A48459@alunos.isel.pt",
        R.drawable.diogo_avatar,
    ),
)

private const val STUDIO_EMAIL = "royale_games@gmail.com"
private const val EMAIL_SUBJECT = "Gomoku Royale App"