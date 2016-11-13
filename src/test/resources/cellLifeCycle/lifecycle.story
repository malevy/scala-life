
Scenario: Any live cell with fewer than two live neighbours dies, as if caused by under-population
Given the following universe
0 0 0
0 1 0
0 0 0
When the next generation is calculated
Then the cell at row:1 col:1 should be DEAD

Scenario: Any live cell with two live neighbours lives on to the next generation.
Given the following universe
1 1 0
0 1 0
0 0 0
When the next generation is calculated
Then the cell at row:1 col:1 should be ALIVE

Scenario: Any live cell with three live neighbours lives on to the next generation.
Given the following universe
1 1 0
0 1 0
0 0 1
When the next generation is calculated
Then the cell at row:1 col:1 should be ALIVE

Scenario: Any live cell with more than three live neighbours dies, as if by over-population
Given the following universe
1 1 0
0 1 1
0 0 1
When the next generation is calculated
Then the cell at row:1 col:1 should be DEAD

Scenario: Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction
Given the following universe
1 1 0
0 0 1
0 0 0
When the next generation is calculated
Then the cell at row:1 col:1 should be ALIVE
