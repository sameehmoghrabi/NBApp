package sameeh.com.nbapp.LocalData;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sameeh.com.nbapp.Models.Player;
import sameeh.com.nbapp.Models.User;
import sameeh.com.nbapp.Models.dao.DaoSession;
import sameeh.com.nbapp.Models.dao.PlayerDAO;
import sameeh.com.nbapp.Models.dao.PlayerDAODao;

/**
 * Created by samee on 4/30/2018.
 */

    public class LocalStorageManager {

        private Gson gson;
        private final String USER_KEY = "PROFILE";
        private static LocalStorageManager localStorageManager;
        private SharedPreferences sharedPreferences;

        private LocalStorageManager(Context context) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            gson = new Gson();
        }

        public static LocalStorageManager getInstance(Context context) {
            if (localStorageManager == null) {
                localStorageManager = new LocalStorageManager(context);
            }
            return localStorageManager;
        }

        public User getUser() {
            String userJson = sharedPreferences.getString(USER_KEY, null);
            if (userJson == null) {
                return null;
            }
            try {
                return gson.fromJson(userJson, User.class);
            } catch (Exception e) {
                return null;
            }
        }

        public void saveUser(User user) {
            String userJson = gson.toJson(user);
            sharedPreferences
                    .edit()
                    .putString(USER_KEY, userJson)
                    .commit();
        }

        public void deleteUser() {
            sharedPreferences
                    .edit()
                    .putString(USER_KEY, null)
                    .commit();
        }

    public void savePlayersInLocalDatabase(DaoSession daoSession, List<Player> players) {
        PlayerDAODao playerDao = daoSession.getPlayerDAODao();
        for (int i = 0; i < players.size(); i++) {
            PlayerDAO playerdao = new PlayerDAO();
            playerdao.setId(players.get(i).getId());
            playerdao.setFname(players.get(i).getFirstName());
            playerdao.setLname(players.get(i).getLastName());
            playerdao.setAge(players.get(i).getStatus());
            playerdao.setPlayerID(players.get(i).getPlayerID());
            playerDao.insertOrReplace(playerdao);
        }
    }

    public List<Player> getPlayersInLocalDatabase(DaoSession daoSession) {
        PlayerDAODao playerDao = daoSession.getPlayerDAODao();
        List<PlayerDAO> playerDAOS = playerDao.loadAll();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerDAOS.size(); i++) {
            players.add(new Player(playerDAOS.get(i).getFname(), playerDAOS.get(i).getLname(), playerDAOS.get(i).getAge(), playerDAOS.get(i).getPlayerID()));
        }
        return players;
    }


    }
