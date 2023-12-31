package pt.isel.gomoku.ui.screens.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.R
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.about.AboutActivity
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardActivity
import pt.isel.gomoku.ui.screens.login.LoginActivity
import pt.isel.gomoku.ui.screens.preferences.MatchPrivacyExtra
import pt.isel.gomoku.ui.screens.preferences.PreferencesActivity
import pt.isel.gomoku.ui.screens.profile.ProfileActivity
import pt.isel.gomoku.ui.screens.profile.UserDetailsExtra
import pt.isel.gomoku.ui.screens.stats.StatsActivity
import pt.isel.gomoku.ui.screens.stats.UserIdExtra
import pt.isel.gomoku.utils.MusicService
import pt.isel.gomoku.utils.NavigateAux
import pt.isel.gomoku.utils.playSound

class MenuActivity : ComponentActivity() {

    private val vm by viewModels<MainScreenViewModel> {
        val app = (application as DependenciesContainer)
        MainScreenViewModel.factory(app.userService, app.tokenRepository)
    }

    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) vm.fetchAuthenticatedUser()
        }

    private val logoutLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) vm.fetchAuthenticatedUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            vm.fetchAuthenticatedUser() // this isn't suspended, so it will not block the splash screen
            false
        }

        startMusicService()

        setContent {
            val authUser by vm.authUser.collectAsState(initial = idle())

            MenuScreen(
                userInfoState = authUser,
                onAvatarClick = {
                    this.playSound(R.raw.metal_click_strong)
                    if (authUser.getOrNull() == null)
                        loginLauncher.launch(Intent(this, LoginActivity::class.java))
                    else {
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra(
                            ProfileActivity.USER_DETAILS_EXTRA,
                            UserDetailsExtra(authUser.getOrNull()!!)
                        )
                        logoutLauncher.launch(intent)
                    }
                },
                onMatchRequested = { isPrivate ->
                    if (authUser.getOrNull() == null)
                        loginLauncher.launch(Intent(this, LoginActivity::class.java))
                    else {
                        this.playSound(R.raw.metal_click_medium)
                        NavigateAux.navigateTo<PreferencesActivity>(
                            this,
                            PreferencesActivity.MATCH_PRIVACY_EXTRA,
                            MatchPrivacyExtra(isPrivate)
                        )
                    }
                },
                onLeaderBoardRequested = { NavigateAux.navigateTo<LeaderBoardActivity>(this) },
                onAboutRequested = { NavigateAux.navigateTo<AboutActivity>(this) },
                onStatsRequested = {
                    if (authUser.getOrNull() == null)
                        loginLauncher.launch(Intent(this, LoginActivity::class.java))
                    else NavigateAux.navigateTo<StatsActivity>(
                        this,
                        StatsActivity.USER_ID_EXTRA,
                        UserIdExtra(authUser.getOrNull()!!.id)
                    )
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        resumeMusicService()
    }

    override fun onPause() {
        super.onPause()
        pauseMusicService()
    }

    private fun startMusicService() {
        val serviceIntent = Intent(this, MusicService::class.java)
        startService(serviceIntent)
    }

    private fun pauseMusicService() {
        val pauseIntent = Intent(this, MusicService::class.java)
        pauseIntent.action = MusicService.ACTION_PAUSE
        startService(pauseIntent)
    }

    private fun resumeMusicService() {
        val resumeIntent = Intent(this, MusicService::class.java)
        resumeIntent.action = MusicService.ACTION_RESUME
        startService(resumeIntent)
    }
}