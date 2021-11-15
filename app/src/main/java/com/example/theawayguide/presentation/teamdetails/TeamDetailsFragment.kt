package com.example.theawayguide.presentation.teamdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.theawayguide.ui.theme.TheAwayGuideTheme
import com.example.theawayguide.presentation.teamdetails.TeamDetailsComposable.TeamDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TeamDetailsFragment()
    }

    private val viewModel: TeamDetailsViewModel by viewModels()

    // val activityViewModel: TeamListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TheAwayGuideTheme {
                    TeamDetailsScreen(viewModel)
                }
            }
        }
    }
}
