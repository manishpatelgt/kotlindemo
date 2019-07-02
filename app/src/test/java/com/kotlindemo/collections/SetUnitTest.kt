package com.kotlindemo.collections

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/05_Collections/02_Set
//A set is an unordered collection that does not support duplicates
class SetUnitTest {

    val openIssues: MutableSet<String> = mutableSetOf("uniqueDescr1", "uniqueDescr2", "uniqueDescr3") // 1

    fun addIssue(uniqueDesc: String): Boolean {
        return openIssues.add(uniqueDesc)                                                             // 2
    }

    fun getStatusLog(isAdded: Boolean): String {
        return if (isAdded) "registered correctly." else "marked as duplicate and rejected."          // 3
    }

    @Test
    fun main(){

        val aNewIssue: String = "uniqueDescr4"
        val anIssueAlreadyIn: String = "uniqueDescr2"

        println("Issue $aNewIssue ${getStatusLog(addIssue(aNewIssue))}")                              // 4
        println("Issue $anIssueAlreadyIn ${getStatusLog(addIssue(anIssueAlreadyIn))}")
    }
}