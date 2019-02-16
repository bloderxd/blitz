package bloder.com.blitz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import bloder.com.core.enableWhen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.enableWhen {
            edit1.isEmail()
            edit2.isFilled()
            edit3.isFollowingRegex("[^-?0-9]+")
        }
    }
}
