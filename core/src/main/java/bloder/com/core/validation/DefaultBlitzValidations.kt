package bloder.com.core.validation

import android.view.View
import android.widget.EditText
import bloder.com.core.ValidationConditionAction

open class DefaultBlitzValidations : BlitzValidation() {

    fun EditText.isFollowingRegex(pattern: String) : EditText = bindViewValidation(this) { if (pattern.isNotEmpty() && pattern.isNotBlank()) {
        it.text.isNotEmpty() && it.text.matches(Regex(pattern))
    } else {
        it.text.isNotEmpty() && it.text.isNotBlank()
    }}

    fun EditText.isFilled() : EditText = bindViewValidation(this) { it.text.isNotEmpty() }

    fun EditText.isEmail() : EditText = bindViewValidation(this) {
        it.text.isNotEmpty() && it.text.matches(Regex("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$"))
    }

    infix fun View.onValidationSuccess(action: ValidationConditionAction) : View = bindSuccessConditionAction(this, action)

    infix fun View.onValidationError(action: ValidationConditionAction) : View = bindErrorValidationAction(this, action)
}