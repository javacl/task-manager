package sample.task.manager.features.task.ui.alarm.delete

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sample.task.manager.R
import sample.task.manager.core.theme.w400
import sample.task.manager.core.theme.w900
import sample.task.manager.core.theme.x2
import sample.task.manager.core.theme.x4
import sample.task.manager.core.util.ui.AppBottomSheetColumn
import sample.task.manager.core.util.ui.AppBox
import sample.task.manager.core.util.ui.AppButton

@Composable
fun DeleteTaskAlarmScreen(
    navController: NavController,
    viewModel: DeleteTaskAlarmViewModel
) {
    val task by viewModel.task.collectAsState(initial = null)

    val snackBarHostState = remember { SnackbarHostState() }

    val networkViewState by viewModel.networkViewState.collectAsState()

    if (networkViewState.showSuccess) {

        navController.navigateUp()

        networkViewState.showSuccess = false
    }

    AppBox(
        hostState = snackBarHostState
    ) {
        AppBottomSheetColumn {

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.label_delete_alarm),
                style = MaterialTheme.typography.w900.x4,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, end = 16.dp, start = 16.dp),
                text = stringResource(
                    id = R.string.param_sure_delete_alarm,
                    task?.text ?: ""
                ),
                style = MaterialTheme.typography.w400.x2,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                modifier = Modifier
                    .padding(top = 24.dp, end = 16.dp, start = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {

                AppButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.label_please_no_delete),
                    isBorder = true,
                    backgroundColor = MaterialTheme.colorScheme.error
                ) {
                    navController.navigateUp()
                }

                Spacer(modifier = Modifier.width(8.dp))

                AppButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.label_yes_delete),
                    backgroundColor = MaterialTheme.colorScheme.error,
                    loading = networkViewState.showProgress
                ) {
                    viewModel.deleteTaskAlarm()
                }
            }
        }
    }
}
