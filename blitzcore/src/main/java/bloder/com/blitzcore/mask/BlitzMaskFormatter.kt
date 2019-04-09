package bloder.com.blitzcore.mask

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.util.*

private const val DEFAULT_MASK_CHAR = '#'

open class BlitzMaskFormatter(private val editText: EditText, masks: List<Mask>) : TextWatcher {

    private val masks: List<Mask>

    private var isUpdating: Boolean = false
    private var selectionEnd: Int = 0
    private var lastTextLength: Int = 0

    constructor(editText: EditText, mask: String) : this(editText, mask, DEFAULT_MASK_CHAR)

    private constructor(editText: EditText, mask: String, maskChar: Char) : this(editText,
            listOf<Mask>(Mask(mask, maskChar, Mask.MAX_CHAR_UNLIMITED)))

    init {
        if (masks.isEmpty()) {
            throw IllegalArgumentException("You must specify at least one mask.")
        }
        this.masks = Collections.unmodifiableList(masks)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (!isUpdating) {
            selectionEnd = editText.selectionEnd
            lastTextLength = editText.text.length
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (isUpdating) {
            isUpdating = false
            return
        }
        Log.d("MASK_HUE", "LOGGED: $s")
        val unmasked = unmask(s.toString())
        val mask = getCorrectMask(unmasked.length)
        val updatedMask: String
        updatedMask = mask?.mask(unmasked) ?: unmasked
        isUpdating = true
        editText.setText(updatedMask)
        editText.setSelection(Math.max(0, selectionEnd - (lastTextLength - updatedMask.length)))
    }

    private fun getCorrectMask(length: Int): Mask? {
        var correctMask: Mask? = null
        var unlimitedCharsMask: Mask? = null
        var oldDiff = Integer.MAX_VALUE
        for (mask in masks) {
            val newDiff = mask.maxCharsToApply - length
            if (newDiff in 0..(oldDiff - 1)) {
                correctMask = mask
                oldDiff = correctMask.maxCharsToApply - length
            }
            if (mask.maxCharsToApply == Mask.MAX_CHAR_UNLIMITED) {
                unlimitedCharsMask = mask
            }
        }
        if (correctMask == null && unlimitedCharsMask != null) {
            correctMask = unlimitedCharsMask
        }
        return correctMask
    }

    private fun unmask(string: String): String = string.replace("[^0-9]".toRegex(), "")

    class Mask @JvmOverloads constructor(private val maskString: String,
                                         private val maskChar: Char = DEFAULT_MASK_CHAR, val maxCharsToApply: Int = MAX_CHAR_UNLIMITED) {

        constructor(maskString: String, maxCharsToApply: Int) : this(maskString, DEFAULT_MASK_CHAR, maxCharsToApply)

        fun mask(unmaskedString: String): String {
            var nextUnmaskedCharIndex = 0
            val masked = StringBuilder()
            var i = 0
            while (i < maskString.length && nextUnmaskedCharIndex < unmaskedString.length) {
                val maskStringChar = maskString[i]
                if (maskStringChar == maskChar) {
                    masked.append(unmaskedString[nextUnmaskedCharIndex])
                    nextUnmaskedCharIndex++
                } else {
                    masked.append(maskStringChar)
                }
                i++
            }
            return masked.toString()
        }

        companion object {

            const val MAX_CHAR_UNLIMITED = -1
        }
    }
}
