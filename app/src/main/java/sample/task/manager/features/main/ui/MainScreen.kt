package sample.task.manager.features.main.ui

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sample.task.manager.core.util.accompanist.navigationMaterial.ExperimentalMaterialNavigationApi
import sample.task.manager.core.util.accompanist.navigationMaterial.ModalBottomSheetLayout
import sample.task.manager.core.util.accompanist.navigationMaterial.bottomSheet
import sample.task.manager.core.util.accompanist.navigationMaterial.rememberBottomSheetNavigator
import sample.task.manager.core.util.accompanist.permissions.ExperimentalPermissionsApi
import sample.task.manager.core.util.accompanist.permissions.rememberPermissionState
import sample.task.manager.core.util.navigation.NavigationRoutes
import sample.task.manager.core.util.snackBar.AppSnackBarHost
import sample.task.manager.core.util.snackBar.SnackBarType
import sample.task.manager.features.main.ui.theme.ThemeListScreen
import sample.task.manager.features.main.ui.theme.ThemeListViewModel
import sample.task.manager.features.task.ui.TaskListScreen
import sample.task.manager.features.task.ui.TaskListViewModel
import sample.task.manager.features.task.ui.alarm.create.CreateTaskAlarmScreen
import sample.task.manager.features.task.ui.alarm.create.CreateTaskAlarmViewModel
import sample.task.manager.features.task.ui.alarm.delete.DeleteTaskAlarmScreen
import sample.task.manager.features.task.ui.alarm.delete.DeleteTaskAlarmViewModel

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MainScreen(
    theme: Int?,
    mainSnackBarHostState: SnackbarHostState,
    showMainSnackBar: (message: String?, type: SnackBarType, duration: SnackbarDuration) -> Unit
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()

    val navController = rememberNavController(bottomSheetNavigator)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        val locationPermissionState = rememberPermissionState(
            Manifest.permission.POST_NOTIFICATIONS
        )

        SideEffect {
            locationPermissionState.launchPermissionRequest()
        }
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetShape = MaterialTheme.shapes.large,
        scrimColor = MaterialTheme.colorScheme.scrim
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                AppSnackBarHost(
                    hostState = mainSnackBarHostState
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.TaskList.route,
                route = NavigationRoutes.Root.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(
                    route = NavigationRoutes.TaskList.route
                ) {
                    val viewModel = hiltViewModel<TaskListViewModel>(it)
                    TaskListScreen(
                        theme = theme,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                bottomSheet(
                    route = NavigationRoutes.ThemeList.route
                ) {
                    val viewModel = hiltViewModel<ThemeListViewModel>(it)
                    ThemeListScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                bottomSheet(
                    route = NavigationRoutes.DeleteTaskAlarm.route
                ) {
                    val viewModel = hiltViewModel<DeleteTaskAlarmViewModel>(it)
                    DeleteTaskAlarmScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                bottomSheet(
                    route = NavigationRoutes.CreateTaskAlarm.route
                ) {
                    val viewModel = hiltViewModel<CreateTaskAlarmViewModel>(it)
                    CreateTaskAlarmScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
