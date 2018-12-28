import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private final static String url = "jdbc:oracle:thin:@//ora3.elka.pw.edu.pl:1521/ora3inf.elka.pw.edu.pl";

	private final static String username = "pzawadka";

	private final static String password = "pzawadka";

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver not found.");
			System.exit(1);
		}
	}

	public static <R> R run(Task<R> task, String sql) {
		R result = null;
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);

			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(sql);
				result = task.execute(stmt);
				conn.commit();
				return result;
			} catch (Exception ex) {
				System.err.println("Cannot execute a statement : "
						+ ex.getMessage());
				conn.rollback();
				throw new RuntimeException(ex);
			} finally {
				if (stmt != null)
					stmt.close();
			}
		} catch (Exception ex) {
			System.err.println("Cannot open a connection : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static <R, B> List<B> run(Query query, ResultSetToBean<B> converter,
			String sql) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);

			List<B> list = new ArrayList<B>();
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(sql);
				query.prepareQuery(stmt);
				ResultSet rs = stmt.executeQuery();
				try {
					while (rs.next()) {
						list.add(converter.convert(rs));
					}
					return list;
				} catch (Exception ex) {
					System.err.println("Cannot convert bean : "
							+ ex.getMessage());
					throw new RuntimeException(ex);
				} finally {
					rs.close();
				}
			} catch (Exception ex) {
				System.err.println("Cannot execute a statement : "
						+ ex.getMessage());
				throw new RuntimeException(ex);
			} finally {
				if (stmt != null)
					stmt.close();
			}
		} catch (Exception ex) {
			System.err.println("Cannot open a connection : " + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignore) {
			}
		}
	}

}
