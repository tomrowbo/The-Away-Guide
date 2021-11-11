package com.example.theawayguide.presentation.teamlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.theawayguide.ui.theme.TheAwayGuideTheme
import com.example.theawayguide.presentation.teamlist.TeamListComposable.TeamListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamListFragment : Fragment() {

    companion object {
        fun newInstance() = TeamListFragment()
    }

    private val viewModel: TeamListViewModel by viewModels()

    // val activityViewModel: TeamListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TheAwayGuideTheme {
                    TeamListScreen(viewModel)
                }
            }
        }
    }
}

data class NavDrawerItem(var route: String, var icon: ImageVector, var title: String)
