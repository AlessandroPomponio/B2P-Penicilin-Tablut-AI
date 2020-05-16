package b2p.search.aima;

/**
 * This class defines all the parameters needed to understand how well the tree search exploration is doing
 * @see <a href="https://github.com/aimacode/aima-java">Aima Java</a>
 *
 * @author Alessandro Buldini
 * @author Alessandro Pomponio
 * @author Federico Zanini
 */
public class Metrics {
    /**
     * Contains all the nodes expanded during the current search
     */
    private int nodesExpanded;
    /**
     * Contains the current depth limit for Iterative Deepening algorithms
     */
    private int currDepthLimit;

    /**
     * Basic constructor that sets both {@code nodeExpanded} and {@code currDepthLimit}
     */
    public Metrics() {
        nodesExpanded = 0;
        currDepthLimit = 0;
    }

    /**
     * Returns the variable {@code nodesExpanded}
     * @return the variable {@code nodesExpanded}
     */
    public int getNodesExpanded() {
        return nodesExpanded;
    }

    /**
     * Returns the variable {@code currDepthLimit}
     * @return the variable {@code currDepthLimit}
     */
    public int getCurrDepthLimit() {
        return currDepthLimit;
    }

    /**
     * Sets the variable {@code nodesExpanded} to the value passed as input
     * @param nodesExpanded value to be set within the class
     */
    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    /**
     * Sets the variable {@code currDepthLimit} to the value passed as input
     * @param currDepthLimit value to be set within the class
     */
    public void setCurrDepthLimit(int currDepthLimit) {
        this.currDepthLimit = currDepthLimit;
    }

    /**
     * Function that needs to be invoked every time a new node is explored
     * @param depth current depth within the search algorithm
     */
    public void updateMetrics(int depth) {
        nodesExpanded++;
        currDepthLimit = Math.max(currDepthLimit, depth);
    }

}
