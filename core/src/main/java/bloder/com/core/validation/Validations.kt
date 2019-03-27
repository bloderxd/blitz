package bloder.com.core.validation

import android.widget.EditText
import bloder.com.core.FormValidator
import bloder.com.core.ValidationConditionAction

open class Validations(private val validator: FormValidator) {

    fun EditText.isFollowingRegex(pattern: String) : EditText = validator.bindValidation(this) { if (pattern.isNotEmpty() && pattern.isNotBlank()) {
        it.matches(Regex(pattern))
    } else {
        it.isNotEmpty() && it.isNotBlank()
    }}

    fun EditText.isEmail() : EditText = validator.bindValidation(this) {
        it.matches(Regex("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$"))
    }

    fun EditText.isFilled() : EditText = validator.bindValidation(this) { it.isNotEmpty() }

    infix fun EditText.onValidationSuccess(action: ValidationConditionAction) : EditText =
            validator.bindSuccessConditionAction(this, action)

    infix fun EditText.onValidationError(action: ValidationConditionAction) : EditText =
            validator.bindErrorValidationAction(this, action)
}