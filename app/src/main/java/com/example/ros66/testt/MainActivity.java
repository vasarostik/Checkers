package com.example.ros66.testt;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    private static String url = "https://api.androidhive.info/contacts/";


	int switcher = 1;
	String click_move = "empty";
	GridView gridView;
	CheckersBoard cb = new CheckersBoard();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        ArrayList contactList = new ArrayList<>();

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int h = metrics.heightPixels;
		int w = metrics.widthPixels;
		System.out.println(h);
		System.out.println(w);
		super.onCreate(savedInstanceState);



		setContentView(R.layout.activity_main);


		//
		// JsonObjectRequest.java
		String URL ="https://api.myjson.com/bins/7tghr";
		RequestQueue queue1 = Volley.newRequestQueue(this);
		JsonObjectRequest request1 = new JsonObjectRequest(
				URL,
				null,
				new com.android.volley.Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
					    try{
                            JSONArray g = response.getJSONArray("contacts");
                            Log.i("Response: ",g.toString());
                        }
                        catch (JSONException e){

                        }
					}
				},
				new com.android.volley.Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("ResponseError: ", error.toString());
					}
				}


		);
		queue1.add(request1);
//













		CheckersPiece all_pieces[] = new CheckersPiece[24];
		for (int i = 0; i < 12; i++) {
			all_pieces[i] = cb.dark_pieces[i];
			all_pieces[i+12] = cb.light_pieces[i];
		}
		String myvec[] = new String[64];
		myvec = cb.vec_string();
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, myvec));
		Context context = getApplicationContext();
		CharSequence text = "White Player starts!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}











	
	public void pressed(View view) {
		System.out.println("pressed");
		EditText e = (EditText) findViewById(R.id.editText1);
		System.out.println(e.getText().toString());
		if (e.getText().toString().length() >= 1) {
			CheckersMove cm = new CheckersMove(e.getText().toString());
			cb.board_move_and_capture(cm);
			String myvec[] = new String[64];
			myvec = cb.vec_string();
			gridView = (GridView) findViewById(R.id.gridview);
			gridView.setAdapter(new ImageAdapter(this, myvec));
		}
	}
	
	public void image_pressed(View view) {
		String s = new String();
		s = (String) view.getTag();
		System.out.println(s);
		if (switcher == 1) {
			System.out.println(s);
			CheckersPosition cp = new CheckersPosition();
			cp.position_parse(s);
			CheckersPiece pp = new CheckersPiece();
			pp = cb.board_get_piece_at(cp.row, cp.col);
			if (cb.turn == 1) {
				if (pp.toString().equals("w")) {
					click_move = s;
					switcher = -1;
				} else if (pp.toString().equals("W")) {
					click_move = s;
					switcher = -1;
				} else {
					Context context = getApplicationContext();
					CharSequence text = "White Player's turn!";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			} else {
				if (pp.toString().equals("b")) {
					click_move = s;
					switcher = -1;
				} else if (pp.toString().equals("B")) {
					click_move = s;
					switcher = -1;
				} else {
					Context context = getApplicationContext();
					CharSequence text = "Black Player's turn!";
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
			}
		} else {
			click_move = click_move + "-" + s;
			System.out.println(click_move);
			int j = cb.board_move_and_capture(new CheckersMove(click_move));
			if (j == -1) {
				Context context = getApplicationContext();
				CharSequence text = "Invalid Move, try again!";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				click_move = "";
				switcher = 1;
			} else {
				String myvec[] = new String[64];
				myvec = cb.vec_string();
				gridView = (GridView) findViewById(R.id.gridview);
				gridView.setAdapter(new ImageAdapter(this, myvec));
				switcher = 1;
				click_move = "";
				cb.board_print();
			}
		}
	}
	public void leave(View view)
	{
		this.finish();
	}





}
