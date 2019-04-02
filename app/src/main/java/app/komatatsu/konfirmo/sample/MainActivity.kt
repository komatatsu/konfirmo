package app.komatatsu.konfirmo.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.komatatsu.konfirmo.Konfirmo
import app.komatatsu.konfirmo.rules.Rule
import app.komatatsu.konfirmo.rules.RuleSet
import app.komatatsu.konfirmo.rules.ValidationType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Konfirmo(this)
            .addSaveButton(submit)
            .addRuleSet(RuleSet(check1)
                            .addRule(Rule.REQUIRED)
                            .addRule(Rule(ValidationType.MAX_LENGTH, 2))
            )
            .addRuleSet(RuleSet(check2)
                            .addRule(Rule.REQUIRED)
                            .addRule(Rule(ValidationType.MAX_LENGTH, 2))
            )
            .addRuleSet(RuleSet(check3)
                            .addRule(Rule.REQUIRED)
                            .addRule(Rule(ValidationType.MAX_LENGTH, 2))
            )
            .initialize()

        submit.setOnClickListener {
            Log.v(javaClass.simpleName, "submit")
        }
    }
}
