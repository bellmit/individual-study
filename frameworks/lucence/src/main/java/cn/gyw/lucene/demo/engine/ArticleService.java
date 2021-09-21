package cn.gyw.lucene.demo.engine;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private static final String PATH_DIR = "/home/guanyw/temp/indexs";

    /**
     * 索引查询
     */
    public void searchDocumentByIndex() throws Exception {
        //创建分析器
        Analyzer analyzer = new StandardAnalyzer();
        // 创建查询解释器
        QueryParser queryParser = new QueryParser("title", analyzer);
        // 获取查询对象
        Query query = queryParser.parse("title:人工智能");

        // 读取索引库
        // 指定索引库目录
        FSDirectory directory = FSDirectory.open(Paths.get(PATH_DIR));

        // 读取索引
        IndexReader indexReader = DirectoryReader.open(directory);
        // 索引库查询
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 获得记录
        //参数:1、查询对象，2、指定返回最大记录
        TopDocs topDocs = indexSearcher.search(query, 10);
        // 获取返回的记录
        ScoreDoc[] scoreDcs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDcs) {
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            System.out.println("索引库ID：" + docId);
            System.out.println("编号：" + doc.get("id"));
            System.out.println("标题：" + doc.get("title"));
            System.out.println("内容：" + doc.get("content"));
        }
    }

    /**
     * 创建索引
     */
    public void createIndex(List<Document> documentList) throws IOException {
        Analyzer analyzer = new SmartChineseAnalyzer();
        // 指定索引的存储目录
        FSDirectory directory = FSDirectory.open(Paths.get(PATH_DIR));
        // 构造配置对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 构造 IndexWriter 对象，构建索引的关键对象
        IndexWriter indexWriter = new IndexWriter(directory, config);
        indexWriter.addDocuments(documentList);
        indexWriter.close();
    }

    /**
     * 根据数据创建Lucene 文档
     * @param datas
     * @return
     */
    public List<Document> getDocuments(List<Article> datas) {
        List<Document> documents = new ArrayList<>(datas.size());
        Document document = null;
        for (Article article : datas) {
            document = new Document();
            Field idField = new TextField("id", article.getId().toString(), Field.Store.YES);
            Field titleField = new TextField("title", article.getTitle(), Field.Store.YES);
            Field contentField = new TextField("content", article.getContent(), Field.Store.YES);

            document.add(idField);
            document.add(titleField);
            document.add(contentField);
            documents.add(document);
        }
        return documents;
    }

    public List<Article> getDatas() throws SQLException {
        List<Article> articles = new ArrayList<>();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from article";

        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Article article = new Article();
            article.setId(resultSet.getInt("id"));
            article.setTitle(resultSet.getString("title"));
            article.setContent(resultSet.getString("content"));
            articles.add(article);
        }

        connection.close();
        resultSet.close();
        return articles;
    }

    private Connection getConnection() {
        //四要素
        String driver = "com.mysql.jdbc.Driver";
        String url =  "jdbc:mysql://127.0.0.1:3306/lucene_data?useSSL=false";
        String username = "root";
        String password = "root";
        try {
            //获取连接并返回
            Class.forName(driver);
            return DriverManager.getConnection(url,  username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
