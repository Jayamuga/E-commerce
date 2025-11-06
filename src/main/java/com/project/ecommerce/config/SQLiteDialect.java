package com.project.ecommerce.config;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.type.SqlTypes;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3)); // SQLite 3.x
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

    // ✅ Updated for Hibernate 6.x (removed @Override)
    public String getTypeName(int code, long length, int precision, int scale) {
        switch (code) {
            case SqlTypes.VARCHAR:
            case SqlTypes.LONGVARCHAR:
                return "TEXT";
            case SqlTypes.INTEGER:
            case SqlTypes.BIGINT:
            case SqlTypes.SMALLINT:
                return "INTEGER";
            case SqlTypes.FLOAT:
            case SqlTypes.DOUBLE:
            case SqlTypes.NUMERIC:
            case SqlTypes.DECIMAL:
                return "REAL";
            case SqlTypes.BLOB:
            case SqlTypes.BINARY:
            case SqlTypes.VARBINARY:
            case SqlTypes.LONGVARBINARY:
                return "BLOB";
            case SqlTypes.BOOLEAN:
                return "INTEGER";
            default:
                return "TEXT"; // Fallback type
        }
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
        // ✅ SQLite uses INTEGER PRIMARY KEY AUTOINCREMENT for identity
        return "INTEGER PRIMARY KEY AUTOINCREMENT";
    }
}
