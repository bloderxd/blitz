package bloder.com.core

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import bloder.com.core.textwatcher.BlitzTextWatcher
import bloder.com.core.validation.Validations

private typealias Validated = Boolean
private typealias Condition = (String) -> Validated
internal typealias ValidationConditionAction = (EditText) -> Unit

class FormValidator(private val view: View) {

    private val conditions: MutableMap<EditText, Validated> = mutableMapOf()
    private val validationTextWatchers: MutableMap<EditText, TextWatcher> = mutableMapOf()
    private val successConditions: MutableMap<EditText, ValidationConditionAction> = mutableMapOf()

    internal fun bindValidation(editText: EditText, condition: Condition) : EditText {
        conditions[editText] = condition(editText.text.toString())
        editText.removeTextChangedListener(validationTextWatchers[editText])
        validationTextWatchers[editText] = BlitzTextWatcher {
            conditions[editText] = condition(it)
            checkIfViewCanBeEnable()
        }
        editText.addTextChangedListener(validationTextWatchers[editText])
        return editText
    }

    internal fun bindSuccessConditionAction(editText: EditText, action: ValidationConditionAction) : EditText {
        successConditions[editText] = action
        return editText
    }

    internal fun bindErrorValidationAction(editText: EditText, action: ValidationConditionAction) : EditText {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && conditions[editText] == false) action(editText)
        }
        return editText
    }

    fun checkIfViewCanBeEnable() {
        view.isEnabled = conditions.filter {
            if (it.value) successConditions[it.key]?.invoke(it.key)
            !it.value
        }.isEmpty()
    }
}

fun View.enableWhen(conditions: Validations.() -> Unit) = FormValidator(this).also {
    Validations(it).conditions()
    it.checkIfViewCanBeEnable()
}