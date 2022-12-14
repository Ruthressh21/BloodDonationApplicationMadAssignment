package my.edu.latestblooddonationapp.User

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import my.edu.latestblooddonationapp.R
import my.edu.latestblooddonationapp.databinding.FragmentUserHomeBinding

class UserHome : Fragment() {

    private var _binding: FragmentUserHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageViewDonateBlood.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_questionnaire)
        }
        binding.imageButtonClose.setOnClickListener{
            binding.imageView4.setVisibility(View.GONE)
            binding.imageButtonClose.setVisibility(View.GONE)
            binding.textView4.setVisibility(View.GONE)
        }
        binding.imageView4.setOnClickListener{
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.youtube.com/watch?v=HMXWvvjAJek")
            startActivity(openURL)

        }
    }
}