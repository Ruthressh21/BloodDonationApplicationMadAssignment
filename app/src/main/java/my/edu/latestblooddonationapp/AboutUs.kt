package my.edu.latestblooddonationapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import my.edu.latestblooddonationapp.databinding.FragmentAboutusBinding

class AboutUs : Fragment() {

    private var _binding: FragmentAboutusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        setHasOptionsMenu(true)
        _binding = FragmentAboutusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in about us
        menu.findItem(R.id.action_logOut).isVisible = false
    }

}