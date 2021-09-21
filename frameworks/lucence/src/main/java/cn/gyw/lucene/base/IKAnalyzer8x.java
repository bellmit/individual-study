package cn.gyw.lucene.base;

import org.apache.lucene.analysis.Analyzer;

/**
 * Lucene 使用IK 分词器
 */
public class IKAnalyzer8x extends Analyzer {

    // 是否智能切分
    private boolean useSmart;

    IKAnalyzer8x() {
        this(false);
    }

    IKAnalyzer8x(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        IKTokenizer8x ikTokenizer8x = new IKTokenizer8x(this.useSmart);
        return new TokenStreamComponents(ikTokenizer8x);
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }
}
