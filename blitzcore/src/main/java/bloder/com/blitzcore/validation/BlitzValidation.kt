package bloder.com.blitzcore.validation

import android.view.View
import bloder.com.blitzcore.FormValidator
import bloder.com.blitzcore.Validated
import bloder.com.blitzcore.ValidationConditionAction

abstract class BlitzValidation {

    private lateinit var validator: FormValidator

    internal fun initValidator(validator: FormValidator) {
        this.validator = validator
    }

    protected fun <T : View> bindViewValidation(view: T, condition: (T) -> Validated) : T = validator.bindValidation(view, condition)
    protected fun bindSuccessConditionAction(view: View, action: ValidationConditionAction) : View = validator.bindSuccessConditionAction(view, action)
    protected fun bindErrorValidationAction(view: View, action: ValidationConditionAction) : View = validator.bindErrorValidationAction(view, action)
}

internal fun <T : BlitzValidation> T.with(validator: FormValidator) : T {
    this.initValidator(validator)
    return this
}