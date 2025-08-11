
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.R

class VideoAdapter(private val context: Context, private val videoList: List<Int>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoView: VideoView = itemView.findViewById(R.id.videoViewPager)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoResource = videoList[position]
        val uri = Uri.parse("android.resource://${context.packageName}/$videoResource")
        holder.videoView.setVideoURI(uri)
        holder.videoView.setOnPreparedListener {
            it.isLooping = true // Pour boucler la vidéo
        }
        holder.videoView.start() // Démarrer la vidéo
    }

    override fun getItemCount(): Int = videoList.size
}
