package com.client.utedating.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.client.utedating.R;
import com.client.utedating.activities.MainActivity;
import com.client.utedating.adapters.LikedAdapter;
import com.client.utedating.models.User;
import com.client.utedating.models.UsersLikedModel;
import com.client.utedating.retrofit.RetrofitClient;
import com.client.utedating.retrofit.UserApiService;
import com.client.utedating.utils.MySharedPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikedFragment extends Fragment {
    List<User> userList;
    RecyclerView recyclerViewLiked;
    LinearLayout linearLayoutEmptyLiked;
    LikedAdapter adapter;

    UserApiService userApiService;
    User user;

    MainActivity mainActivity;
    public LikedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData() {
        user = MySharedPreferences.getUserInfo(getActivity(),"user");

        userApiService = RetrofitClient.getInstance().create(UserApiService.class);
        userApiService.getUserSwipedRight(user.get_id()).enqueue(new Callback<UsersLikedModel>() {
            @Override
            public void onResponse(Call<UsersLikedModel> call, Response<UsersLikedModel> response) {
                if(response.isSuccessful()){
                    userList = response.body().getResult().getUserSwipedRight();
                    if(userList.size() == 0){
                        linearLayoutEmptyLiked.setVisibility(View.VISIBLE);
                        recyclerViewLiked.setVisibility(View.GONE);
                    }
                    else{
                        linearLayoutEmptyLiked.setVisibility(View.GONE);
                        recyclerViewLiked.setVisibility(View.VISIBLE);
                        adapter = new LikedAdapter(userList);
                        recyclerViewLiked.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersLikedModel> call, Throwable t) {

            }
        });

    }

    private void initView(View view) {
        recyclerViewLiked = view.findViewById(R.id.recyclerViewLiked);
        linearLayoutEmptyLiked = view.findViewById(R.id.linearLayoutEmptyLiked);
        recyclerViewLiked.setLayoutManager( new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerViewLiked.setHasFixedSize(true);
        mainActivity = (MainActivity) getActivity();
    }
}