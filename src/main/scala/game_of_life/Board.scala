package game_of_life

import game_of_life.signal.Var
import game_of_life.view.{Draw, DrawMode, DrawModes}

object Board {
  private val NORMAL = Draw(DrawModes.NORMAL, Array[Array[Int]]())
  val draw = Var(NORMAL)
  val tickRate = new Var(300)
  val grid = Var(Grid(Array(0), 1, 1))


  def generateArray(old: Array[Int], width: Int, height: Int): Array[Int] = {
    val array = Array.ofDim[Int](width * height)
    var i = 0
    while (i < old.length && i < array.length) {
      array(i) = old(i)
      i = i + 1
    }
    array
  }

  def drawing(mode: DrawMode, shape: Array[Array[Int]]): Unit = draw.update(Draw(mode, shape))

  def generateArrayPreserveRowCol(old: Grid, width: Int, height: Int): Array[Int] = {
    val array = Array.ofDim[Int](width * height)
    var i = 0
    while (i < old.grid.length) {
      val x = i % old.width
      val y = i / old.width
      if(x + width * y < array.length) {
        array(x + width * y) = old.grid(i)
      }
      i = i + 1
    }
    array
  }

  def updateWidth(newWidth: Int): Unit = {
    val old = grid()
    grid.update(Grid(generateArrayPreserveRowCol(old, newWidth, old.height), newWidth, old.height))
  }

  def updateHeight(newHeight: Int): Unit = {
    val old = grid()
    grid.update(Grid(generateArray(old.grid, old.width, newHeight), old.width, newHeight))
  }

  def updateDimesions(width: Int, height: Int): Unit = {
    val old = grid()
    grid.update(Grid(generateArrayPreserveRowCol(old, width, height), width, height))
  }

  def stampShapeAt(x: Int, y: Int, shape: Array[Array[Int]]) = {
    val old = grid()
    val oldGrid = old.grid.clone()
    shape.zipWithIndex.foreach{ case (row, rowIndex) =>
      row.zipWithIndex.foreach{case (value, colIndex) => oldGrid( x + colIndex + old.width * (rowIndex + y)) = value}
    }
    grid.update(old.copy(grid = oldGrid))
  }

  def handleClick(x: Int, y: Int) = {
    val currentDraw = draw()
    if(currentDraw.drawMode == DrawModes.NORMAL) {
      updateSquareAtIndex(x,y)
    } else {
      stampShapeAt(x, y, currentDraw.shape)
      draw.update(NORMAL)
    }
  }

  def updateSquareAtIndex(x: Int, y: Int): Unit = {
    val old = grid()
    grid.update(old.changeSquareAtIndex(x, y))
  }

  def clear = {
    val old = grid()
    grid.update(old.copy(grid = Array.ofDim(old.width * old.height)))
  }

  def step: Unit = {
    val old = grid()
    grid.update(old.nextGeneration)
  }
}
