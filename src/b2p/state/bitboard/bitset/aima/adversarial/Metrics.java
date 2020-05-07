package b2p.state.bitboard.bitset.aima.adversarial;

public class Metrics {
    private int nodeExpanded;
    private int currDepthLimit;

    public Metrics() {
        nodeExpanded = 0;
        currDepthLimit = 0;
    }

    public int getNodeExpanded() { return nodeExpanded; }
    public int getCurrDepthLimit() { return currDepthLimit; }

    public void setNodeExpanded(int nodeExpanded) { this.nodeExpanded = nodeExpanded; }
    public void setCurrDepthLimit(int currDepthLimit) { this.currDepthLimit = currDepthLimit; }

    public void updateMetrics(int depth) {
        nodeExpanded += 1;
        currDepthLimit = Math.max(currDepthLimit, depth);
    }

}
