package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Criterion;

public class CriterionDAO {

	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";

	// 全観点を取得
    public List<Criterion> findAllCriterions() {
        List<Criterion> criterionList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM CRITERION";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int criterionId = rs.getInt("CRITERION_ID");
                String criterionName = rs.getString("CRITERION_NAME");

                Criterion criterion = new Criterion(criterionId, criterionName);

                criterionList.add(criterion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return criterionList;
    }
}
