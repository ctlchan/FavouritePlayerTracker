package com.example.favouriteplayertracker.data.repository.userList

import android.util.Log
import com.example.favouriteplayertracker.data.local.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserListDao
import com.example.favouriteplayertracker.data.remote.NbaApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException
import kotlin.math.pow

class UserListRepositoryImpl(
    private val dao: UserListDao,
    private val nbaApi: NbaApi
): UserListRepository {

    private val TAG = "UserListRepoImpl"


    override fun getFavouritePlayers(): Flow<List<FavouritePlayer>> {
        return dao.getAllPlayers()
    }

    override suspend fun addFavouritePlayer(name: String) {

        // Check if they're already being tracked before making API request.
        if (!dao.playerExists(name)) {

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

                data["team"] = data["team"] as Map<*, *>
                Log.i(TAG, data["team"].toString())

                Log.i(TAG, data.toString())

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
                    team_name = data["team"].toString(),
                    weight_pounds = if (data["weight_pounds"] == null) {
                        data["weight_pounds"] as Int?
                    } else {
                        doubleToLong(data["weight_pounds"].toString()).toInt()
                    },
                )

                dao.addPlayer(newPlayer)

            }

        }
    }

    override suspend fun removeFavouritePlayer(player: FavouritePlayer) {
            dao.deletePlayer(player)
    }

    override suspend fun unselectPlayer() {
        dao.unselect()
    }

    override suspend fun reselectPlayer(name: String) {
        dao.reselect(name)
    }

    override fun getSelected(): Flow<String> {
        return dao.getSelected()
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


