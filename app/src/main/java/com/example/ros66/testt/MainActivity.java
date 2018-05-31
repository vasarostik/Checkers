package com.example.ros66.testt;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;




public class MainActivity extends Activity {



	int switcher = 1;
	int iter = 1;
	String click_move = "empty";
	GridView gridView;
	CheckersBoard cb = new CheckersBoard();
	String ros="";
    String resultCount;
    String result2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int h = metrics.heightPixels;
		int w = metrics.widthPixels;
		System.out.println(h);
		System.out.println(w);
		super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);


		// JsonObjectRequest.java
		String URL ="https://ba0818f3-9da6-4a47-a54e-67951fc06e75.mock.pstmn.io/move";
		RequestQueue queue1 = Volley.newRequestQueue(this);
		//RequestQueue mRequestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest request1 = new JsonObjectRequest(

				URL,
				null,
				new com.android.volley.Response.Listener<JSONObject>() {



					@Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            resultCount = response.optString("from");
                            result2 = response.optString("to");
							Log.i("from: ", resultCount);
                            Log.i("to: ", result2);
                           /* int resultCount = response.optInt("resultCount");
                            if (resultCount > 0) {
                                Gson gson = new Gson();
                                JSONArray jsonArray = response.optJSONArray("results");
                                if (jsonArray != null) {

                                    SongInfo[] songs = gson.fromJson(jsonArray.toString(), SongInfo[].class);
                                    if (songs != null && songs.length > 0) {
                                        for (SongInfo song : songs) {
                                            Log.i("LOG", song.kind);
                                            if(!(song.trackName.isEmpty())){
                                            setRos(song.trackName); }

                                            Log.i("DOOOO", getRos());
 // somewhere here must be a await

                                        }
                                    }
                                }
                            }*/
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
		queue1.start();
        Log.i("hello","Rostik!");



        CheckersPiece all_pieces[] = new CheckersPiece[24];
		for (int i = 0; i < 12; i++) {
			all_pieces[i] = cb.dark_pieces[i];
			all_pieces[i+12] = cb.light_pieces[i];
		}
		String myvec[];
		myvec = cb.vec_string();
		gridView = findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this, myvec));
		Context context = getApplicationContext();
		CharSequence text = "White Player starts!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

    public String getRos() {
        return ros;
    }

    public void setRos(String ros) {
        this.ros = ros;
    }

    public void pressed(View view) {
		System.out.println("pressed");
		EditText e = findViewById(R.id.editText1);
		System.out.println(e.getText().toString());
		if (e.getText().toString().length() >= 1) {

			CheckersMove cm = new CheckersMove(e.getText().toString());




			cb.board_move_and_capture(cm);
			String myvec[];
			myvec = cb.vec_string();
			gridView = findViewById(R.id.gridview);
			gridView.setAdapter(new ImageAdapter(this, myvec));
		}
	}

	public void image_pressed(View view) {
		String s;

		//Hardcode for presentation
		/*	 if(iter==1)
		s = "B6";
		else if(iter==2)
			s = "C5";
		else if(iter ==5)
			s="C5";
		else if(iter ==6)
            s="A3";

		else
		s = (String) view.getTag();*/

		//hardcode
        if(iter==1)
            s = resultCount;
        else if(iter==2)
            s = result2;
        else
		s = (String) view.getTag();

		System.out.println(s);
		if (switcher == 1) {
			System.out.println(s);
			CheckersPosition cp = new CheckersPosition();
			cp.position_parse(s);

            Log.i("YORKSHIRE",getRos()+"DOne");
			CheckersPiece pp;
			pp = cb.board_get_piece_at(cp.row, cp.col);
			if (cb.turn == 1) {
				switch (pp.toString()) {
					case "w":
						click_move = s;
						switcher = -1;
						break;
					case "W":
						click_move = s;
						switcher = -1;
						break;
					default:
						Context context = getApplicationContext();
						CharSequence text = "White Player's turn!";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						break;
				}
			} else {
				switch (pp.toString()) {
					case "b":
						click_move = s;
						switcher = -1;
						break;
					case "B":
						click_move = s;
						switcher = -1;
						break;
					default:
						Context context = getApplicationContext();
						CharSequence text = "Black Player's turn!";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						break;
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
				String myvec[];
				myvec = cb.vec_string();
				gridView = findViewById(R.id.gridview);
				gridView.setAdapter(new ImageAdapter(this, myvec));
				switcher = 1;
				click_move = "";
				cb.board_print();
			}
		}
		iter++;
	}
	public void leave(View view)
	{
		this.finish();
	}



















}
