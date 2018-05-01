package sameeh.com.nbapp.Screens.Main.Players;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sameeh.com.nbapp.Base.BaseFragment;
import sameeh.com.nbapp.Base.LoggedInScreen;
import sameeh.com.nbapp.LocalData.LocalStorageManager;
import sameeh.com.nbapp.Models.ApiError;
import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Models.TeamPlayersAdapter;
import sameeh.com.nbapp.Models.dao.DaoSession;
import sameeh.com.nbapp.MyApp;
import sameeh.com.nbapp.R;
import sameeh.com.nbapp.api.LoggedIn.LoggedInApiManager;


public class PlayersListFragment extends BaseFragment implements LoggedInScreen {

    private PlayersListFragmentListener listener;

    private LoggedInApiManager loggedInApiManager;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.list)
    RecyclerView playersList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public PlayersListFragment() {
        // Required empty public constructor
    }

    public static PlayersListFragment newInstance() {
        PlayersListFragment fragment = new PlayersListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInApiManager = LoggedInApiManager.getInstance(getActivity());
        localStorageManager = LocalStorageManager.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        showProgressBar();
        fetchAllPlayers();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        playersList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showPlayersList(List<Player> players) {
        playersList.setAdapter(new TeamPlayersAdapter(players));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void fetchAllPlayers() {

        loggedInApiManager.getPlayers().enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    List<Player> players = response.body();
                    savePlayersInLocalDatabase(players);
                    showPlayersList(players);
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        ApiError apiError = parseApiErrorString(errorJson);
                        actBasedOnApiErrorCode(apiError);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                hideProgressBar();
                getPlayersInLocalDatabase();
            }
        });
    }

    private void savePlayersInLocalDatabase(List<Player> players) {
        DaoSession daoSession = ((MyApp) (getActivity().getApplication())).getDaoSession();
        localStorageManager.savePlayersInLocalDatabase(daoSession, players);
    }

    private void getPlayersInLocalDatabase() {
        DaoSession daoSession = ((MyApp) (getActivity().getApplication())).getDaoSession();
        List<Player> playersFromDatabase = localStorageManager.getPlayersInLocalDatabase(daoSession);
        showPlayersList(playersFromDatabase);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.new_player)
    public void requestShowNewPlayerPage() {
        if (listener != null) {
            listener.onRequestCreateNewPlayer();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayersListFragmentListener) {
            listener = (PlayersListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PlayersListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void notLoggedInAnymore() {
        if (listener != null) {
            listener.onErrorFetchingPlayers();
        }
    }

    public interface PlayersListFragmentListener {
        void onRequestCreateNewPlayer();

        void onErrorFetchingPlayers();
    }

}