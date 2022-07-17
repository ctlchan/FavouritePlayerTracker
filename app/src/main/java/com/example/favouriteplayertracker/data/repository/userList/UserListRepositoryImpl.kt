package com.example.favouriteplayertracker.data.repository.userList

import android.util.Log
import com.example.favouriteplayertracker.data.local.Teams.TeamDataDao
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.remote.NbaApi
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException
import kotlin.math.pow

class UserListRepositoryImpl(
    private val userListDao: UserListDao,
    private val nbaApi: NbaApi,
    private val teamDao: TeamDataDao
): UserListRepository {

    private val TAG = "UserListRepoImpl"


    override fun getFavouritePlayers(): Flow<List<FavouritePlayer>> {
        return userListDao.getAllPlayers()
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


                val newTeam = mapToTeamEntity(teamMap as Map<String, *>)

                val newTeamId = newTeam.id

                val newPlayer = FavouritePlayer(
                    name = data["first_name"].toString() + " " + data["last_name"].toString(),
                    height_feet = if (data["height_feet"] == null) {
                       data["height_feet"] as Int?
                   } else {
                          doubleToLong(data["height_feet"].toString()).toInt()
                          },
                    height_inches = if (data["height_inches"] == null) {
                        data["height_inches"] as Int?
                    } else {
                        doubleToLong(data["height_inches"].toString()).toInt()
                    },
                    id = if ('E' in data["id"].toString()) {
                        scientificNotationToLong(data["id"].toString())
                    } else {
                           doubleToLong(data["id"].toString())
                           },
                    position = data["position"].toString(),
                    team_id = newTeamId,
                    weight_pounds = if (data["weight_pounds"] == null) {
                        data["weight_pounds"] as Int?
                    } else {
                        doubleToLong(data["weight_pounds"].toString()).toInt()
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

fun scientificNotationToLong(number: String): Long {
    var initial: String = ""
    var beforeE = true
    var exponent: Int = 0

    for (char in number) {

        if (char == 'E') {
            beforeE = false
        }

        else if (beforeE) {
            initial = initial.plus(char)
        }

        else if (!beforeE) {
            exponent = char.digitToInt()
        }

    }
    Log.i("Conversion", "Initial: $initial")
    Log.i("Conversion", "Exponent: $exponent")
    // convert
    val ten = 10
    val doubleTen = ten.toDouble()
    val tenToExponent = doubleTen.pow(exponent)
    val result = initial.toDouble() * tenToExponent

    return result.toLong()

}

fun doubleToLong(double: String): Long {
    // Converts Double of format XXX.0 to Long
    var result: String = ""
    var i = 0

    while (double[i] != '.') {
        result = result.plus(double[i])

        i ++
    }

    return result.toLong()

}
/*
* Function to help convert a string representation of a JSON to a Kotlin map
* From: stackoverflow.com/questions/44870961/how-to-map-a-json-string-to-kotlin-map
*
* Note: Unused as of 7/17/22
* */
private fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
    when (val value = this[it])
    {
        is JSONArray ->
        {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}

private fun mapToTeamEntity(teamMap: Map<String, *>): TeamEntity {
    return TeamEntity(
        id = doubleToLong(teamMap["id"].toString()).toInt(),
        abbreviation = teamMap["abbreviation"].toString(),
        city = teamMap["city"].toString(),
        conference = teamMap["conference"].toString(),
        division = teamMap["division"].toString(),
        full_name = teamMap["full_name"].toString(),
        name = teamMap["name"].toString()
    )
}




