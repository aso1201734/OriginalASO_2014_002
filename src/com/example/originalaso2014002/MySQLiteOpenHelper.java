/**
 * 
 */
package com.example.originalaso2014002;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author student
 *
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	/**
	 * @param context 呼び出しコンテクスト
	 * @param name 利用DB名
	 * @param factory カーソルファクトリー
	 * @param version DBバージョン
	 */
	public MySQLiteOpenHelper(Context context) {
		super(context,"20140021201734.sqlite3",null,1);
	}

	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("CREATE TABLE IF NOT EXISTS hitokoto ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , phrase TEXT )");

	}

	/* (非 Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ
		db.execSQL("drop table Hitokoto");
		onCreate(db);

	}
	
	/**
	 * 引数のフレーズをHitokotoテーブルにインサートするプライベートメソッド
	 * @param SQLiteDatabase インサート先DBのインスタンス変数
	 * @param inputMsg インサートするメッセージ
	 */
	public void insertHitokoto(SQLiteDatabase db, String inputMsg){
		String sqlstr = "insert into Hitokoto (phrase) values('" + inputMsg +"');";
			try{
				//トランザクション開始
				db.beginTransaction();
				db.execSQL(sqlstr);
				//トランザクション成功
				db.setTransactionSuccessful();
			} catch (SQLException e) {
				Log.e("ERROR", e.toString());
			} finally {
				//トランザクション終了
				db.endTransaction();
			}
		return;
	}
	
	public String selectRandomHitokoto(SQLiteDatabase db){
		String rtString = null;
		String sqlstr = "SELECT _id, phrase FROM Hitokoto ORDER BY RANDOM();";
			try{
				//トランザクション開始
				SQLiteCursor cursor = (SQLiteCursor)db.rawQuery(sqlstr,null);
				if(cursor.getCount()!=0){
					//カーソル位置を先頭にする
					cursor.moveToFirst();
					rtString =cursor.getString(1);
				}
				cursor.close();
			} catch (SQLException e) {
				Log.e("ERROR", e.toString());
			} finally {
				//既にカーソルもcloseしてあるので、何もしない
			}
		return rtString;
	}
	
	public void DeleteHitokoto(SQLiteDatabase db, int id) {
		String sqlstr = "DELETE FROM Hitokoto where _id = " + id + ";";
		try {
			db.beginTransaction();
			db.execSQL(sqlstr);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.e("ERROR", e.toString());
		} finally {
			db.endTransaction();
		}
	}
	
	/**
	 * Hitokotoテーブルからデータをすべて取得
	 * @param SQLiteDatabase SELECTアクセスするDBのインスタンス変数
	 * @return 取得したデータの塊の表(導入表)のレコードをポイントするカーソル
	 */
	
	public SQLiteCursor selectHitokotoList(SQLiteDatabase db){
		SQLiteCursor corsor = null;
		String sqlstr = "SELECT _id,phrase FROM Hitokoto ORDER BY _id;";
		try {
			//トランザクション開始
			corsor = (SQLiteCursor)db.rawQuery(sqlstr, null);
			if(corsor.getCount()!=0) {
				corsor.moveToFirst();
			}
		} catch (SQLException e) {
			Log.e("ERROR",e.toString());
		} finally {
		}
		return corsor;
	}
}
