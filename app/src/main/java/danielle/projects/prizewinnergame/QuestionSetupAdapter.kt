package danielle.projects.prizewinnergame
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class QuestionSetupAdapter(private var questionEntities: ArrayList<QuestionEntity>, private val context: Context) : RecyclerView.Adapter<QuestionSetupAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the question_setup_view_design view
        // that is used to hold list of question entities
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_setup_view_design, parent, false)
        return ViewHolder(view)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the image to the imageview from itemHolder class
        val questionEntity = questionEntities[position]
        val imageHandler = ImageHandler()
        val imageLoaded: Boolean = imageHandler.loadImage(holder.imageViewQuestionImage, "Question", questionEntity.id)
        if (!imageLoaded)
        {
            holder.imageViewQuestionImage.setImageResource(R.drawable.question_mark)
        }
        // sets the text to the textview from itemHolder class
        holder.questionId = questionEntity.id
        holder.textViewQuestion.text = questionEntity.question
        holder.textViewChoiceA.text = questionEntity.choiceA
        holder.textViewChoiceB.text = questionEntity.choiceB
        holder.finalQuestion = questionEntity.finalQuestion
        var correctAnswer: String? = null
        if (questionEntity.correctAnswer == 0)
        {
            correctAnswer = "A"
        }
        else if (questionEntity.correctAnswer == 1)
        {
            correctAnswer = "B"
        }
        holder.textViewCorrectAnswer.text = correctAnswer
        /* if not the final question or the non-deletable timed questions
        (the first 9) remove the delete button for this question */
        if (questionEntity.id <= Constants.NUMBER_OF_TIMED_QUESTIONS + 1)
        {
            holder.btnDelete?.visibility = View.GONE
        }
        else
        {
            holder.btnDelete?.setOnClickListener {
                holder.itemView.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    (context as SetupQuizBasicsActivity).deleteRecord(context, questionEntity)

                }
            }
        }
    }

    // return the number of the items in the list of question entities
    override fun getItemCount(): Int {
        return questionEntities.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var btnDelete: Button? = itemView.findViewById(R.id.btnDeleteQuestion)
        var questionId = -1
        var finalQuestion = false
        val imageViewQuestionImage: ImageView = itemView.findViewById(R.id.imageViewQuestionImage)
        val textViewQuestion: TextView = itemView.findViewById(R.id.textViewQuestionTitle)
        val textViewChoiceA: TextView = itemView.findViewById(R.id.textViewQuestionChoiceA)
        val textViewChoiceB: TextView = itemView.findViewById(R.id.textViewQuestionChoiceB)
        val textViewCorrectAnswer: TextView = itemView.findViewById(R.id.textViewCorrectAnswer)
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(v?.context, EditQuestionActivity::class.java)
            Toast.makeText(v?.context, "Q ID is $questionId", Toast.LENGTH_SHORT).show()
            intent.putExtra(Constants.QUESTION_ID_EXTRA, (questionId).toString())
            intent.putExtra(Constants.IS_FINAL_QUESTION_FLAG, finalQuestion)
            v?.context?.startActivity(intent)
        }
    }
}