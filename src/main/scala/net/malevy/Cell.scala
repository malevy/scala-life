package net.malevy

class Cell(val state: Int) {

  private def isAlive = this.state == Cell.Alive

  def observe(neighbors: Seq[Cell]) : Cell = {
    val livingNeighbors = neighbors.map(c => c.state).sum

    // Any live cell with fewer than two live neighbours dies, as if caused by under-population.
    if (this.isAlive && livingNeighbors < 2) new Cell(Cell.Dead)

    // Any live cell with two or three live neighbours lives on to the next generation.
    else if (this.isAlive && livingNeighbors < 4) new Cell(this.state)

    // Any live cell with more than three live neighbours dies, as if by over-population.
    else if (this.isAlive && livingNeighbors > 3) new Cell(Cell.Dead)

    // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
    else if (!this.isAlive && livingNeighbors == 3) new Cell(Cell.Alive)

    else new Cell(this.state)
  }

}

object Cell {
  val Alive: Int = 1
  val Dead: Int = 0
}
