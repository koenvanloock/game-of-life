package game_of_life

case class Grid(grid: Array[Int], width: Int, height: Int) {

  def getSquareAtIndex(x: Int, y: Int) = grid(x + y * width)

  def changeSquareAtIndex(x: Int, y: Int) = {
    println(s"update at ($x, $y)")
    val newGrid = grid.clone()
    val oldVal = getSquareAtIndex(x, y)
    newGrid(x + y * width) = if (oldVal == 0) 1 else 0
    Grid(newGrid, width, height)
  }

  def groupWithNeighborScore: Array[(Int, Int)] = {
    for {
      y <- 0 until height
      x <- 0 until width
    } yield (getSquareAtIndex(x, y), getNeighborScoreOf(x, y))
  }.toArray

  def getNeighborScoreOf(x: Int, y: Int): Int = (for {
    first <- (x - 1) to (x + 1)
    second <- (y - 1) to (y + 1)
    if first > -1 && second > -1 && first < width && second < height && !(first == x && second == y)
  } yield {
    getSquareAtIndex(first, second)
  }).sum

  def applyGameRules(value: Int, neighborScore: Int): Int =
    if (value == 1 && neighborScore < 2) {
      0
    } else if (value == 1 && neighborScore == 2) {
      1
    } else if (neighborScore > 3) {
      0
    } else if ( neighborScore == 3) {
      1
    } else {
      0
    }

  def nextGeneration: Grid = Grid(groupWithNeighborScore
    .map { case (value, neighborScore) => applyGameRules(value, neighborScore) }, width, height)
}
