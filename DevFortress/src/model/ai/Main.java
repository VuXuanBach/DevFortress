
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * RMIT University Vietnam SGS
 * Bachelor of IT
 * ISYS2102 - Software Engineering 2
 * Work Insurance team
 */
/**
 *
 * @author Hoang Hai
 */
public class Main {

    public static AreaMap map = new AreaMap(90, 90);
    private static ArrayList<Node> closedList;
    private static SortedNodeList openList;

    public static void main(String[] args) {
        closedList = new ArrayList<Node>();
		openList = new SortedNodeList();
        ArrayList<String> path = calcShortestPath(1, 1, 2, 2);
        System.out.println(path.size());
        for (String s : path) {
            System.out.println(s);
        }
        
        
//        Node start = new Node(x, y, null);
//        Node end = new Node(2, 1);
//        Node current = start;
//
//        ArrayList<Node> openList = new ArrayList<Node>();
//        ArrayList<Node> closeList = new ArrayList<Node>();
//        openList.add(start);
//
//        while (current != end) {
//            if (current == end) {
//                break;
//            } else {
//                current = getFirst(openList, start);
//                closeList.add(current);
//                openList.remove(current);
//
//                ArrayList<Node> near = getNearNode(current);
//                for (int i = 0; i < near.size(); i++) {
//                    boolean neighborIsBetter;
//                    Node check = near.get(i);
//                    int point = 10;
//                    if (check.x != current.x && check.y != current.y) {
//                        point = 14;
//                    }
//                    check.findG(current, point);
//                    check.findH(end);
//
//                    if (closeList.contains(check)) {
//                        continue;
//                    }
//                    int neighborDistanceFromStart = check.g;
//                    if (!openList.contains(check)) {
//                        openList.add(check);
//                        neighborIsBetter = true;
//                        //if neighbor is closer to start it could also be better
//                    } else if (neighborDistanceFromStart < current.g) {
//                        neighborIsBetter = true;
//                    } else {
//                        neighborIsBetter = false;
//                    }
//                    // set neighbors parameters if it is better
//                    if (neighborIsBetter) {
//                        check.previous = current;
//                    }
//                    System.out.println(openList.contains(new Node(1, 1)));
//                    System.out.println("Node: x=" + current.x + "   y=" + current.y);
//                }
//            }
//        }
//        for (Node n : openList) {
//            System.out.println("Node: x=" + n.x + "   y=" + n.y);
//        }
    }

    public static ArrayList<String> calcShortestPath(int startX, int startY, int goalX, int goalY) {
        //this.startX = startX;
        //this.startY = startY;
        //this.goalX = goalX;
        //this.goalY = goalY;

        //mark start and goal node
        map.setStartLocation(startX, startY);
        map.setGoalLocation(goalX, goalY);

        //Check if the goal node is also an obstacle (if it is, it is impossible to find a path there)
        if (map.getNode(goalX, goalY).isObstacle) {
            return null;
        }

        map.getStartNode().setDistanceFromStart(0);
        closedList.clear();
        openList.clear();
        openList.add(map.getStartNode());

        //while we haven't reached the goal yet
        while (openList.size() != 0) {

            //get the first Node from non-searched Node list, sorted by lowest distance from our goal as guessed by our heuristic
            Node current = openList.getFirst();

            // check if our current Node location is the goal Node. If it is, we are done.
            if (current.getX() == map.getGoalLocationX() && current.getY() == map.getGoalLocationY()) {
                return reconstructPath(current);
            }

            //move current Node to the closed (already searched) list
            openList.remove(current);
            closedList.add(current);

            //go through all the current Nodes neighbors and calculate if one should be our next step
            for (Node neighbor : current.getNeighborList()) {
                boolean neighborIsBetter;

                //if we have already searched this Node, don't bother and continue to the next one 
                if (closedList.contains(neighbor)) {
                    continue;
                }

                //also just continue if the neighbor is an obstacle
                if (!neighbor.isObstacle) {

                    // calculate how long the path is if we choose this neighbor as the next step in the path 
                    float neighborDistanceFromStart = (current.getDistanceFromStart() + map.getDistanceBetween(current, neighbor));

                    //add neighbor to the open list if it is not there
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                        neighborIsBetter = true;
                        //if neighbor is closer to start it could also be better
                    } else if (neighborDistanceFromStart < current.getDistanceFromStart()) {
                        neighborIsBetter = true;
                    } else {
                        neighborIsBetter = false;
                    }
                    // set neighbors parameters if it is better
                    if (neighborIsBetter) {
                        neighbor.setPreviousNode(current);
                        neighbor.setDistanceFromStart(neighborDistanceFromStart);
                        neighbor.setHeuristicDistanceFromGoal(getEstimatedDistanceToGoal(neighbor.getPoint(), map.getGoalPoint()));
                    }
                }

            }
        }
        return null;
    }

    private static ArrayList<String> reconstructPath(Node node) {
		ArrayList<String> path = new ArrayList<String>();
		while(!(node.getPreviousNode() == null)) {
			path.add(0,node.x+"-"+node.y);
			node = node.getPreviousNode();
		}
		return path;
	}
    
    public static float getEstimatedDistanceToGoal(Point start, Point goal) {
        float dx = goal.x - start.x;
		float dy = goal.y - start.y;
		
		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		//Optimization! Changed to distance^2 distance: (but looks more "ugly")
		
		//float result = (float) (dx*dx)+(dy*dy);
		
		
		return result;
    }

//    private ArrayList<Point> reconstructPath(Node node) {
//		ArrayList<Point> path = new ArrayList<Point>();
//		while(!(node.getPreviousNode() == null)) {
//			path.add(0,node.getPoint());
//			node = node.getPreviousNode();
//		}
//		this.shortestPath = path;
//		return path;
//	}
//    
//    public static Node getFirst(ArrayList<Node> list, Node start) {
//        Node result = (list.size() > 0) ? list.get(0) : null;
//        for (int i = 1; i < list.size(); i++) {
//            Node node = list.get(i);
//            System.out.println((node.g + node.h) + "-" + (result.g + result.h));
//
//            if (result.compareTo(node) >= 0) {
//                result = node;
//            }
//        }
//        return result;
//
//    }
//    public static ArrayList<Node> getNearNode(Node node) {
//        ArrayList<Node> nodeList = new ArrayList<Node>();
//        int x = node.x;
//        int y = node.y;
//        nodeList.add(new Node(x - 1, y - 1));
//        nodeList.add(new Node(x - 1, y));
//        nodeList.add(new Node(x - 1, y + 1));
//        nodeList.add(new Node(x, y - 1));
//        nodeList.add(new Node(x, y + 1));
//        nodeList.add(new Node(x + 1, y + 1));
//        nodeList.add(new Node(x + 1, y));
//        nodeList.add(new Node(x + 1, y - 1));
//        return nodeList;
//    }
    public static boolean findNode(ArrayList<Node> list, Node node) {
        for (Node n : list) {
            if (n.equals(node)) {
                return true;
            }
        }
        return false;
    }

    private static class SortedNodeList {

        private ArrayList<Node> list = new ArrayList<Node>();

        public Node getFirst() {
            return list.get(0);
        }

        public void clear() {
            list.clear();
        }

        public void add(Node node) {
            list.add(node);
            Collections.sort(list);
        }

        public void remove(Node n) {
            list.remove(n);
        }

        public int size() {
            return list.size();
        }

        public boolean contains(Node n) {
            return list.contains(n);
        }
    }
}
