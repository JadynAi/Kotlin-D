package com.jadynai.kotlindiary.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.jadynai.kotlindiary.databinding.ActivityVideoPlayerCompatBinding

/**
 *Jairett since 2022/6/15
 */
class VideoPlayerCompatActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityVideoPlayerCompatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val _binding = ActivityVideoPlayerCompatBinding.inflate(layoutInflater, null, false)
        viewBinding = _binding
        setContentView(_binding.root)
        val url = "http://p/1/726d/0/482000-121000-0-0-0.m3u8"
        Log.d("cece", "onViewCreated url: $url")
        initVideoPlayer(url)
    }

    private var mediaPlayer: MediaPlayer? = null
    private var surface: Surface? = null
    private val audioManager by lazy {
        this.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun initVideoPlayer(url: String) {
//        audioManager.requestAudioFocus({
//
//        }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnErrorListener { iMediaPlayer, i, i2 ->
            Log.d("cece", "on error : $i $i2")
            false
        }
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
            prepareSurface()
        }
        mediaPlayer?.setOnInfoListener { iMediaPlayer, i, i2 ->
            Log.d("cece", "setOnInfoListener: $i $i2")
            false
        }
        mediaPlayer?.setOnBufferingUpdateListener { iMediaPlayer, i ->
            Log.d("cece", "setOnBufferingUpdateListener: $i")
        }
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer?.setDataSource(this, Uri.parse(url), hashMapOf())
        mediaPlayer?.isLooping = false
        mediaPlayer?.prepareAsync()
    }

    private fun prepareSurface() {
        val textureView = TextureView(this)
        viewBinding.videoPlayer.addView(textureView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT))
        textureView.keepScreenOn = true
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                surface = Surface(p0)
                mediaPlayer?.setSurface(surface)
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                surface = null
                mediaPlayer?.release()
                mediaPlayer = null
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
            }

        }
    }
}
