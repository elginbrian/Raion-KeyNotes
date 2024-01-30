package com.raion.keynotes.util

import com.raion.keynotes.model.GetNoteResponse
import com.raion.keynotes.model.NoteItem

fun processNoteList(rawNoteList: GetNoteResponse?): List<NoteItem>{
    var noteList: List<NoteItem>

    noteList =
        if (rawNoteList != null) {
            rawNoteList.data
        } else {
            listOf()
        }
    return noteList
}