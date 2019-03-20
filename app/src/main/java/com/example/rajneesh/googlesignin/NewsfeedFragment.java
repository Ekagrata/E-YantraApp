package com.example.rajneesh.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rajneesh.googlesignin.Activities.CommentActivity;
import com.example.rajneesh.googlesignin.CustomAdapters.NewsfeedRecyclerAdapter;
import com.example.rajneesh.googlesignin.ResponseHolderClasses.NewsFeedResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.

 */
public class NewsfeedFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NewsFeedResponse> feeds;
    NewsfeedRecyclerAdapter adapter;
    int id;

    public NewsfeedFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_newsfeed, container, false);

        recyclerView= view.findViewById(R.id.feedlist);
        feeds= new ArrayList<>();
        fetchfeeds();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    private void fetchfeeds () {
        retrofit2.Call<List<NewsFeedResponse>> call = APIClient.getInstance().getApi().getfeeds();
        call.enqueue(new Callback<List<NewsFeedResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<NewsFeedResponse>> call, retrofit2.Response<List<NewsFeedResponse>> response) {
                feeds = (ArrayList) response.body();
                adapter = new NewsfeedRecyclerAdapter(getActivity(), feeds, new NewsfeedRecyclerAdapter.OnItemClickListner() {
                    @Override
                    public void OnItemClicked(int position) {
                        // Toast.makeText(Main2Activity.this, "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnCommentSelected(int position) {
                        Intent intent = new Intent(getActivity(), CommentActivity.class);
                        int id = feeds.get(position).getNewsid();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("token", "newsfeed");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public int getid(int position) {
                        return id= feeds.get(position).getNewsid();


//                            Log.d("comno Main2",comno);
//                            return comno;
//
                    }


                }, getActivity().getWindowManager());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(retrofit2.Call<List<NewsFeedResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }






}
