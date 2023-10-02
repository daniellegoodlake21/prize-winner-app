package danielle.projects.prizewinnergame
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PrizeSetupAdapter(private val prizeEntities: List<PrizeEntity>) : RecyclerView.Adapter<PrizeSetupAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the prize_setup_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.prize_setup_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // sets the image to the imageview from our itemHolder class
        val prizeEntity = prizeEntities[position]
        val imageHandler = ImageHandler()
        val imageLoaded = imageHandler.loadImage(holder.imageViewPrizeImage, "Prize", prizeEntity.prizeId)
        if (!imageLoaded)
        {
            holder.imageViewPrizeImage.setImageResource(R.drawable.gift)
        }
        // sets the text to the textview from our itemHolder class
        holder.prizeId = prizeEntity.prizeId
        holder.textViewPrizeTitle.text = prizeEntity.title
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return prizeEntities.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var prizeId = -1
        val imageViewPrizeImage: ImageView = itemView.findViewById(R.id.imageViewPrizeImage)
        val textViewPrizeTitle: TextView = itemView.findViewById(R.id.textViewPrizeTitle)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(v?.context, EditPrizeActivity::class.java)
            intent.putExtra("prizeId", prizeId.toString())
            v?.context?.startActivity(intent)
        }
    }

}