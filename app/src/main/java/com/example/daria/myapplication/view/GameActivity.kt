package com.example.daria.myapplication.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import android.view.MenuItem
import android.support.v7.app.AlertDialog
import com.example.daria.myapplication.*
import com.example.daria.myapplication.model.Organism
import com.example.daria.myapplication.model.Size
import com.example.daria.myapplication.presenter.IView
import com.example.daria.myapplication.presenter.Presenter


class GameActivity : AppCompatActivity(), IView {
    private lateinit var tableView: TableView
    private lateinit var presenter: Presenter
    private lateinit var possibleTableSizes: Array<String>
    private var itemSelected = 0
    private val defaultSize: Size?
    private val sizes: HashMap<Int,Size> = hashMapOf(
            0 to Size(15, 10),
            1 to Size(12, 8),
            2 to Size(10, 6),
            3 to Size(7, 5),
            4 to Size(5, 3))

    init {
        defaultSize = sizes[0]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        possibleTableSizes = resources.getStringArray(R.array.possible_table_sizes)
        tableView = table_view
        presenter = Presenter(this)
    }

    override fun onNewState(organisms: List<Organism>) {
        tableView.placeOrganisms(organisms)
    }

    override fun onNewSize(size: Size) {
        tableView.size = size
    }

    fun reset(view: View) {
        presenter.resetGame()
    }

    fun changeState(view: View) {
        presenter.performSimulation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.settings -> {
                showAlertDialog {
                    setTitle("Размер таблицы")
                    setSingleChoiceItems(possibleTableSizes, itemSelected){
                        dialogInterface, selectedIndex -> itemSelected = selectedIndex
                    }
                    setPositiveButton{
                        val size = sizes[itemSelected]?:defaultSize
                        presenter.changeTableSize(size)
                    }
                    setNegativeButton{}
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialog(dialogBuilder: AlertDialog.Builder.() -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.dialogBuilder()
        val dialog = builder.create()
        dialog.show()
    }

    private fun AlertDialog.Builder.setPositiveButton(text: String = "Применить", handleClick: (which: Int) -> Unit = {}) {
        this.setPositiveButton(text) { dialogInterface, which-> handleClick(which) }
    }

    private fun AlertDialog.Builder.setNegativeButton(text: String = "Закрыть", handleClick: (which: Int) -> Unit = {}) {
        this.setNegativeButton(text) { dialogInterface, which-> handleClick(which) }
    }
}
