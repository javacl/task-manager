package sample.task.manager.features.task.domain.validation

import sample.task.manager.core.util.ValidateKeys

class CreateTaskAlarmValidationError {

    val secondsLaterFromNow: ArrayList<ValidateKeys> = ArrayList()

    fun isValid(): Boolean {
        return secondsLaterFromNow.size <= 0
    }
}
