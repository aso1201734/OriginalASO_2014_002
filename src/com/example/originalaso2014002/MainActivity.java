package com.example.originalaso2014002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{
	
	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	protected void onResume() {
		super.onResume();
		
		//登録ボタン変数にリスナーを登録する
		Button btnENTRY = (Button)findViewById(R.id.btnDELETE);
		btnENTRY.setOnClickListener(this);
		//メンテボタン変数にリスナーを登録する
		Button btnMAINTE = (Button)findViewById(R.id.btnBACK);
		btnMAINTE.setOnClickListener(this);
		//一言チェックボタン変数にリスナーを登録する
		Button btnCHECK = (Button)findViewById(R.id.btnCHECK);
		btnCHECK.setOnClickListener(this);
		
		//クラスのフィールド変数がnullなら、DB空間オープン
		if(sdb == null) {
			helper =new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			//異常終了
			return;
		}
	}
	
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		
		//生成して代入用のIntentインスタンス変数を用意
		Intent intent = null;
		switch(v.getId()) {
			case R.id.btnDELETE:
				//エディットテキストからの入力内容を取り出す
				EditText etv = (EditText)findViewById(R.id.edtMsg);
				String inputMsg = etv.getText().toString();
				
				//inputMsgがnullでない、かつ、空でない場合のみ、if文内を実行
				if(inputMsg!=null!=inputMsg.isEmpty()){
					//MySQLiteOpenHelperのインサートメソッドを呼び出し
					helper.insertHitokoto(sdb, inputMsg);
				}
				
				//入力欄をクリア
				etv.setText("");
				break;
			case R.id.btnBACK: //メンテボタンが押された
				//インテントのインスタンス生成
				intent = new Intent(MainActivity.this,MaintenanceActivity.class);
				//次画面のアクティビティ起動
				startActivity(intent);
				break;
				
			case R.id.btnCHECK: //一言チェックボタンが押された
				//MySQLiteOpenHelperのセレクト一言メソッドを呼び出して一言をランダムに取得
				String strHitokoto = helper.selectRandomHitokoto(sdb);
				//インテントのインスタンス生成
				intent = new Intent(MainActivity.this,HitokotoActivity.class);
				//インテントに一言を混入
				intent.putExtra("hitokoto",strHitokoto);
				//次画面のアクティビティ起動
				startActivity(intent);
				break;
			}
				
			}
			
	}
