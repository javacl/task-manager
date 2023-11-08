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

    data object CreateTaskAlarm : NavigationRoutes(
        route = "create_task_alarm/{${NavigationKey.KEY_ID}}"
    )

    data object DeleteTaskAlarm : NavigationRoutes(
        route = "delete_task_alarm/{${NavigationKey.KEY_ID}}"
    )
}
