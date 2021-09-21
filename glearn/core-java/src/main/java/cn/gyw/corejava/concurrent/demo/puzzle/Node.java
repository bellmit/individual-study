package cn.gyw.corejava.concurrent.demo.puzzle;

import cn.gyw.platform.annotations.Immutable;

import java.util.LinkedList;
import java.util.List;

/**
 * 用于谜题解决框架的链表节点
 *
 * 说明：
 * Node 代表通过一系列的移动到达的一个位置，其中保存了到达该位置的移动以及前一个Node。
 * 只要沿着Node 回溯，就可以重新构建移动路径。
 *
 * @param <P> 位置
 * @param <M> 移动
 */
@Immutable
public class Node<P, M> {

    final P pos;
    final M move;
    final Node<P, M> prev;

    Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    /**
     * 回溯链表路径
     * @return
     */
    List<M> asMoveList() {
        List<M> solutions = new LinkedList<>();
        for (Node<P, M> node = this ; node.prev != null ; node = node.prev) {
            solutions.add(0, node.move);
        }
        return solutions;
    }
}
