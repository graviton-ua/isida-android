package ua.graviton.isida

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.graviton.isida.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity(R.layout.activity_info) {
    private val binding by viewBinding(ActivityInfoBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}