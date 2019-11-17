package game_of_life.view

import game_of_life.signal.Signal
import game_of_life.{Board, Grid}
import scalafx.Includes._
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.MouseEvent
import scalafx.scene.paint.Color

class GameOfLifeCanvas(width: Int, height: Int) extends Canvas(width, height) {

  Signal {
    val grid = Board.grid()
    repaint(grid.width, grid.height, grid.grid)
  }

  def previewShape(x: Int, y: Int, widthSquares: Int, heightSquares: Int, shape: Array[Array[Int]]): Unit = {
    shape.zipWithIndex.foreach { case (row, rowIndex) => row.zipWithIndex.foreach { case (value, colIndex) => {
      val color = if (value == 1) Color.Blue else Color.DarkGrey
      val w = width / widthSquares
      val h = height / heightSquares
      graphicsContext2D.fill = color
      graphicsContext2D.fillRect((x + colIndex) * w, (y + rowIndex) * h, w, h)
    }
    }
    }
  }

  onMouseMoved = event => {
    val g = Board.grid()
    val drawMode = Board.draw()
    getCursorSquare(g, event.x, event.y).foreach { case (x, y) =>
      repaint(g.width, g.height, g.grid)
      if (drawMode.drawMode == DrawModes.NORMAL) {
        highlightSquare(x, y, g.width, g.height)
      } else {
        previewShape(x, y, g.width, g.height, drawMode.shape)
      }
    }
  }

  onMouseClicked = (event: MouseEvent) => {
    val g = Board.grid()
    getCursorSquare(g, event.x, event.y).foreach { case (x, y) =>
      Board.handleClick(x, y)
    }
  }


  def repaint(widthSquares: Int, heightSquares: Int, grid: Array[Int]) = {
    graphicsContext2D.fill = Color.White
    graphicsContext2D.fillRect(0, 0, width, height)
    drawSquares(widthSquares, heightSquares, grid)
    drawGrid(widthSquares, heightSquares)
  }


  def drawGrid(widthSquares: Int, heightSquares: Int) = {
    val squareWidth = width / widthSquares
    val squareHeight = height / heightSquares
    graphicsContext2D.setStroke(Color.Black)
    graphicsContext2D.lineWidth = 1
    for (x <- 0 to widthSquares) yield graphicsContext2D.strokeLine(x * squareWidth, 0, x * squareWidth, squareHeight * heightSquares)
    for (y <- 0 to heightSquares) yield graphicsContext2D.strokeLine(0, y * squareHeight, squareWidth * widthSquares, y * squareHeight)
  }

  def drawSquares(widthSquares: Int, heightSquares: Int, grid: Array[Int]) = {
    var i = 0
    while (i < grid.length) {
      if (grid(i) == 1) {
        val x = i % widthSquares
        val y = i / widthSquares


        val w = width / widthSquares
        val h = height / heightSquares

        graphicsContext2D.stroke = Color.Black
        graphicsContext2D.lineWidth = 1
        graphicsContext2D.fill = Color.Green
        graphicsContext2D.fillRect(x * w, y * h, w, h)
      }
      i = i + 1
    }
  }

  def highlightSquare(x: Int, y: Int, widthSquares: Int, heightSquares: Int) = {
    val w = width / widthSquares
    val h = height / heightSquares
    graphicsContext2D.fill = Color.Orange
    graphicsContext2D.fillRect(x * w, y * h, w, h)
  }

  private def getCursorSquare(grid: Grid, eventX: Double, eventY: Double): Option[(Int, Int)] = {
    val squareWidth = width / grid.width
    val squareHeight = height / grid.height
    if (eventX < (grid.width * squareWidth) && eventY < (height * squareHeight)) {
      val x = eventX.toInt / squareWidth
      val y = eventY.toInt / squareHeight
      Some((x, y))
    } else None
  }
}
