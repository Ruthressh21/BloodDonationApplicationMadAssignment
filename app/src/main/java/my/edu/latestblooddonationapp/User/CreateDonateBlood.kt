package my.edu.latestblooddonationapp.User

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentCreateDonateBloodBinding

class CreateDonateBlood : Fragment() {

    private var _binding: FragmentCreateDonateBloodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentCreateDonateBloodBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in login fragment
        menu.findItem(R.id.action_logOut).isVisible = false
    }
}