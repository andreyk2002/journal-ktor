package com.jetbrains.handson.website.entity

val inMemoryEntries = mutableListOf(
    JournalEntry("First entry", "Here could be you add!")
)

data class JournalEntry(val headline : String, val description : String)
