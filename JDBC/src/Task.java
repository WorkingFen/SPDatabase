import java.sql.PreparedStatement;

public interface Task<R> {
	R execute(PreparedStatement ps) throws Exception;
}

