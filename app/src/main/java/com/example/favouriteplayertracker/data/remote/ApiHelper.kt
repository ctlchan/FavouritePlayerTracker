package com.example.favouriteplayertracker.data.remote

import android.util.Log
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.pow

class ApiHelper {

    companion object {

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
        fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
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

        fun mapToTeamEntity(teamMap: Map<String, *>): TeamEntity {
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

    }

}