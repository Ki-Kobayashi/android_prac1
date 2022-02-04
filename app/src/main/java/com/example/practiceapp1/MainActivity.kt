package com.example.practiceapp1

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    //    後から何か入れたいが、何を入れていいかわからないときは、「lateinit」をつかう
//   Int?といった形で、nullが入るときは「?」を型の後につけることもできるが、極力使わず以下のように書く
    private lateinit var soundPool: SoundPool

    //    基本型（数字・文字列・真偽）では,lateinitが使えない。初期値を入れる必要がある
    private var soundHakushuId = 0
    private var soundSmileId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")
//        ①ID：buttonがクリックされたときに、onClickメソッドを呼ぶ奴（コールバックメソッド）
//       button.setOnClickListener(this)

//        ②メソッドとしてクリック処理を切り分けてセットする方法
//        button.setOnClickListener { mButtonClick() }
        button1.setOnClickListener { soundPool.play(soundHakushuId, 1.0f, 1.0f, 0, 0, 1.0f) }
        button2.setOnClickListener { soundPool.play(soundSmileId, 1.0f, 1.0f, 0, 0, 1.0f) }
        button3.setOnClickListener { soundPool.play(soundHakushuId, 1.0f, 1.0f, 0, 0, 1.0f) }
        button4.setOnClickListener { soundPool.play(soundSmileId, 1.0f, 1.0f, 0, 0, 1.0f) }
        button5.setOnClickListener { soundPool.play(soundHakushuId, 1.0f, 1.0f, 0, 0, 1.0f) }
        button6.setOnClickListener { soundPool.play(soundSmileId, 1.0f, 1.0f, 0, 0, 1.0f) }
    }

    override fun onResume() {
        super.onResume()
        setButtonSound()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        // サウンドプールの後片付け
        soundPool.release()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }


    private fun setButtonSound() {
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //オーディオの属性をしていることで、ハードウェアを効率的に使って、良い音を出すことができまっせってこと。それができるのがLOLIPOP以降
            SoundPool.Builder().setAudioAttributes(
                AudioAttributes.Builder()
                    //AudioAttributes.USAGE_MEDIAとか、音楽とか、用途に応じて音量など良い感じに鳴らすように設定できるやつ
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
                .setMaxStreams(1) // 同時にならす音の数
                .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        soundHakushuId = soundPool.load(this, R.raw.hakushu, 1)
        soundSmileId = soundPool.load(this, R.raw.smile, 1)
    }
}