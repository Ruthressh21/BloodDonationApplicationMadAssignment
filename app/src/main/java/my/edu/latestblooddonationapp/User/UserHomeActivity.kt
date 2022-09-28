package my.edu.latestblooddonationapp.User

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.nav_header_home_user.view.*
import my.edu.latestblooddonationapp.MainActivity
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.ActivityHomeUserBinding
import my.edu.latestblooddonationapp.databinding.FragmentLoginBinding

class UserHomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeUserBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomeUser.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayoutUser
        val navView: NavigationView = binding.navViewUser
        val navController = findNavController(R.id.nav_host_fragment_content_home_user)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_editProfile,
                R.id.nav_aboutUs,
                R.id.action_logOut
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        updateNavHeader()
    }

    private fun updateNavHeader() {
        firebaseAuth = FirebaseAuth.getInstance()

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref =
            Firebase.database("https://blooddonationkotlin-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users").child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val navView: NavigationView = binding.navViewUser
                val view: View = navView.getHeaderView(0)
                val name = dataSnapshot.child("name").value as String?
                val email = dataSnapshot.child("email").value as String?
                view.textViewNameUser.setText(name)
                view.textViewEmailUser.setText(email)

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_logOut -> {
                val intent = Intent(this, FragmentLoginBinding::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_user)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}