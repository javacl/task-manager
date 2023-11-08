package sample.task.manager.core.util.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import sample.task.manager.R
import sample.task.manager.core.theme.ExtraLargeRadius
import sample.task.manager.core.theme.LargeRadius
import sample.task.manager.core.theme.disable
import sample.task.manager.core.theme.onDisable
import sample.task.manager.core.theme.w400
import sample.task.manager.core.theme.w500
import sample.task.manager.core.theme.w700
import sample.task.manager.core.theme.x1
import sample.task.manager.core.theme.x2
import sample.task.manager.core.theme.x3
import sample.task.manager.core.util.extensions.appShadow
import sample.task.manager.core.util.snackBar.AppSnackBarHost

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) = Box(
    modifier = modifier
        .appShadow(cornersRadius = LargeRadius)
        .clip(shape = shape)
        .background(color = backgroundColor)
) {
    content()
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    height: Dp = 48.dp,
    isWrap: Boolean = false,
    isBorder: Boolean = false,
    borderWidth: Dp = 1.dp,
    enabled: Boolean = true,
    loading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.disable,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContentColor: Color = MaterialTheme.colorScheme.onDisable,
    shape: Shape = MaterialTheme.shapes.large,
    iconResource: Int? = null,
    iconSize: Dp = 16.dp,
    iconPadding: Dp = 8.dp,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.w700.x2,
    onClick: () -> Unit
) {
    val isEnabled = enabled && !loading

    val mBackgroundColor = if (isEnabled) backgroundColor else disabledBackgroundColor

    val mContentColor = if (isEnabled) {

        if (isBorder) backgroundColor else contentColor

    } else {

        disabledContentColor
    }

    Box(
        modifier = modifier
            .appShadow(cornersRadius = LargeRadius)
            .clip(shape)
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (isWrap) {
                        Modifier.wrapContentWidth()
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
                .height(height)
                .then(
                    if (isBorder) {
                        Modifier.border(
                            width = borderWidth,
                            color = mBackgroundColor,
                            shape = shape
                        )
                    } else {
                        Modifier.background(mBackgroundColor)
                    }
                )
                .clickable(
                    onClick = onClick,
                    enabled = isEnabled
                )
                .padding(horizontal = if (isWrap) 32.dp else 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            if (loading) {

                AppCircularProgressIndicator()

            } else {

                iconResource?.let {
                    Icon(
                        modifier = Modifier
                            .padding(end = iconPadding)
                            .size(iconSize),
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = mContentColor
                    )
                }

                Text(
                    text = text,
                    style = textStyle,
                    color = mContentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AppCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) = CircularProgressIndicator(
    modifier = modifier.size(16.dp),
    color = color,
    strokeWidth = 2.dp
)

@Composable
fun AppBottomSheetColumn(
    modifier: Modifier = Modifier,
    fullExpand: Boolean = false,
    fraction: Float = 0.9f,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (fullExpand) {
                    Modifier.wrapContentHeight()
                } else {
                    Modifier
                        .wrapContentHeight()
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * fraction)
                }
            )
    ) {

        Box(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
                .wrapContentSize()
                .width(32.dp)
                .height(3.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.disable)
        )

        content()
    }
}

@Composable
fun AppBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    hostState: SnackbarHostState,
    onRefresh: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        content()

        AppSnackBarHost(
            hostState = hostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            onRefresh = onRefresh
        )
    }
}

@Composable
fun AppIconButton(
    iconResource: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isSmall: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .wrapContentSize()
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false, radius = ExtraLargeRadius),
            onClick = onClick
        )
        .padding(if (isSmall) 8.dp else 16.dp),
    contentAlignment = Alignment.Center
) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(id = iconResource),
        contentDescription = null,
        tint = color
    )
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.w500.x3,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    title: String? = null,
    isSubTitle: Boolean = false,
    subTitleResource: Int = R.string.label_optional,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.large,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTextColor = MaterialTheme.colorScheme.onDisable,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        cursorColor = MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
        errorBorderColor = MaterialTheme.colorScheme.error,
        leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onDisable,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onDisable,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor = MaterialTheme.colorScheme.onDisable,
        errorLabelColor = MaterialTheme.colorScheme.error,
        placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onDisable
    ),
    layoutDirection: LayoutDirection? = null
) {
    Column(
        modifier = modifier
    ) {

        title?.let {

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = buildAnnotatedString {

                    withStyle(
                        MaterialTheme.typography.w400.x2.toSpanStyle()
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                    ) {
                        append(title)
                    }

                    if (isSubTitle) {

                        withStyle(
                            MaterialTheme.typography.w400.x1.toSpanStyle()
                                .copy(
                                    color = MaterialTheme.colorScheme.onDisable
                                )
                        ) {
                            append(" ")
                            append("(${stringResource(id = subTitleResource)})")
                        }
                    }
                }
            )
        }

        CompositionLocalProvider(
            LocalLayoutDirection provides (layoutDirection ?: LocalLayoutDirection.current)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                interactionSource = interactionSource,
                shape = shape,
                colors = colors
            )
        }

        AnimatedVisibility(
            visible = isError && !errorMessage.isNullOrEmpty()
        ) {
            errorMessage?.let {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = it,
                    style = MaterialTheme.typography.w400.x2,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun AppTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.w500.x3,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    title: String? = null,
    isSubTitle: Boolean = false,
    subTitleResource: Int = R.string.label_optional,
    isError: Boolean = false,
    errorMessage: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.large,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTextColor = MaterialTheme.colorScheme.onDisable,
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        cursorColor = MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
        disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
        errorBorderColor = MaterialTheme.colorScheme.error,
        leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLeadingIconColor = MaterialTheme.colorScheme.onDisable,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = MaterialTheme.colorScheme.onDisable,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor = MaterialTheme.colorScheme.onDisable,
        errorLabelColor = MaterialTheme.colorScheme.error,
        placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledPlaceholderColor = MaterialTheme.colorScheme.onDisable
    ),
    layoutDirection: LayoutDirection? = null
) {
    Column(
        modifier = modifier
    ) {

        title?.let {

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = buildAnnotatedString {

                    withStyle(
                        MaterialTheme.typography.w400.x2.toSpanStyle()
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                    ) {
                        append(title)
                    }

                    if (isSubTitle) {

                        withStyle(
                            MaterialTheme.typography.w400.x1.toSpanStyle()
                                .copy(
                                    color = MaterialTheme.colorScheme.onDisable
                                )
                        ) {
                            append(" ")
                            append("(${stringResource(id = subTitleResource)})")
                        }
                    }
                }
            )
        }

        CompositionLocalProvider(
            LocalLayoutDirection provides (layoutDirection ?: LocalLayoutDirection.current)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                interactionSource = interactionSource,
                shape = shape,
                colors = colors
            )
        }

        AnimatedVisibility(
            visible = isError && !errorMessage.isNullOrEmpty()
        ) {
            errorMessage?.let {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = it,
                    style = MaterialTheme.typography.w400.x2,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppSwipeRefresh(
    refreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    pullRefreshState: PullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    ),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {

        content()

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            scale = true
        )
    }
}
