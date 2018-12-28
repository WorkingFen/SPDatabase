import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DBTest {
	public static class Localization {
		private String place;
		private String country;
		private String location;

		public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public Localization(String place, String country, String location) {
			super();
			this.place = place;
			this.country = country;
			this.location = location;
		}

		public Localization() {
		}
		
		public String toString() {
			return String.format("Localization(%s, %s, %s)", place, country, location); 
		}
	}

	public final static ResultSetToBean<Localization> localizationConverter = new ResultSetToBean<Localization>() {
		public Localization convert(ResultSet rs) throws Exception {
			Localization l = new Localization();
			l.setPlace(rs.getString("PLACE"));
			l.setCountry(rs.getString("COUNTRY"));
			l.setLocation(rs.getString("LOCATION"));
			return l;
		}
	};

	public static void main(String[] args) {

		boolean result = DBManager.run(new Task<Boolean>() {
			public Boolean execute(PreparedStatement ps) throws Exception {
				ps.setString(1, "Obserwatorium Astronomiczne");
				ps.setString(2, "Polska");
				ps.setString(3, "53 46 20.7 N 20 29 20.1 E");
				return ps.executeUpdate() > 0;
			}
		}, "insert into localization values(?, ?, ?)");

		System.out.println(result ? "Udalo sie" : "Nie udalo sie");

		List<Localization> localizations = DBManager
				.run(new Query() {
					public void prepareQuery(PreparedStatement ps)
							throws Exception {
						//ps.setInt(1, 10000);
					}
				}, localizationConverter,
						"select place, country, location from localization");
		
		for (Localization l: localizations) {
			System.out.println(l);
		}
		
		result = DBManager.run(new Task<Boolean>() {
			public Boolean execute(PreparedStatement ps) throws Exception {
				ps.setString(1, "Planetarium");
				ps.setString(2, "53 46 20.7 N 20 29 20.1 E");
				return ps.executeUpdate() > 0;
			}
		}, "update localization set place = ? where location = ?");

		System.out.println(result ? "Udalo sie" : "Nie udalo sie");
		
		localizations = DBManager
				.run(new Query() {
					public void prepareQuery(PreparedStatement ps)
							throws Exception {
						//ps.setInt(1, 10000);
					}
				}, localizationConverter,
						"select place, country, location from localization");
		
		for (Localization l: localizations) {
			System.out.println(l);
		}
		
		result = DBManager.run(new Task<Boolean>() {
			public Boolean execute(PreparedStatement ps) throws Exception {
				ps.setString(1, "Planetarium");
				return ps.executeUpdate() > 0;
			}
		}, "delete from localization where place = ?");

		System.out.println(result ? "Udalo sie" : "Nie udalo sie");
		
		localizations = DBManager
				.run(new Query() {
					public void prepareQuery(PreparedStatement ps)
							throws Exception {
						//ps.setInt(1, 10000);
					}
				}, localizationConverter,
						"select place, country, location from localization");
		

		for (Localization l: localizations) {
			System.out.println(l);
		}
	}

}
