package bloder.com.core.textwatcher

import android.text.Editable
import android.text.TextWatcher

internal open class SimpleTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}

internal open class BlitzTextWatcher(private val block: (newText: String) -> Unit) : SimpleTextWatcher() {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        block(s?.toString() ?: "")
    }
}