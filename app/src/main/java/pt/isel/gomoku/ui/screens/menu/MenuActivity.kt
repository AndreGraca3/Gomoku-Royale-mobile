package pt.isel.gomoku.ui.screens.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pt.isel.gomoku.DependenciesContainer
import pt.isel.gomoku.domain.Idle
import pt.isel.gomoku.domain.getOrNull
import pt.isel.gomoku.domain.idle
import pt.isel.gomoku.ui.screens.about.AboutActivity
import pt.isel.gomoku.ui.screens.leaderboard.LeaderBoardActivity
import pt.isel.gomoku.ui.screens.login.LoginActivity
import pt.isel.gomoku.ui.screens.match.MatchActivity
import pt.isel.gomoku.utils.MusicService
import pt.isel.gomoku.utils.NavigateAux

class MenuActivity : ComponentActivity() {

    private val vm by viewModels<MainScreenViewModel> {
        val app = (application as DependenciesContainer)
        MainScreenViewModel.factory(app.userService, app.tokenRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener {
            it.remove()
            vm.hasInitialized = true
        }

        lifecycleScope.launch {
            vm.getLocalToken()
            vm.token.collect {
                Log.d("login", "Token: $it")
                if (it != null) {
                    Log.v("login", "fetchAuthenticatedUser")
                    vm.fetchAuthenticatedUser()
                }
            }
        }

        // Start background music
        val svc = Intent(this, MusicService::class.java)
        // startService(svc) // comment this to avoid mental breakdowns while developing

        setContent {
            val userInfo by vm.userInfo.collectAsState(initial = idle())
            MenuScreen(
                isInitialized = vm.hasInitialized,
                userInfoState = userInfo,
                onAvatarClick = {
                    if (userInfo.getOrNull() == null)
                        NavigateAux.navigateTo<LoginActivity>(this)
                },
                onMatchRequested = { NavigateAux.navigateTo<MatchActivity>(this) },
                onLeaderBoardRequested = { NavigateAux.navigateTo<LeaderBoardActivity>(this) },
                onAboutRequested = { NavigateAux.navigateTo<AboutActivity>(this) }
            )
        }
    }
}