package danielle.projects.prizewinnergame

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PrizeShowcaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prize_showcase)

        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener{
            val intent = Intent(this, PrizeGridActivity::class.java)
            startActivity(intent)
        }

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener{
            val intent = Intent(this, QuizInstructions::class.java)
            startActivity(intent)
            finish()
        }

        displayPrizeImage(0)
    }

    private fun displayPrizeImage(prizeIndex: Int)
    {
        val imageView: ImageView = findViewById(R.id.imageViewPrizeShowcase)
        val imageHandler = ImageHandler()
        imageHandler.loadImage(imageView, "Prize", prizeIndex)

        // handle progress bar
        val progressBar: ProgressBar = findViewById(R.id.progressBarPrizeShowcase)
        progressBar.progress = prizeIndex + 1

        // set prize title text
        val prizeFileHandler = PrizeFileHandler(filesDir.absolutePath)
        val prizeTitle: String = prizeFileHandler.readPrize(prizeIndex).title
        val prizeTitleTextView: TextView = findViewById(R.id.textViewPrizeTitle)
        prizeTitleTextView.text = prizeTitle

        // handle animation
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 1000

        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.startOffset = 3000
        fadeOut.duration = 1000

        val animation = AnimationSet(false)
        animation.addAnimation(fadeIn)
        animation.addAnimation(fadeOut)

        // loop through the prize images
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                if (Constants.NUMBER_OF_PRIZES - 1 > prizeIndex) {
                    displayPrizeImage(prizeIndex + 1)
                } else {
                    displayPrizeImage(0)
                }
            }

            override fun onAnimationRepeat(animation: Animation) {
                // TODO Auto-generated method stub
            }

            override fun onAnimationStart(animation: Animation) {
                // TODO Auto-generated method stub
            }
        })

        imageView.animation = animation
    }

}