package ru.lingstra.avitocopy.presentation.list

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_cars_list.view.*
import ru.lingstra.avitocopy.*
import ru.lingstra.avitocopy.domain.common.CarListElement
import kotlin.math.absoluteValue

class CarsListRecyclerAdapter(
    private var data: ArrayList<CarListElement>
) : RecyclerView.Adapter<CarsListRecyclerAdapter.ViewHolder>() {

    private val goToOriginalIntent: PublishRelay<CarListElement> = PublishRelay.create()

    fun getGoToOriginalEvent(): Observable<CarListElement> =
        goToOriginalIntent.hide()

    override fun getItemViewType(position: Int): Int =
        R.layout.item_cars_list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(viewType)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CarListElement) {

            Picasso.get()
                .load(item.imageUrl)
                .fit()
                .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.no_image)!!)
                .into(itemView.image)

            itemView.markAndModel.text =
                itemView.context.getString(R.string.markAndModelCar, item.mark.firstUpper(), item.model.firstUpper())
            itemView.price.text = itemView.context.getString(R.string.priceCar, item.price)
            itemView.bodyType.text = itemView.context.getString(R.string.bodyTypeCar, item.bodyType.firstUpper())
            itemView.year.text = itemView.context.getString(R.string.yearCar, item.year)
            itemView.mileage.text =
                itemView.context.getString(R.string.mileageCar, item.totalMileage, item.mileagePerYear)
            itemView.driveUnit.text = itemView.context.getString(R.string.driveUnitCar, item.driveUnit.firstUpper())
            itemView.city.text = itemView.context.getString(R.string.cityCar, item.city.firstUpper())
            itemView.comment.text = itemView.context.getString(R.string.commentCar, item.comment.firstUpper())

            itemView.warning1.visible = item.mileagePerYear > 20_000
            itemView.warning2.visible = (item.price - item.medianPrice).absoluteValue > item.medianPrice / 2

            if (itemView.warning1.visible) itemView.warning1.text = itemView.context.getString(R.string.warningMileage)
            if (itemView.warning2.visible) itemView.warning2.text =
                itemView.context.getString(R.string.warningPrice, item.medianPrice)
            itemView.chooseButton.setOnClickListener {
                goToOriginalIntent.accept(item)
            }
        }
    }

    fun addData(newData: List<CarListElement>) {
        data.addAll(newData)
        this.notifyDataSetChanged()
    }
}