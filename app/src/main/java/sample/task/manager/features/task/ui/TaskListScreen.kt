package sample.task.manager.features.task.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel
) {
    val taskList by viewModel.taskList.collectAsState(initial = null)
}
