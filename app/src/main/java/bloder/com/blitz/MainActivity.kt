package bloder.com.blitz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import bloder.com.core.enableWhen
import bloder.com.core.enableWhenUsing
import bloder.com.core.mask.BlitzMaskFormatter
import bloder.com.core.mask.then
import bloder.com.core.mask.withMask
import bloder.com.core.mask.withMaskSequence
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit3 withMaskSequence("##/##" then "####-####" then "#####>###<#####")

        button.enableWhenUsing(CustomValidationExample()) {
            edit1.isEmail() onValidationSuccess {
                arrow1.visibility = View.VISIBLE
            } onValidationError {
                arrow1.visibility = View.GONE
            }
            edit2.isFilled() withMask "###.##-####"
            checkbox.wasChosen()
        }
    }
}
