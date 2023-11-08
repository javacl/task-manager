package sample.task.manager.features.task.domain.validation

import sample.task.manager.core.util.ValidateKeys

class CreateTaskAlarmValidationError {

    val time: ArrayList<ValidateKeys> = ArrayList()

    fun isValid(): Boolean {
        return time.size <= 0
    }
}
