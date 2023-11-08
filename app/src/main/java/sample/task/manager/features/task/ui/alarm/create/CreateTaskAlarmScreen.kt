package sample.task.manager.features.task.ui.alarm.create

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sample.task.manager.R
import sample.task.manager.core.theme.w400
import sample.task.manager.core.theme.w600
import sample.task.manager.core.theme.w900
import sample.task.manager.core.theme.x2
import sample.task.manager.core.theme.x4
import sample.task.manager.core.util.extensions.parseValidationErrorMessage
import sample.task.manager.core.util.ui.AppBottomSheetColumn
import sample.task.manager.core.util.ui.AppBox
import sample.task.manager.core.util.ui.AppButton
import sample.task.manager.core.util.ui.AppIconButton
import sample.task.manager.core.util.ui.AppTextField
import sample.task.manager.features.task.domain.validation.CreateTaskAlarmValidationError

@Composable
fun CreateTaskAlarmScreen(
    navController: NavController,
    viewModel: CreateTaskAlarmViewModel
) {
    val context = LocalContext.current

    val task by viewModel.task.collectAsState(initial = null)

    val time by viewModel.time.collectAsState()

    val title by viewModel.title.collectAsState()

    val description by viewModel.description.collectAsState()

    val notValidTime by viewModel.notValidTime.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    val networkViewState by viewModel.networkViewState.collectAsState()
    val validationError = networkViewState.validationError as CreateTaskAlarmValidationError?

    if (networkViewState.showValidationError) {

        viewModel.setNotValidTime(!validationError?.time.isNullOrEmpty())

        networkViewState.showValidationError = false
    }

    if (networkViewState.showSuccess) {

        navController.navigateUp()

        networkViewState.showSuccess = false
    }

    val scrollState = rememberScrollState()

    AppBox(
        hostState = snackBarHostState
    ) {
        AppBottomSheetColumn {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = if (task?.time == null) R.string.label_create_alarm else R.string.label_edit_alarm),
                    style = MaterialTheme.typography.w900.x4,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    text = task?.text ?: "",
                    style = MaterialTheme.typography.w400.x2,
                    color = MaterialTheme.colorScheme.onBackground
                )

                AppTextField(
                    modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp),
                    value = "",
                    onValueChange = {},
                    title = stringResource(id = R.string.label_time),
                    isError = notValidTime,
                    enabled = !networkViewState.showProgress,
                    errorMessage = context.parseValidationErrorMessage(errorList = validationError?.time),
                    textStyle = MaterialTheme.typography.w600.x4,
                    trailingIcon = {

                        AppIconButton(
                            iconResource = R.drawable.ic_chevron_small_down_s24_sw1v5,
                            enabled = !networkViewState.showProgress
                        ) {
                        }
                    },
                    readOnly = true,
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                    }
                                }
                            }
                        },
                    maxLines = 1
                )

                AppTextField(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    value = title,
                    onValueChange = viewModel::setTitle,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    enabled = !networkViewState.showProgress,
                    title = stringResource(id = R.string.label_title),
                    textStyle = MaterialTheme.typography.w600.x4,
                    maxLines = 1
                )

                AppTextField(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    value = description,
                    onValueChange = viewModel::setDescription,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    enabled = !networkViewState.showProgress,
                    title = stringResource(id = R.string.label_description),
                    textStyle = MaterialTheme.typography.w600.x4,
                    maxLines = 1
                )

                AppButton(
                    modifier = Modifier.padding(
                        top = 24.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    text = stringResource(id = R.string.label_submit),
                    loading = networkViewState.showProgress
                ) {
                    viewModel.insertTaskAlarm()
                }
            }
        }
    }
}
