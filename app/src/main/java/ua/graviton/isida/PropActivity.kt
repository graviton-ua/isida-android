package ua.graviton.isida

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import ua.graviton.isida.databinding.ActivityPropBinding

class PropActivity : AppCompatActivity(R.layout.activity_prop) {
    private val binding by viewBinding(ActivityPropBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}