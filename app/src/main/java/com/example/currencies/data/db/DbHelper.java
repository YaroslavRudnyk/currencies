package com.example.currencies.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.currencies.data.db.table.CurrenciesTable;
import com.example.currencies.data.db.table.RatesTable;

public class DbHelper extends SQLiteOpenHelper {

  public static final String DB_NAME = "db_currencies";
  public static final int DB_VERSION = 2;

  public DbHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    db.setForeignKeyConstraintsEnabled(true);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    createDbTables(db);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion < 2)
      updateToV2(db);
  }

  ///////////////////////////////////////////////////////////////////////////
  // PRIVATE SECTION
  ///////////////////////////////////////////////////////////////////////////

  private void updateToV2(SQLiteDatabase db) {
    db.execSQL("ALTER TABLE "
        + CurrenciesTable.TABLE
        + " ADD COLUMN "
        + CurrenciesTable.COLUMN_IS_FAVORITE
        + " INTEGER DEFAULT 0");
  }

  private void createDbTables(SQLiteDatabase db) {
    // Currencies
    db.execSQL(CurrenciesTable.getCreateTableQuery());
    db.execSQL(CurrenciesTable.getCreateIndexQuery());
    // Rates
    db.execSQL(RatesTable.getCreateTableQuery());
  }
}
