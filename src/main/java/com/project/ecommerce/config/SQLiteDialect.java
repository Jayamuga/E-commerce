package com.project.ecommerce.config;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.type.SqlTypes;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3)); // SQLite version 3.x
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean dropConstraints() {
        return false;
    }

    @Override
    public boolean hasAlterTable() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return " ADD COLUMN ";
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    // ✅ Corrected method signature:
    @Override
    public String getTypeName(int jdbcTypeCode) {
        return switch (jdbcTypeCode) {
            case SqlTypes.VARCHAR, SqlTypes.LONGVARCHAR -> "TEXT";
            case SqlTypes.INTEGER, SqlTypes.BIGINT, SqlTypes.SMALLINT -> "INTEGER";
            case SqlTypes.FLOAT, SqlTypes.DOUBLE, SqlTypes.NUMERIC, SqlTypes.DECIMAL -> "REAL";
            case SqlTypes.BLOB, SqlTypes.BINARY, SqlTypes.VARBINARY, SqlTypes.LONGVARBINARY -> "BLOB";
            case SqlTypes.BOOLEAN -> "INTEGER";
            default -> super.getTypeName(jdbcTypeCode);
        };
    }
}

class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type) {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) {
        return "INTEGER PRIMARY KEY AUTOINCREMENT";
    }
}
