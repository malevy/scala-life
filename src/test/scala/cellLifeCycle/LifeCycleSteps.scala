package cellLifeCycle

import net.malevy.Universe
import net.malevy.Cell
import org.jbehave.core.annotations._
import org.junit.Assert._

class LifeCycleSteps {

  override def toString: String = "LifeCycleSteps"

  var universe: Universe = _
  var storyCounter:Int = 0

  @BeforeScenario
  def setup(): Unit = {
    println(s"setup: $storyCounter")
    universe = null
  }

  @AfterScenario
  def teardown(): Unit = {
    println(s"teardown $storyCounter")
    storyCounter+=1
  }

  @Given("the following universe $initialCellStates")
  def CreateUniverse(initialCellStates:String): Unit = {

    val states = initialCellStates.split("\n") // rows
      .map(row => row.split(" ").map(col => col.toInt))

    universe = new Universe(states)
  }

  @When("the next generation is calculated")
  def evolve(): Unit = universe = universe.evolve()

  @Then("the cell at row:$row col:$col should be $state")
  def assertCellState(row: Int, col: Int, state: String): Unit = {
    val expectedState = state.trim.toUpperCase match {
      case "ALIVE" => Cell.Alive
      case "DEAD" => Cell.Dead
      case _ => throw new IllegalArgumentException(s"Unrecognized expected state $state.")
    }

    var finalState:Int = -1
    universe.iterate((r:Int, c:Int, state:Int) => {
      if (r == row && c == col) finalState = state
    })

    if (-1 == finalState) throw new IllegalArgumentException(s"[$row, $col] is not within the given matrix")
    else assertEquals("the indicated cell did not have the expected state", expectedState, finalState)
  }


}
