package ai;

import model.Node;

import java.util.*;

public class IDAStar {

    public void search(Node startNode) {
        Stack<Node> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        int whileCounter = 0, tempSum;
        int cutOff = startNode.sum();
        ArrayList<Integer> sums = new ArrayList<>();
        try {
            while (true) {
                frontier.add(startNode);
                inFrontier.put(startNode.hash(), true);
                while (!frontier.isEmpty()) {
                    whileCounter += 1;
                    Node temp = frontier.pop();
                    tempSum = temp.sum();
                    sums.add(tempSum);
                    inFrontier.remove(temp.hash());
                    if (temp.isGoal()) {
                        System.out.println("you win !!! " + whileCounter);
                        printResult(temp, 0);
                        return;
                    }
                    if (tempSum < cutOff) {
                        continue;
                    }
                    ArrayList<Node> children = temp.successor();
                    for (Node child : children)
                        if (!(inFrontier.containsKey(child.hash()))) {
                            frontier.add(child);
                            inFrontier.put(child.hash(), true);
                        }
                }
                cutOff = updateCutOff(sums, cutOff);
            }
        }
        catch (StackOverflowError error) {
            System.out.println("no solution");
        }

    }

    public void printResult(Node node, int depthCounter) {
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node);
        node.drawState();
        printResult(node.getParent(), depthCounter + 1);
    }


    public int updateCutOff(ArrayList<Integer> sums, int cutOff){
        Collections.sort(sums);
        for (int i = sums.size() - 1; i >= 0; i--) {
            if (sums.get(i) < cutOff)
                return sums.get(i);
        }
        return 0;
    }

}
