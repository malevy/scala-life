package unittests

import org.junit.Assert._
import net.malevy.{Cell, Universe}
import org.junit.Test

class universeTests {

  @Test
  def canBuildAUniverse(): Unit = {
    val seed = Array(
      Array(0,0,0),
      Array(0,0,0),
      Array(0,0,0)
    )

    val universe = new Universe(seed)

    assertNotNull("The universe was not created", universe)

  }

  @Test
  def whenTheUniverseEvolves_TheCorrectCellIsChanged(): Unit = {
    val seed = Array(
      Array(0,0,0),
      Array(0,0,0),
      Array(0,0,0)
    )

    val universe = new Universe(seed)

    val nextGeneration = universe.evolve()
    dump(universe)
  }

  private def dump(universe: Universe) : Unit = {
    var currentRow: Int = -1
    val sb = new StringBuilder()
    universe.iterate((row:Int, col:Int, state:Int) => {
      if (row != currentRow) {
        sb.append("\n")
        currentRow = row
      }
      sb.append(state).append(" ")
    })
    sb.append("\n")

    println(sb.toString())
  }

}
