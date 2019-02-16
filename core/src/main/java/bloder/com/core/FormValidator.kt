package bloder.com.core

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import bloder.com.core.textwatcher.BlitzTextWatcher
import bloder.com.core.validation.Validations

private typealias Validated = Boolean
private typealias Condition = (String) -> Validated

class FormValidator(private val view: View) {

    private val conditions: MutableMap<EditText, Validated> = mutableMapOf()
    private val textWatchers: MutableMap<EditText, TextWatcher> = mutableMapOf()

    fun buildValidation(editText: EditText, condition: Condition) : EditText {
        conditions[editText] = condition(editText.text.toString())
        editText.removeTextChangedListener(textWatchers[editText])
        textWatchers[editText] = BlitzTextWatcher {
            conditions[editText] = condition(it)
            checkIfViewCanBeEnable()
        }
        editText.addTextChangedListener(textWatchers[editText])
        return editText
    }

    fun checkIfViewCanBeEnable() {
        view.isEnabled = conditions.filter { !it.value }.isEmpty()
    }
}

fun View.enableWhen(conditions: Validations.() -> Unit) = FormValidator(this).also {
    Validations(it).conditions()
    it.checkIfViewCanBeEnable()
}