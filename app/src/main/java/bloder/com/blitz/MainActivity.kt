package bloder.com.blitz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import bloder.com.blitzcore.enableWhen
import bloder.com.blitzcore.enableWhenUsing
import bloder.com.blitzcore.mask.withMask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buildCustomFormValidation()
        signup.setOnClickListener {
            Toast.makeText(this, "Signed up", Toast.LENGTH_LONG).show()
        }
    }

    private fun showSuccessCaseFor(successView: View, errorView: View) {
        successView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    private fun showErrorCaseFor(successView: View, errorView: View) {
        successView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    private fun buildCustomFormValidation() = signup.enableWhenUsing(CustomValidationExample()) {
        email_field.isEmail() onValidationSuccess {
            showSuccessCaseFor(email_success_icon, email_error_icon)
        } onValidationError {
            showErrorCaseFor(email_success_icon, email_error_icon)
        }
        document_field.isFilled() withMask "###.###.###-#"
        terms.isAccepted()
    }

    private fun buildFormValidation() = signup.enableWhen {
        email_field.isEmail()
        document_field.isFilled()
    }
}