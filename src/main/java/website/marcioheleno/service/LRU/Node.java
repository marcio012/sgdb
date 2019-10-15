package website.marcioheleno.service.LRU;

import website.marcioheleno.model.bloco.dados.BlocoDado;

public class Node {

    private Node nextNode;
    private BlocoDado block;

    public Node() {}

    public Node(BlocoDado block) {
        this.block = block;
        this.nextNode = null;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public BlocoDado getBlock() {
        return block;
    }

    public void setBlock(BlocoDado block) {
        this.block = block;
    }
}
