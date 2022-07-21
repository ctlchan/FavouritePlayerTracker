package com.example.favouriteplayertracker.data.repository.userList

import android.util.Log
import com.example.favouriteplayertracker.data.local.Teams.TeamDataDao
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAverages
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAvgDao
import com.example.favouriteplayertracker.data.remote.ApiHelper
import com.example.favouriteplayertracker.data.remote.nbaApi.NbaApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException

class UserListRepositoryImpl(
    private val userListDao: UserListDao,
    private val nbaApi: NbaApi,
    private val teamDao: TeamDataDao,
    private val seasonAvgDao: SeasonAvgDao
): UserListRepository {

    private val TAG = "UserListRepoImpl"


    override fun getFavouritePlayers(): Flow<List<FavouritePlayer>> {
        return userListDao.getAllPlayers()
    }

    override fun getSeasonAverages(): Flow<List<SeasonAverages>> {
        // TODO: Determine logic for WHEN to make API call
        return seasonAvgDao.getSeasonAverages()
    }

    override suspend fun getPlayerNames(): List<String> {
        return userListDao.getPlayerNames()
    }

    override suspend fun getSelectedId(): Int {
        return userListDao.getSelectedId()
    }

    override suspend fun getIdToNameMap(): Map<Long, String> {
        return userListDao.getIdToNameMap()
    }

    override suspend fun addFavouritePlayer(name: String) {

        // Check if they're already being tracked before making API request.
        if (!userListDao.playerExists(name)) {

            val response = try {
                nbaApi.getPlayer(name)
            }
            catch (e: IOException) {
                // Most likely instance: no internet connection
                Log.e(TAG, "IOException, you might not have internet connection.")
                return
            }
            catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response.")
                return
            }

            if (response.isSuccessful && response.body() != null) {
                try {
                    Log.i(TAG, response.body().toString())
                } catch (e: NullPointerException) {
                    Log.i(TAG, "null...")
                }

                val data = response.body()!!.data[0].toMutableMap()

                Log.i(TAG, data.toString())


                val dataTeamAsString = data["team"].toString()
                Log.i("data[team]", dataTeamAsString)



                val teamMap = data["team"] as Map<*, *>
                Log.i("dataMap", teamMap::class.java.simpleName)


                val newTeam = ApiHelper.mapToTeamEntity(teamMap as Map<String, *>)

                val newTeamId = newTeam.id

                val newPlayer = FavouritePlayer(
                    name = data["first_name"].toString() + " " + data["last_name"].toString(),
                    height_feet = if (data["height_feet"] == null) {
                       data["height_feet"] as Int?
                   } else {
                        ApiHelper.doubleToLong(data["height_feet"].toString()).toInt()
                          },
                    height_inches = if (data["height_inches"] == null) {
                        data["height_inches"] as Int?
                    } else {
                        ApiHelper.doubleToLong(data["height_inches"].toString()).toInt()
                    },
                    id = if ('E' in data["id"].toString()) {
                        ApiHelper.scientificNotationToLong(data["id"].toString())
                    } else {
                        ApiHelper.doubleToLong(data["id"].toString())
                           },
                    position = data["position"].toString(),
                    team_id = newTeamId,
                    weight_pounds = if (data["weight_pounds"] == null) {
                        data["weight_pounds"] as Int?
                    } else {
                        ApiHelper.doubleToLong(data["weight_pounds"].toString()).toInt()
                    },
                )

                userListDao.addPlayer(newPlayer)
                teamDao.addTeam(newTeam)

            }

        }
    }

    override suspend fun addTeam(team: TeamEntity) {
        teamDao.addTeam(team)
    }

    override suspend fun removeFavouritePlayer(player: FavouritePlayer) {
            userListDao.deletePlayer(player)
    }

    override suspend fun unselectPlayer() {
        userListDao.unselect()
    }

    override suspend fun reselectPlayer(name: String) {
        userListDao.reselect(name)
    }

    override fun getSelected(): Flow<FavouritePlayer> {
        return userListDao.getSelected()
    }

    override fun getTeam(id: Int): Flow<TeamEntity> {
        return teamDao.getTeam(id)
    }

}





