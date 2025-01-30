package com.example.tictactoe

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var board: Array<Array<Button>>
    private var currentPlayer = 'X'
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusTextView: TextView = findViewById(R.id.statusTextView)
        val resetButton: Button = findViewById(R.id.resetButton)
        val gridLayout: GridLayout = findViewById(R.id.boardGrid)

        board = Array(3) { row ->
            Array(3) { col ->
                val button: Button = gridLayout.getChildAt(row * 3 + col) as Button
                button.setOnClickListener { onCellClicked(row, col, button, statusTextView) }
                button
            }
        }

        resetButton.setOnClickListener { resetGame(statusTextView) }
    }

    private fun onCellClicked(row: Int, col: Int, button: Button, statusTextView: TextView) {
        if (!gameActive || button.text.isNotEmpty()) return

        button.text = currentPlayer.toString()

        if (checkWin()) {
            statusTextView.setTextColor(Color.GREEN)
            statusTextView.text = "Player $currentPlayer Wins!"
            gameActive = false
        } else if (isBoardFull()) {
            statusTextView.text = "It's a Draw!"
            gameActive = false
        } else {
            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            statusTextView.text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if ((board[i][0].text == currentPlayer.toString() &&
                        board[i][1].text == currentPlayer.toString() &&
                        board[i][2].text == currentPlayer.toString()) ||
                (board[0][i].text == currentPlayer.toString() &&
                        board[1][i].text == currentPlayer.toString() &&
                        board[2][i].text == currentPlayer.toString())
            ) {
                return true
            }
        }
        return (board[0][0].text == currentPlayer.toString() &&
                board[1][1].text == currentPlayer.toString() &&
                board[2][2].text == currentPlayer.toString()) ||
                (board[0][2].text == currentPlayer.toString() &&
                        board[1][1].text == currentPlayer.toString() &&
                        board[2][0].text == currentPlayer.toString())
    }

    private fun isBoardFull(): Boolean {
        return board.all { row -> row.all { button -> button.text.isNotEmpty() } }
    }

    private fun resetGame(statusTextView: TextView) {
        currentPlayer = 'X'
        gameActive = true
        statusTextView.text = "Player X's Turn"
        board.forEach { row ->
            row.forEach { button ->
                button.text = ""
            }
        }
    }
}