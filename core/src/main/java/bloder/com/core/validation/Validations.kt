package bloder.com.core.validation

import android.widget.EditText
import bloder.com.core.FormValidator

open class Validations(private val validator: FormValidator) {

    fun EditText.isFollowingRegex(pattern: String) : EditText = validator.buildValidation(this) { if (pattern.isNotEmpty() && pattern.isNotBlank()) {
        it.matches(Regex(pattern))
    } else {
        it.isNotEmpty() && it.isNotBlank()
    }}

    fun EditText.isEmail() : EditText = validator.buildValidation(this) {
        it.matches(Regex("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$"))
    }

    fun EditText.isFilled() : EditText = validator.buildValidation(this) { it.isNotEmpty() }
}