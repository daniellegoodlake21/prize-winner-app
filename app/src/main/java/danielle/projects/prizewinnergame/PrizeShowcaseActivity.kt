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
import danielle.projects.prizewinnergame.databinding.ActivityPrizeShowcaseBinding


class PrizeShowcaseActivity : AppCompatActivity() {

    private var binding: ActivityPrizeShowcaseBinding? = null

    private var btnNext: Button? = null
    private var btnBack: Button? = null
    private var progressBar: ProgressBar? = null
    private var imageView: ImageView? = null
    private var prizeTitleTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrizeShowcaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        btnNext = binding?.btnNext
        btnNext?.setOnClickListener{
            val intent = Intent(this, PrizeGridActivity::class.java)
            startActivity(intent)
        }

        btnBack = binding?.btnBack
        btnBack?.setOnClickListener{
            val intent = Intent(this, QuizInstructions::class.java)
            startActivity(intent)
            finish()
        }

        displayPrizeImage(0)
    }

    private fun displayPrizeImage(prizeIndex: Int)
    {
        imageView = binding?.imageViewPrizeShowcase
        val imageHandler = ImageHandler()
        imageView?.let {
            imageHandler.loadImage(imageView!!, "Prize", prizeIndex)
            // handle progress bar
            progressBar = binding?.progressBarPrizeShowcase
            progressBar?.progress = prizeIndex + 1

            // set prize title text
            val prizeFileHandler = PrizeFileHandler(filesDir.absolutePath)
            val prizeTitle: String = prizeFileHandler.readPrize(prizeIndex).title
            prizeTitleTextView = binding?.textViewPrizeTitle
            prizeTitleTextView?.text = prizeTitle

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

            imageView!!.animation = animation
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}