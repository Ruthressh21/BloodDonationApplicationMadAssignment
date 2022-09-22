package my.edu.latestblooddonationapp.User

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import my.edu.latestblooddonationapp.MainActivity
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.ActivityHomeUserBinding

class UserHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)

        setContentView(binding.root)



      }


    }


