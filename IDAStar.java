package ai;

import model.Node;

import java.util.*;

public class IDAStar {

    public void search(Node startNode) {
        Stack<Node> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        int whileCounter = 0;
        int cutOff = startNode.sum();
        try {
            while (true) {
                frontier.add(startNode);
                inFrontier.put(startNode.hash(), true);
                while (!frontier.isEmpty()) {
                    whileCounter += 1;
                    Node temp = frontier.pop();
                    inFrontier.remove(temp.hash());
                    if (temp.isGoal()) {
                        System.out.println("you win !!! " + whileCounter);
                        printResult(temp, 0);
                        return;
                    }
                    if (temp.sum() < cutOff) {
                        continue;
                    }
                    //System.out.println("g");
                    ArrayList<Node> children = temp.successor();
                    for (Node child : children)
                        if (!(inFrontier.containsKey(child.hash()))) {
                            frontier.add(child);
                            inFrontier.put(child.hash(), true);
                        }
                    //System.out.println("h");
                    cutOff = updateCutOff(frontier, cutOff);
                }
            }
        } catch (StackOverflowError error) {
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

    public int updateCutOff(Stack<Node> tmp, int cutOff) {
        int i;
        ArrayList<Integer> arrayList = new ArrayList<>();
        Iterator<Node> iterator = tmp.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().sum());
        }
        Collections.sort(arrayList);
        for (i = arrayList.size() - 1; i > 0; i--) {
            if (arrayList.get(i) < cutOff)
                return arrayList.get(i);
        }
        System.out.println(cutOff);
        return cutOff;
    }
}