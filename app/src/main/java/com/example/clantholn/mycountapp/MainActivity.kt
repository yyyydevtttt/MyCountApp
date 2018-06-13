package com.example.clantholn.mycountapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Log.e
import android.view.GestureDetector
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // X軸最低スワイプ距離
    private val SWIPE_MIN_DISTANCE = 50
    // X軸最低スワイプスピード
    private val SWIPE_THRESHOLD_VELOCITY = 150

    // タッチイベントを処理するためのインターフェース
    private var mGestureDetector: GestureDetector? = null

    // パンのカウンタ
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  タッチイベントハンドラの初期化
        mGestureDetector = GestureDetector(this, mOnGestureListener)

        // リセットボタン押下時のイベント
        // ※ ボタン押下イベントは、setOnClickListener するか、ボタンに onClick 属性をつけて関数を用意する
        resetButton.setOnClickListener {
            // カウンターの初期化
            counter = 0
            // カウント表示エリアの初期化
            textView2.text = ""
            // メッセージエリアの初期化
            messageView.text = ""
        }
    }

    // タッチイベント
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector!!.onTouchEvent(event)
    }

    // タッチイベントのリスナー
    private val mOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        // フリックイベント
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            try {
                // X軸の移動速度が指定値より遅い、または、Y軸の移動速度が指定値より遅い
                if (Math.abs(velocityX) <= SWIPE_THRESHOLD_VELOCITY || Math.abs(velocityY) <= SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("onFling", "スワイプ速度が遅いです")
                    messageView.text = "もっと激しく！"

                    // 開始位置から終了位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    // スワイプ方向をログ出力
                    Log.d("onFling", "左から右")
                    // カウントアップ
                    counter++
                    // メッセージエリアを初期化
                    messageView.text = ""

                    // 開始位置から終了位置の移動距離が指定値より大きい
                    // X軸の移動速度が指定値より大きい
                } else if (e1.x - e2.x > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
                    // スワイプ方向をログ出力
                    Log.d("onFling", "右から左")
                    // 0 でなければ、カウントダウン
                    if (counter != 0) {
                        counter--
                    }
                    // メッセージエリアを初期化
                    messageView.text = ""
                }

                // カウントを表示
                textView2.text = counter.toString()

            } catch (e: Exception) {
                // エラーログの出力
                e("onFling", e.message)
            }

            return false
        }
        // 長押し
        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
        }
    }


}
