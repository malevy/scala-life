package net.malevy

import scala.util.Random

class Universe(seed: Array[Array[Int]]) {
  assert(null != seed)
  val rows = seed.length
  val cols = seed.map(row => row.length).max

  // needed for sparse arrays
  private val colFill = Array.fill(cols)(0)

  private val cells = seed.zipWithIndex.map(
    row => row._1
      .union(colFill.take(cols - row._1.length))
      .zipWithIndex.map(col => new ColumnWrapper(row._2, col._2, new Cell(Random.nextInt(2)))))

  private def neighborPositions(row: Int, col: Int):Seq[(Int, Int)] = {
    val cellsAround = (-1 to 1).flatMap(r => (-1 to 1).map(c => (r, c)))
    cellsAround
        .map(id => (row+id._1, col+id._2))
        .filter(pos => isInGrid(pos._1, pos._2))
  }

  private def isInGrid(row: Int, col: Int): Boolean =
    row >= 0 && row < rows && col >= 0 && col < cols

  /**
    * Evolve this universe to the next generation
    * @return a universe with the next generation of cells
    */
  def evolve(): Universe = {
    val nextGeneration = cells.map(rw => rw.map(cw => {
      val neighbors = neighborPositions(cw.row, cw.col)
        .map(pos => cells(pos._1)(pos._2))
        .map(cw => cw.cell)
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

  private case class ColumnWrapper(row: Int, col: Int, cell:Cell)
}

object Universe {

  def makeRandom(rows: Int, cols: Int) : Universe = {

    // 2% chance that the cell starts in an ALIVE state
    val cellState = Array.fill(rows)(Array.fill(cols)({
      if (Random.nextInt(100) < 2) 1 else 0
    }))
    new Universe(cellState)
  }

}