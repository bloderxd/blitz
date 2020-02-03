package bloder.com.blitzcore.mask

import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText

private object MaskDsl {

    private val textWatchers: MutableMap<EditText, TextWatcher> = mutableMapOf()

    fun injectMask(editText: EditText, textWatcher: TextWatcher) {
        editText.removeTextChangedListener(textWatchers[editText])
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        textWatcher.let {
            textWatchers[editText] = it
            editText.addTextChangedListener(it)
        }
    }
}

infix fun EditText.withMask(mask: String): EditText = if (mask.isEmpty() && mask.isBlank()) {
    this
} else {
    (MaskDsl.injectMask(this, BlitzMaskFormatter(this, mask))).let { this }
}


infix fun EditText.withMaskSequence(masks: MutableList<String>) = MaskDsl.injectMask(this, BlitzMaskFormatter(this, masks.flatMap {
    listOf(
            BlitzMaskFormatter.Mask(it, it.filter { char -> char == '#' }.length))
}))

infix fun String.then(mask: String): MutableList<String> = mutableListOf(this, mask)

infix fun MutableList<String>.then(mask: String): MutableList<String> = this.also { it.add(mask) }