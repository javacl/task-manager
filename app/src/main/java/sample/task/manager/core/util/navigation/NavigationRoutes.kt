package sample.task.manager.core.util.navigation


sealed class NavigationRoutes(val route: String) {

    // Main
    data object Root : NavigationRoutes(
        route = "root"
    )

    data object ThemeList : NavigationRoutes(
        route = "theme_list"
    )

    // Task
    data object TaskList : NavigationRoutes(
        route = "task_list"
    )
}
