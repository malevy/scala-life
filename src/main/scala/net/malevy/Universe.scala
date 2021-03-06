package net.malevy

import scala.util.Random

class Universe(seed: Array[Array[Int]]) {
  assert(null != seed)
  val rows = seed.length
  val cols = seed.map(row => row.length).max

  // needed for sparse arrays
  private val colFill = Array.fill(cols)(0)

  private val cells: Array[Array[ColumnWrapper]] = seed.zipWithIndex.map(
    row => row._1
      .union(colFill.take(cols - row._1.length))
      .zipWithIndex.map(col => ColumnWrapper(row._2, col._2, new Cell(col._1))))

  private def neighborPositions(row: Int, col: Int):Seq[(Int, Int)] = {
    val cellsAround = (-1 to 1).flatMap(r => (-1 to 1).map(c => (r, c)))
    cellsAround
        .filter(offset => !(offset._1 == 0 && offset._2 == 0))  // remove the center
        .map(id => (row+id._1, col+id._2))
        .filter(pos => isInGrid(pos._1, pos._2))
  }

  private def isInGrid(row: Int, col: Int): Boolean =
    row >= 0 && row < rows && col >= 0 && col < cols

  /**
    * apply the generation rules to the universe
    * @return a new Universe instance
    */
  def evolve(): Universe = {
    val nextGeneration = cells.map(rw => rw.map(cw => {

      val neighbors = neighborPositions(cw.row, cw.col)
        .map(pos => cells(pos._1)(pos._2).cell)
      cw.cell.observe(neighbors).state
    }) )

    new Universe(nextGeneration)
  }

  /**
    * hook for iterating the universe
    * @param callback (row, col, state) => Unit
    */
  def iterate(callback: (Int, Int, Int) => Unit): Unit = {
    assert(null != callback)
    cells.foreach(rw => rw.foreach(cw => callback(cw.row, cw.col, cw.cell.state)))
  }

  /**
    * determines if two Universe instance are identical
    * @param other
    * @return
    */
  override def equals(other: scala.Any): Boolean = {
    other match {
      case other:Universe => {
        null != other &&
        other.cols == this.cols &&
        other.rows == this.rows &&
        this.toString.equals(other.toString)
      }
      case _ => false
    }
  }

  /**
    * Render the state of the Universe as a string where
    * a DEAD cell is represented with a zero (0) and
    * an ALIVE cell is represented with a one (1)
    * @return a string representing the state of the Universe
    */
  override def toString: String = {
    val sb = new StringBuilder()
    this.iterate((row:Int, col:Int, state:Int) => sb.append(state))
    sb.toString()
  }

  private case class ColumnWrapper(row: Int, col: Int, cell:Cell)
}

object Universe {

  /**
    * create a random Universe with the given dimensions
    * @param rows the number of rows
    * @param cols the number of columns
    * @return a new Universe instance
    * @note each cell has a 50% chance of starting in an ALIVE state
    */
  def makeRandom(rows: Int, cols: Int) : Universe = {

    // 50% chance that the cell starts in an ALIVE state
    val cellState = Array.fill(rows)(Array.fill(cols)({
      if (Random.nextInt(100) < 50) 1 else 0
    }))
    new Universe(cellState)
  }

}