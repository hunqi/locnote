package com.rs.locnote.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import java.util.*

class SpeechUtils(context: Context) : SpeakListener, UtteranceProgressListener() {
    private var mContext: Context
    private lateinit var mTextToSpeech: TextToSpeech
    private var isSuccess: Boolean = true

    init {
        mContext = context.applicationContext
        mTextToSpeech = TextToSpeech(mContext) {
            //系统语音初始化成功
            if (it == TextToSpeech.SUCCESS) {//操作成功
                val result = mTextToSpeech.setLanguage(Locale.CHINA)
                mTextToSpeech.setPitch(1.0f) // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                mTextToSpeech.setSpeechRate(1.0f)
                mTextToSpeech.setOnUtteranceProgressListener(this)

                // TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失
                // TextToSpeech.LANG_NOT_SUPPORTED：不支持
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    //系统不支持中文播报
                    isSuccess = false
                }
            }
        }
    }

    /**
     * 开始播报
     * @param text
     */
    override fun startSpeak(text: String) {
        if (!isSuccess) {
            Toast.makeText(mContext, "系统不支持中文播报", Toast.LENGTH_SHORT).show()
            return
        }

        if (mTextToSpeech != null) {
            // QUEUE_ADD：该模式下会把新的语音任务放到语音任务之后，等前面的语音任务执行完了才会执行新的语音任务。
            // QUEUE_FLUSH：该模式下在有新任务时候会清除当前语音任务，执行新的语音任务
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null)
        }
    }

    /**
     * 结束播报
     */
    override fun stopSpeak() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop()
        }
    }

    override fun onStart(utteranceId: String?) {
        TODO("Not yet implemented")
    }

    override fun onDone(utteranceId: String?) {
        TODO("Not yet implemented")
    }

    override fun onError(utteranceId: String?) {
        TODO("Not yet implemented")
    }
}