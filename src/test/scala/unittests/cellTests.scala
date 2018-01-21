package unittests

import org.junit.Assert._
import net.malevy.Cell
import org.junit.Test
import org.hamcrest.core.Is._


class cellTests {

  @Test
  def whenALiveCellHasOneNeighbors_ItDies(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,1))

    assertThat("the cell should die", nextGen.state, is(Cell.Dead))
  }

  @Test
  def whenALiveCellHasTwoNeighbors_Lives(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,2))

    assertThat("the cell should be alive", nextGen.state, is(Cell.Alive))
  }

  @Test
  def whenALiveCellHasThreeNeighbors_ItLives(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,3))

    assertThat("the cell should be alive", nextGen.state, is(Cell.Alive))
  }

  @Test
  def whenALiveCellHasFourNeighbors_ItDies(): Unit = {
    val cell = new Cell(Cell.Alive)
    val nextGen = cell.observe(makeNeighbors(8,4))

    assertThat("the cell should die", nextGen.state, is(Cell.Dead))
  }

  @Test
  def whenADeadCellHasThreeLiveNeighbors_ItLives(): Unit = {
    val cell = new Cell(Cell.Dead)
    val nextGen = cell.observe(makeNeighbors(8,3))

    assertThat("the cell should be alive", nextGen.state, is(Cell.Alive))
  }

  @Test
  def whenADeadCellHasEightNeighbors_ItStaysDead(): Unit = {
    val cell = new Cell(Cell.Dead)
    val nextGen = cell.observe(makeNeighbors(8,0))

    assertThat("the cell should die", nextGen.state, is(Cell.Dead))
  }

  private def makeNeighbors(total: Int, live: Int):Seq[Cell] = {
    Array.fill(live)(new Cell(Cell.Alive)) ++
      Array.fill(total-live)(new Cell(Cell.Dead))
  }
}
