package sample.task.manager.features.main.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import sample.task.manager.R
import sample.task.manager.core.theme.w400
import sample.task.manager.core.theme.w600
import sample.task.manager.core.theme.w900
import sample.task.manager.core.theme.x2
import sample.task.manager.core.theme.x4
import sample.task.manager.core.util.ui.AppBottomSheetColumn
import sample.task.manager.core.util.ui.AppDivider

@Composable
fun ThemeListScreen(
    navController: NavController,
    viewModel: ThemeListViewModel
) {
    val theme by viewModel.theme.collectAsState(initial = null)

    val themeList = listOf(
        ThemeType.SystemDefault.value,
        ThemeType.Light.value,
        ThemeType.Dark.value
    )

    AppBottomSheetColumn {

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.label_choose_theme),
            style = MaterialTheme.typography.w900.x4,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = themeList
            ) { item ->
                ThemeListItem(
                    item = item,
                    selected = item == theme
                ) {
                    viewModel.updateTheme(item)
                    navController.navigateUp()
                }

                AppDivider(horizontal = 16.dp)
            }
        }
    }
}

@Composable
fun ThemeListItem(
    item: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = ThemeUtil.getThemeName(item)),
            style = MaterialTheme.typography.w400.x4,
            color = MaterialTheme.colorScheme.onBackground
        )

        if (selected) {

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(id = R.string.label_selected),
                style = MaterialTheme.typography.w600.x2,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
