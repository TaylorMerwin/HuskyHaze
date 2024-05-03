package edu.tacoma.uw.huskyhaze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.tacoma.uw.huskyhaze.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {

    private var mbinding: FragmentAboutBinding? = null
    private val binding get() = mbinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mbinding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Handle button click to navigate back to settings
        binding.closeBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mbinding = null
    }
}
