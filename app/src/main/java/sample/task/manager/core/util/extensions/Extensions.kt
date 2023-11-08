package sample.task.manager.core.util.extensions

import android.content.Context
import sample.task.manager.core.util.ValidateKeys
import sample.task.manager.core.model.NetworkViewState

fun Context.parseValidationErrorMessage(errorList: ArrayList<ValidateKeys>?): String? {
    return if (errorList.isNullOrEmpty()) null else getString(errorList.first().value)
}

fun Context.parseServerErrorMessage(networkViewState: NetworkViewState): String {
    return networkViewState.serverErrorMessage ?: getString(networkViewState.errorMessage)
}
