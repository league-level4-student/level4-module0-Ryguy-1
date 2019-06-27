package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start	
	
		if(randGen.nextInt(2)==1) {
			maze.cells[0][randGen.nextInt(height-1)].setWestWall(false);
			maze.cells[width-1][randGen.nextInt(height-1)].setEastWall(false);
		}else {
			maze.cells[randGen.nextInt(width-1)][0].setNorthWall(false);
			maze.cells[randGen.nextInt(width-1)][height-1].setSouthWall(false);
		}
		
		
		Cell temp = maze.getCell(randGen.nextInt(w), randGen.nextInt(h));

		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(temp);

		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. check for unvisited neighbors using the cell

		ArrayList<Cell> unvisitedNBors = getUnvisitedNeighbors(currentCell);

		// C. if has unvisited neighbors,

		if (unvisitedNBors.isEmpty() == false) {
			int temp1 = randGen.nextInt(unvisitedNBors.size());
			uncheckedCells.push(unvisitedNBors.get(temp1));

			// C1. select one at random.

			// C2. push it to the stack

			// C3. remove the wall between the two cells
			Cell tempCell = unvisitedNBors.get(temp1);

			removeWalls(tempCell, currentCell);

			// C4. make the new cell the current cell and mark it as visited

			currentCell = tempCell;
			currentCell.setBeenVisited(true);

			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}
		// D. if all neighbors are visited
		else {

			// D1. if the stack is not empty
			if (uncheckedCells.empty() == false) {

				// D1a. pop a cell from the stack
				Cell tempCell = uncheckedCells.pop();

				// D1b. make that the current cell
				currentCell = tempCell;
				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}
	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		if (c1.getX() < c2.getX()) {
			c2.setWestWall(false);
			c1.setEastWall(false);
		} else if (c1.getX() > c2.getX()) {
			c2.setEastWall(false);
			c1.setWestWall(false);
		} else if (c1.getY() > c2.getY()) {
			c2.setSouthWall(false);
			c1.setNorthWall(false);
		} else if (c1.getY() < c2.getY()) {
			c2.setNorthWall(false);
			c1.setSouthWall(false);
		}
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		int counter = 0;
		ArrayList<Cell> listCells = new ArrayList<Cell>();
		if (c.getX() > 0) {
			if (maze.cells[c.getX() - 1][c.getY()].hasBeenVisited() == false) {
				listCells.add(maze.cells[c.getX() - 1][c.getY()]);
			}
		}
		if (c.getX() < width - 1) {
			if (maze.cells[c.getX() + 1][c.getY()].hasBeenVisited() == false) {
				listCells.add(maze.cells[c.getX() + 1][c.getY()]);
			}
		}
		if (c.getY() > 0) {
			if (maze.cells[c.getX()][c.getY() - 1].hasBeenVisited() == false) {
				listCells.add(maze.cells[c.getX()][c.getY() - 1]);
			}
		}
		if (c.getY() < height - 1) {
			if (maze.cells[c.getX()][c.getY() + 1].hasBeenVisited() == false) {
				listCells.add(maze.cells[c.getX()][c.getY() + 1]);
			}
		}
		return listCells;

	}
}
