package hr.ferit.ivansertic.lv5_z1

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var soundPool : SoundPool

    private var loaded : Boolean = false

    var soundMap : HashMap<Int,Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setUpUI()

        this.loadSounds()
    }

    private fun loadSounds() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            this.soundPool = SoundPool.Builder().setMaxStreams(10).build()
        }else{
            this.soundPool = SoundPool(10,AudioManager.STREAM_MUSIC,0)
        }

        this.soundPool.setOnLoadCompleteListener { _, _, _ -> loaded = true }
        this.soundMap[R.raw.old] = this.soundPool.load(this, R.raw.old, 1)
        this.soundMap[R.raw.harley] = this.soundPool.load(this, R.raw.harley, 1)
        this.soundMap[R.raw.formula1] = this.soundPool.load(this, R.raw.formula1, 1)
    }

    private fun setUpUI() {
        this.oldCar.setOnClickListener(this)

        this.harley.setOnClickListener(this)

        this.f1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(this.loaded == false ) return

        when (v?.getId()){
            R.id.oldCar -> playSound(R.raw.old)
            R.id.harley -> playSound(R.raw.harley)
            R.id.f1 -> playSound(R.raw.formula1)
        }
    }

    private fun playSound(sound: Any) {
        val soundId = this.soundMap[sound] ?: 0
        this.soundPool.play(soundId, 1f,1f,1,0,1f)
    }


}