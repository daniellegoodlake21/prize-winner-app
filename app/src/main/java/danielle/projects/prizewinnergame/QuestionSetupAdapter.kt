package danielle.projects.prizewinnergame
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionSetupAdapter(private val mList: List<QuestionViewModel>) : RecyclerView.Adapter<QuestionSetupAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the question_setup_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_setup_view_design, parent, false)
        return if (itemCount == 1) {
            ViewHolder(view, true)
        } else {
            ViewHolder(view, false)
        }
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the image to the imageview from our itemHolder class
        val questionViewModel = mList[position]
        val imageHandler = ImageHandler()
        val imageLoaded: Boolean = if (itemCount == 1)
        {
            imageHandler.loadImage(holder.imageViewQuestionImage, "Question", -1)
        }
        else
        {
            imageHandler.loadImage(holder.imageViewQuestionImage, "Question", position)
        }
        if (!imageLoaded)
        {
            holder.imageViewQuestionImage.setImageResource(R.drawable.question_mark)
        }
        // sets the text to the textview from our itemHolder class
        holder.textViewQuestion.text = questionViewModel.question
        holder.textViewChoiceA.text = questionViewModel.choiceA
        holder.textViewChoiceB.text = questionViewModel.choiceB
        var correctAnswer: String? = null
        if (questionViewModel.correctAnswer == 0)
        {
            correctAnswer = "A"
        }
        else if (questionViewModel.correctAnswer == 1)
        {
            correctAnswer = "B"
        }
        holder.textViewCorrectAnswer.text = correctAnswer


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View, finalQuestion: Boolean) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        
        val imageViewQuestionImage: ImageView = itemView.findViewById(R.id.imageViewQuestionImage)
        val textViewQuestion: TextView = itemView.findViewById(R.id.textViewQuestionTitle)
        val textViewChoiceA: TextView = itemView.findViewById(R.id.textViewQuestionChoiceA)
        val textViewChoiceB: TextView = itemView.findViewById(R.id.textViewQuestionChoiceB)
        val textViewCorrectAnswer: TextView = itemView.findViewById(R.id.textViewCorrectAnswer)
        private var finalQuestion: Boolean = false
        init{
            this.finalQuestion = finalQuestion
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(v?.context, EditQuestionActivity::class.java)
            if (finalQuestion)
            {
                intent.putExtra(Constants.QUESTION_ID_EXTRA, "-1")
            }
            else
            {
                intent.putExtra(Constants.QUESTION_ID_EXTRA, adapterPosition.toString())
            }
            v?.context?.startActivity(intent)
        }
    }
}