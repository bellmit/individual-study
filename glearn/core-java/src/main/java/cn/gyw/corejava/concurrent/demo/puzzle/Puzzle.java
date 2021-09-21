package cn.gyw.corejava.concurrent.demo.puzzle;

import java.util.Set;

/**
 * 谜题框架
 *
 * 定义：包含一个初始位置、一个结束位置、用于判断是否有效的移动的规则集
 *
 * 规则集包含两部分：
 * 1）计算从目标位置所有的合法移动
 * 2）每次移动的结果位置
 *
 * @param <P>
 * @param <M>
 */
public interface Puzzle<P, M> {

    /**
     * 初始位置
     * @return
     */
    P initialPosition();

    /**
     * 判断是否目标位置
     * @param position
     * @return
     */
    boolean isGoal(P position);

    /**
     * 规则集：目标位置的所有合法移动集
     * @param position
     * @return
     */
    Set<M> legalMoves(P position);

    /**
     * 规则集：每次移动的结果位置
     * @param position
     * @param move
     * @return 结束位置
     */
    P move(P position, M move);
}
