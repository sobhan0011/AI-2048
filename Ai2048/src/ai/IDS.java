package ai;

import model.Node;

import java.util.*;

public class IDS{

    public void search(Node startNode) {
        Stack<Node> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        try {
            for (int i = 0;; i++) { //
                frontier.add(startNode);
                inFrontier.put(startNode.hash(), true);
                while (!frontier.isEmpty()) {
                    Node temp = frontier.pop();
                    inFrontier.remove(temp.hash());
                    if (temp.isGoal()) {
                        System.out.println("you win!");
                        printResult(temp, 0);
                        return;
                    }
                    if (depthCounter(temp, 0) + 1 > i)
                        continue;
                    ArrayList<Node> children = temp.successor();
                    for (Node child : children)
                        if (!(inFrontier.containsKey(child.hash()))) {
                            frontier.add(child);
                            inFrontier.put(child.hash(), true);
                        }
                }
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

    public int depthCounter(Node node, int depthCounter) {
        if (node.getParent() == null)
            return depthCounter;
        depthCounter = depthCounter(node.getParent(), depthCounter + 1);
        return depthCounter;
    }


}
