package ua.graviton.isida.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.graviton.isida.R
import ua.graviton.isida.databinding.ActivityRunBinding

private const val KEY_DATA = "data"

fun Context.intentRun(data: ShortArray): Intent = Intent(this, RunActivity::class.java).apply { putExtra(KEY_DATA, data) }

class RunActivity : AppCompatActivity(R.layout.activity_run) {
    private val binding by viewBinding(ActivityRunBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}