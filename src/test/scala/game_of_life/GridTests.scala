package game_of_life

import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class GridTests extends Specification {

  "Grid" should {
    "get the first element of the second row" in {
      val grid = Grid(Array(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0), 5, 2)
      grid.getSquareAtIndex(0, 1) must equalTo(1)
    }


    "getNeighborscore returns a the correct number of neighbors of a cell" in {
      val grid = Grid(Array(
        0, 0, 1,
        0, 1, 0,
        1, 0, 1), 3, 3)
      grid.getNeighborScoreOf(1, 1) must beEqualTo(3)
      grid.getNeighborScoreOf(1, 0) must beEqualTo(2)
      grid.getNeighborScoreOf(0, 0) must beEqualTo(1)
    }


    "groupWithNeighborscore returns a sequence of equal length than the initial grid" in {
      val grid = Grid(Array(0, 0, 0, 0, 1, 0, 0, 0, 0, 0), 5, 2)
      grid.groupWithNeighborScore.length must equalTo(grid.grid.length)
    }


    "applyRules of cell value 1 with 1 neighbor dies" in {
      Grid(Array(), 3, 3).applyGameRules(1, 1) must equalTo(0)
    }

    "applyRules of cell value 1 with 2 neighbors lives" in {
      Grid(Array(), 3, 3).applyGameRules(1, 2) must equalTo(1)
    }

    "applyRules of cell value 1 with 3 neighbors lives" in {
      Grid(Array(), 3, 3).applyGameRules(1, 3) must equalTo(1)
    }

    "next generation is correct for one iteration" in {
      val grid = Grid(
        Array(
          0, 1, 0, 0, 0,
          1, 0, 0, 0, 0,
          1, 0, 1, 1, 0,
          0, 0, 0, 1, 0,
          1, 0, 0, 0, 0), 5, 5)
      val nextGenGrid = Grid(
        Array(
          0, 0, 0, 0, 0,
          1, 0, 1, 0, 0,
          0, 1, 1, 1, 0,
          0, 1, 1, 1, 0,
          0, 0, 0, 0, 0), 5, 5)

      val test = grid.getNeighborScoreOf(1,0)
      grid.nextGeneration.grid must beEqualTo(nextGenGrid.grid)
    }


    "glider in four generations is translated glider" in {
      val grid = Grid(
        Array(
          1, 0, 0, 0, 0,
          0, 1, 1, 0, 0,
          1, 1, 0, 0, 0,
          0, 0, 0, 0, 0,
          0, 0, 0, 0, 0), 5, 5)
      val target = Grid(
        Array(
          0, 1, 0, 0, 0,
          0, 0, 1, 0, 0,
          1, 1, 1, 0, 0,
          0, 0, 0, 0, 0,
          0, 0, 0, 0, 0), 5, 5)

      val test = grid.getNeighborScoreOf(1,0)
      grid.nextGeneration.grid must beEqualTo(target.grid)
    }

    "grid updates the correct square" in {
      val grid = Grid(Array.ofDim(10 * 66), 10, 66)
      grid.changeSquareAtIndex(7,6).getSquareAtIndex(7, 6) must equalTo(1)
    }
  }

}
