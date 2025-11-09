package com.example.vocabularymemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val database by lazy { (application as VocabularyMemo).database }
    private val wordDao by lazy { database.wordDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            var inputWord by remember { mutableStateOf("") }
            var inputMeaning by remember { mutableStateOf("") }
            var allWords by remember { mutableStateOf(listOf<WordEntity>()) }

            fun refreshWords() {
                scope.launch {
                    allWords = wordDao.getAll()
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                BasicTextField(
                    value = inputWord,
                    onValueChange = { inputWord = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                BasicTextField(
                    value = inputMeaning,
                    onValueChange = { inputMeaning = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    val newWord = WordEntity(word = inputWord, meaning = inputMeaning)
                    scope.launch {
                        wordDao.insert(newWord)
                        inputWord = ""
                        inputMeaning = ""
                        refreshWords()
                    }
                }) {
                    Text("登録")
                }
                Spacer(modifier = Modifier.height(16.dp))
                allWords.forEach { word ->
                    Text("${word.word} : ${word.meaning}")
                }
            }

            LaunchedEffect(Unit) {
                refreshWords()
            }
        }
    }
}