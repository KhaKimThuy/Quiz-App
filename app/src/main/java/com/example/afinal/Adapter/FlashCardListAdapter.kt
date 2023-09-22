package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R

class FlashCardListAdapter (
    private val cardList:ArrayList<FlashCardDomain>,
) : RecyclerView.Adapter<MyViewHolderCard>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderCard {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.viewholder_flashcard, parent, false)
        return MyViewHolderCard(listItem)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderCard, position: Int) {
        holder.eng_lang.text = cardList[position].engLanguage
        holder.vn_lang.text = cardList[position].vnLanguage
//        val drawableResourceId : Int = holder.itemView.resources.getIdentifier(cardList[position].picUrl, "drawable", holder.itemView.context.packageName)
//
//        Glide.with(holder.itemView.context)
//            .load(drawableResourceId)
//            .transform(GranularRoundedCorners(30F, 30F, 0F, 0F))
//            .into(holder.avatar)

//        holder.itemView.setOnClickListener{
//            val intent = Intent(holder.itemView.context, DetailTopicActivity::class.java)
//            intent.putExtra("object", cardList[position])
//            holder.itemView.context.startActivity(intent)
//        }

//        holder.itemView.setOnClickListener{
//            var scale = applicationContext.resources.displayMetrics.density
//            val front = holder.eng_lang
//            val back = holder.vn_lang
//            lateinit var front_animation: AnimatorSet
//            lateinit var back_animation: AnimatorSet
//
//            front.cameraDistance = 8000 * scale
//            back.cameraDistance = 8000 * scale
//
//
//            // Now we will set the front animation
//            front_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
//            back_animation = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet
//
//            // Now we will set the event listener
//            front.setOnClickListener{
//                    front_animation.setTarget(front);
//                    back_animation.setTarget(back);
//                    front_animation.start()
//                    back_animation.start()
//                }
//            back.setOnClickListener{
//                    front_animation.setTarget(back)
//                    back_animation.setTarget(front)
//                    back_animation.start()
//                    front_animation.start()
//            }
//        }
    }
}
class MyViewHolderCard(view : View): RecyclerView.ViewHolder(view){
    val eng_lang = view.findViewById<TextView>(R.id.textView_engLang)
    val vn_lang = view.findViewById<TextView>(R.id.textView_vnLang)
}