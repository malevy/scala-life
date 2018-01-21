package unittests

import org.junit.Assert._
import net.malevy.Universe
import org.hamcrest.core.Is._
import org.hamcrest.core.IsNull._
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

    assertThat(universe, is(notNullValue()))
  }

  @Test
  def whenTheUniverseEvolves_TheCorrectCellIsChanged(): Unit = {
    val seed = Array(
      Array(1,1,1),
      Array(0,0,0),
      Array(0,0,0)
    )

    val universe = new Universe(seed)

    val nextGeneration = universe.evolve()
    dump(nextGeneration)
    assertThat(nextGeneration.toString, is("010010000"))
  }

  @Test
  def canDumpToString(): Unit = {
    val seed = Array(
      Array(0,0,0),
      Array(0,0,0),
      Array(0,0,0)
    )

    val universe = new Universe(seed)

    assertThat(universe.toString, is("000000000"))
  }

  @Test
  def twoIdenticalUniversesAreEqual(): Unit = {
    val seed = Array(
      Array(0,0,0),
      Array(1,0,1),
      Array(0,0,0)
    )

    val u1 = new Universe(seed)
    val u2 = new Universe(seed)

    assertThat(u1, is(u2))

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
