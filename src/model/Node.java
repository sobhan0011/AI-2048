package model;

import core.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.ToIntFunction;

public class Node  {
    Board board;
    Node parent;
    Movement previousMovement;

    public Node(Board board, Node parent, Movement previousMovement) {
        this.parent = parent;
        this.board = board;
        this.previousMovement = previousMovement;
    }

    public ArrayList<Node> successor() {
        ArrayList<Node> result = new ArrayList<Node>();
        result.add(new Node(board.moveLeft(), this, Movement.LEFT));
        result.add(new Node(board.moveRight(), this, Movement.RIGHT));
        result.add(new Node(board.moveDown(), this, Movement.DOWN));
        result.add(new Node(board.moveUp(), this, Movement.UP));
        return result;
    }

    public void drawState() {
        System.out.println("moved to : " + this.previousMovement);
        for (int i = 0; i < board.row; i++) {
            for (int j = 0; j < board.col; j++) {
                System.out.print(Constants.ANSI_BRIGHT_GREEN + board.cells[i][j] + spaceRequired(board.cells[i][j]));
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }

    public boolean isGoal() {
        return board.isGoal();
    }

    public int pathCost() {
        if (this.getParent() == null)
            return 0;
        int value;
        switch (this.previousMovement) {
            case LEFT:
                value = 1;
                break;
            case RIGHT:
                value = 3;
                break;
            case DOWN:
                value = 5;
                break;
            default:
                value = 7;
        };
        return this.getParent().pathCost() + value;
    }

//    public int getMin(){
//
//    }

    public int heuristic() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < this.board.row; i++) {
            for (int j = 0; j < this.board.col; j++) {
                if (map.containsKey(this.board.cells[i][j]))
                    map.put(this.board.cells[i][j], map.get(this.board.cells[i][j]) + 1);
                else
                    map.put(this.board.cells[i][j], 1);
            }
        }
        int duplicateCounter = map.values().stream().reduce(0, Integer::sum);

        int sumOfCorners = this.board.cells[0][0] +
                this.board.cells[0][this.board.col - 1] +
                this.board.cells[this.board.row - 1][0] +
                this.board.cells[this.board.row - 1][this.board.col - 1];

        int zeroCounter = 0;
        for (int i = 0; i < this.board.row; i++)
            for (int j = 0; j < this.board.col; j++)
                if (this.board.cells[i][j] == 0)
                    zeroCounter += 1;

        return  sumOfCorners + zeroCounter + (duplicateCounter / 10);
    }

    public int sum() {
        return heuristic() - 12 * pathCost() / 10;
    }


    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append(board.hash()).append("/PM=").append(previousMovement.toString());
        return hash.toString();
    }

    private String spaceRequired(int cell) {
        int length = String.valueOf(cell).length();
        String result ="";
        for (int i = 0; i < 5-length ; i++) {
            result += " ";
        }
        result += " ";
        return result;
    }

    public Node getParent() {
        return parent;
    }
}
