package bloder.com.core.mask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import java.util.Collections

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

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (isUpdating) {
            isUpdating = false
            return
        }
        val unmasked = unmask(s.toString())
        val mask = getCorrectMask(unmasked.length)
        val updatedMask: String
        updatedMask = mask?.mask(unmasked) ?: unmasked
        isUpdating = true
        editText.setText(updatedMask)
        editText.setSelection(Math.max(0, selectionEnd - (lastTextLength - updatedMask.length)))
    }

    override fun afterTextChanged(s: Editable) {}

    private fun getCorrectMask(length: Int): Mask? {
        var correctMask: Mask? = null
        var unlimitedCharsMask: Mask? = null
        var oldDiff = Integer.MAX_VALUE
        for (mask in masks) {
            val newDiff = mask.mMaxCharsToApply - length
            if (newDiff in 0..(oldDiff - 1)) {
                correctMask = mask
                oldDiff = correctMask.mMaxCharsToApply - length
            }
            if (mask.mMaxCharsToApply == Mask.MAX_CHAR_UNLIMITED) {
                unlimitedCharsMask = mask
            }
        }
        if (correctMask == null && unlimitedCharsMask != null) {
            correctMask = unlimitedCharsMask
        }
        return correctMask
    }

    fun unmask(string: String): String = string.replace("[^0-9]".toRegex(), "")

    class Mask @JvmOverloads constructor(private val mMaskString: String,
                                         private val mMaskChar: Char = DEFAULT_MASK_CHAR, val mMaxCharsToApply: Int = MAX_CHAR_UNLIMITED) {

        constructor(maskString: String, maxCharsToApply: Int) : this(maskString, DEFAULT_MASK_CHAR, maxCharsToApply)

        fun mask(unmaskedString: String): String {
            var nextUnmaskedCharIndex = 0
            val masked = StringBuilder()
            var i = 0
            while (i < mMaskString.length && nextUnmaskedCharIndex < unmaskedString.length) { val maskStringChar = mMaskString[i]
                if (maskStringChar == mMaskChar) {
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
