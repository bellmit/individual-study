package cn.gyw.platform.crawler.dao;

import cn.gyw.platform.crawler.entity.Article;
import cn.gyw.platform.crawler.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {

    public boolean batchInsert(List<Article> articles) {

        Connection connection = DBUtil.getConnection();
        String sql = "insert into article values (?,?,?)";
        try {
            List<PreparedStatement> sqlList = new ArrayList<>(articles.size());
            for (Article article : articles) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, article.getId());
                ps.setString(2, article.getTitle());
                ps.setString(3, article.getContent());
                sqlList.add(ps);
            }

            for (PreparedStatement statement : sqlList) {
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
