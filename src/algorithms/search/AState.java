package algorithms.search;

public abstract class AState {
    int cost ;
    AState cameFrom ;

    public AState(int cost , AState cameFrom) {
        this.cost = cost;
        this.cameFrom = cameFrom ;
    }

    public Solution backTrack() {
        Solution solution = new Solution();
        AState curr = this ;
        while(curr != null) {
            solution.addToStartOfSolutionPath(curr);
            curr = curr.cameFrom ;
        }
        return solution ;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public AState getCameFrom() {
        return cameFrom;
    }
    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

}
