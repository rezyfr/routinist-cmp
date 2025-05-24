package presentation.navigation

import org.jetbrains.compose.resources.DrawableResource
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.ic_discovery_active
import routinist.shared.generated.resources.ic_discovery_inactive
import routinist.shared.generated.resources.ic_home_active
import routinist.shared.generated.resources.ic_home_inactive
import routinist.shared.generated.resources.ic_medal_active
import routinist.shared.generated.resources.ic_medal_inactive
import routinist.shared.generated.resources.ic_profile_active
import routinist.shared.generated.resources.ic_profile_inactive

enum class BottomNavigation(
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: String
) {
    HOME(
        selectedIcon = Res.drawable.ic_home_active,
        unselectedIcon = Res.drawable.ic_home_inactive,
        route = "Home"
    ),
    EXPLORE(
        selectedIcon = Res.drawable.ic_discovery_active,
        unselectedIcon = Res.drawable.ic_discovery_inactive,
        route = "Explore"
    ),
    ADD(
        selectedIcon = Res.drawable.ic_home_active,
        unselectedIcon = Res.drawable.ic_home_active,
        route = "Add"
    ),
    CHALLENGE(
        selectedIcon = Res.drawable.ic_medal_active,
        unselectedIcon = Res.drawable.ic_medal_inactive,
        route = "Challenge"
    ),
    PROFILE(
        selectedIcon = Res.drawable.ic_profile_active,
        unselectedIcon = Res.drawable.ic_profile_inactive,
        route = "Profile"
    )
}