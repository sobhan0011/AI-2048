package core;

import ai.*;
import model.Board;
import model.Node;

import java.util.Hashtable;
import java.util.Scanner;

import static model.Movement.NONE;

public class main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" please enter the goal value : \n then enter rows and columns and your board");
        int goalValue = Integer.parseInt(sc.nextLine());
        String mn = sc.nextLine();
        int rows = Integer.parseInt(mn.split(" ")[0]);
        int columns = Integer.parseInt(mn.split(" ")[1]);

        String[][] board = new String[rows][columns];
        String[] lines = new String[rows];
        for (int i = 0; i < rows; i++) {
            lines[i] = sc.nextLine();
            String[] line = lines[i].split(" ");
            System.arraycopy(line, 0, board[i], 0, columns);
        }

        Mapper mapper = new Mapper();
        int[][] cells = mapper.createCells(board, rows, columns);
        Board gameBoard = mapper.createBoard(cells, goalValue, rows, columns);
        Board.mode = Constants.MODE_NORMAL;
        System.out.println(gameBoard.toString());

        Hashtable<String, Boolean> initHash = new Hashtable<>();
        Node start = new Node(gameBoard, null, NONE);
       // System.out.println("\nDFS Answer :");
        /*DFS dfs = new DFS();
        dfs.search(start);*/
        System.out.println("\nBFS Answer :");
        BFS bfs = new BFS();
        bfs.search(start);
        /*System.out.println("\nIDS Answer :");
        IDS ids = new IDS();
        ids.search(start);
        System.out.println("\nUCS Answer :");
        UCS ucs = new UCS();
        ucs.search(start);*/
        System.out.println("\nASTAR Answer :");
        ASTAR astar = new ASTAR();
        astar.search(start);
        System.out.println("\nGBFS Answer :");
        GBFS gbfs = new GBFS();
        gbfs.search(start);
    }
}
