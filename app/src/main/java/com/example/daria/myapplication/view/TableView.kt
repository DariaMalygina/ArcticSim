package com.example.daria.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.*
import android.graphics.Bitmap
import com.example.daria.myapplication.*
import com.example.daria.myapplication.model.Orca
import com.example.daria.myapplication.model.Organism
import com.example.daria.myapplication.model.Penguin
import com.example.daria.myapplication.model.Size

data class Sprit(val bitmap: Bitmap, val point: Point)

class TableView(context: Context?, attrs: AttributeSet):View(context,attrs) {
    private var sprits = ArrayList<Sprit>()
    private var cellSize: Int = 0
    private val tablePaint: Paint = Paint()
    private val bitmapPaint: Paint = Paint(Paint.FILTER_BITMAP_FLAG)
    private val bitmapOrca: Bitmap
    private val bitmapPenguin: Bitmap
    private var topLeft: Point = Point(0,0)
    private var topRight: Point = Point(0,0)
    private var bottomLeft: Point= Point(0,0)
    init {
        tablePaint.color = Color.BLACK
        bitmapOrca = BitmapFactory.decodeResource(resources, R.drawable.orca)
        bitmapPenguin = BitmapFactory.decodeResource(resources, R.drawable.tux)
    }

    var size: Size = Size(15, 10)
        set(newSize) {
            field = newSize
            sprits.clear()
            requestLayout()
        }

    fun placeOrganisms(organisms: List<Organism>) {
        val resizedBitmapPenguin = Bitmap.createScaledBitmap(bitmapPenguin,cellSize, cellSize,true)
        val resizedBitmapOrca = Bitmap.createScaledBitmap(bitmapOrca,cellSize, cellSize,true)

        sprits.clear()
        for (o in organisms) {
            val coords = getCoordinatesOrganism(o)
            when {
                (o is Orca) -> sprits.add(Sprit(resizedBitmapOrca, coords))
                (o is Penguin) -> sprits.add(Sprit(resizedBitmapPenguin, coords))
            }
        }

        invalidate()
    }

    private fun getCoordinatesOrganism(organism: Organism): Point {
        val p = organism.position
        val x = p.row*cellSize
        val y = p.column*cellSize
        return Point(x,y)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        cellSize = (widthSize/ size.columnCount)
        if (cellSize*size.rowCount > heightSize) {
            cellSize = (heightSize/ size.rowCount)
        }

        when (widthMode == View.MeasureSpec.EXACTLY || widthMode == View.MeasureSpec.AT_MOST) {
            true -> width = cellSize*size.columnCount + 1
            false -> width = -1
        }

        when (heightMode == View.MeasureSpec.EXACTLY || heightMode == View.MeasureSpec.AT_MOST) {
            true ->  height = cellSize*size.rowCount + 1
            false -> height = -1
        }

        topRight.x = width
        bottomLeft.y = height
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            drawTable(canvas)
            drawOrganism(canvas)
        }
    }

    private fun drawTable(canvas: Canvas) {
        var offset: Int
        for (row in 0..size.rowCount) {
            offset = row*cellSize
            canvas.drawLine(topLeft.x.toFloat(), (topLeft.y + offset).toFloat(),
                    topRight.x.toFloat(), (topRight.y + offset).toFloat(), tablePaint)
        }
        for (column in 0..size.columnCount) {
            offset = column*cellSize
            canvas.drawLine((topLeft.x + offset).toFloat(), topLeft.y.toFloat(),
                    (bottomLeft.x + offset).toFloat(), bottomLeft.y.toFloat(), tablePaint)
        }
    }

    private fun drawOrganism(canvas: Canvas) {
        for (s in sprits) {
            canvas.drawBitmap(s.bitmap, s.point.y.toFloat(), s.point.x.toFloat(), bitmapPaint)
        }
    }
}