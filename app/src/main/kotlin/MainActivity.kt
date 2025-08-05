package com.example.thcourses

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.thcourses.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import com.example.thcourses.core.navigation.R as NavR
import com.example.thcourses.feature.auth.impl.R as AuthR

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )

        // https://issuetracker.google.com/issues/326356902
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination: NavDestination, arguments ->
            val isAuth = when (destination.id) {
                AuthR.id.login_fragment,
                AuthR.id.registration_fragment,
                AuthR.id.auth_nav_graph,
                    -> true

                else -> false
            }
            binding.navView.visibility = if (isAuth) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        if (savedInstanceState == null) {
            navController.navigate(
                resId = AuthR.id.auth_nav_graph,
                navOptions = NavOptions.Builder()
                    .setPopUpTo(NavR.id.home_nav_graph, true)
                    .build(),
                args = null,
            )
        }
    }
}
