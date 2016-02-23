package com.jianqi.poppy;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.jianqi.poppy.model.Movie;
import com.jianqi.poppy.model.MovieResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jianqi.poppy.adapter.ImageAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieListFragment extends Fragment {


    private String BASE_URL = "http://api.themoviedb.org";
    private String LOG_TAG = this.getClass().getSimpleName();

    // the data
    private List<Movie>  movieList = new ArrayList<Movie>();
    private ImageAdapter mImageUrlAdapter;
    private ArrayAdapter<String> mArrayAdapter;

    private OnFragmentInteractionListener mListener;

    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));
        //mImageUrlAdapter = new ImageAdapter(                getContext(), R.layout.fragment_movie_list, weekForecast);

        mArrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.gridviewitem,R.id.testTextView,weekForecast);
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);
        Log.d(LOG_TAG, "onCreateView: setadapter");
        gridView.setAdapter(mArrayAdapter);



        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void refresh(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieInterface apiService = retrofit.create(MovieInterface.class);

        Call<MovieResult> call = apiService.getLatestMovie(BuildConfig.MOVIE_API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                Log.d(LOG_TAG, "get back response from api");
                ArrayList<String> backDropList = new ArrayList<String>();
                for (Movie movie : response.body().getResults()) {
                    backDropList.add(movie.getBackdropPath());
                    movieList.add(movie);
                }

                mArrayAdapter.clear();
                mArrayAdapter.addAll(backDropList);

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.d(LOG_TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
