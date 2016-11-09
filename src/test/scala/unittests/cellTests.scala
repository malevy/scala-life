package unittests

import junit.framework.TestCase
import org.junit.Assert._
import net.malevy.Cell
import org.junit.Test

class cellTests {

  @Test
  def whenALiveCellHasOneNeighbors_ItDies(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,1))

    assertEquals("the cell should die", Cell.Dead, nextGen.state)
  }

  @Test
  def whenALiveCellHasTwoNeighbors_Lives(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,2))

    assertEquals("the cell should be alive", Cell.Alive, nextGen.state)
  }

  @Test
  def whenALiveCellHasThreeNeighbors_ItLives(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,3))

    assertEquals("the cell should be alive", Cell.Alive, nextGen.state)
  }

  @Test
  def whenALiveCellHasFourNeighbors_ItDies(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,4))

    assertEquals("the cell should die", Cell.Dead, nextGen.state)
  }

  @Test
  def whenADeadCellHasThreeLiveNeighbors_ItLives(): Unit = {
    val cell = new Cell(Cell.Dead)
    val nextGen = cell.observe(makeNeighbors(8,3))

    assertEquals("the cell should live", Cell.Alive, nextGen.state)
  }

  private def makeNeighbors(total: Int, live: Int):Seq[Cell] = {
    Array.fill(live)(new Cell(Cell.Alive)) ++
      Array.fill(total-live)(new Cell(Cell.Dead))
  }
}
