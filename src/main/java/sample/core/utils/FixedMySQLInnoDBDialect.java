package sample.core.utils;

import java.sql.Types;

import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.type.StandardBasicTypes;

public class FixedMySQLInnoDBDialect extends MySQLInnoDBDialect {
	protected void registerVarcharTypes() {
		super.registerVarcharTypes();
		registerColumnType(Types.CHAR, 255, "char($l)");
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
	}

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB";
	}
}
