package sample.task.manager.features.task.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import sample.task.manager.R
import sample.task.manager.core.theme.w300
import sample.task.manager.core.theme.w400
import sample.task.manager.core.theme.w500
import sample.task.manager.core.theme.w600
import sample.task.manager.core.theme.w700
import sample.task.manager.core.theme.x2
import sample.task.manager.core.theme.x4
import sample.task.manager.core.util.extensions.parseServerErrorMessage
import sample.task.manager.core.util.navigation.NavigationRoutes
import sample.task.manager.core.util.navigation.safeNavigate
import sample.task.manager.core.util.snackBar.showAppSnackBar
import sample.task.manager.core.util.ui.AppBox
import sample.task.manager.core.util.ui.AppSwipeRefresh
import sample.task.manager.features.main.ui.theme.ThemeUtil

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TaskListScreen(
    theme: Int?,
    navController: NavController,
    viewModel: TaskListViewModel
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val taskList by viewModel.taskList.collectAsState(initial = null)

    val snackBarHostState = remember { SnackbarHostState() }

    val networkViewState by viewModel.networkViewState.collectAsState()

    if (networkViewState.showError) {

        SideEffect {

            coroutineScope.launch {

                snackBarHostState.showAppSnackBar(
                    message = context.parseServerErrorMessage(networkViewState),
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        networkViewState.showError = false
    }

    AppBox(
        hostState = snackBarHostState,
        onRefresh = viewModel::refresh
    ) {
        AppSwipeRefresh(
            refreshing = networkViewState.showProgress,
            onRefresh = viewModel::refresh
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                stickyHeader {
                    TaskListToolbarItem(
                        navController = navController,
                        theme = theme
                    )
                }
            }
        }
    }
}

@Composable
fun TaskListToolbarItem(
    navController: NavController,
    theme: Int?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.label_app_name),
                style = MaterialTheme.typography.w700.x4,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(id = R.string.txt_app_detail),
                style = MaterialTheme.typography.w300.x2,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    navController.safeNavigate(NavigationRoutes.ThemeList.route)
                }
                .padding(8.dp),
            text = stringResource(id = ThemeUtil.getThemeName(theme)),
            style = MaterialTheme.typography.w500.x2,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
