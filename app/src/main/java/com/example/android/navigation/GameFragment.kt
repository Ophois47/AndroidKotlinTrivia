/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one. We randomize the answers before showing the text.
    // All questions must have four answers. We'd want these to contain references to string
    // resources so we could internationalize.(or better yet, not define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "What is Android Jetpack?",
                    answers = listOf("all of these", "tools", "documentation", "libraries")),
            Question(text = "Base class for Layout?",
                    answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            Question(text = "Layout for complex Screens?",
                    answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            Question(text = "Pushing structured data into a Layout?",
                    answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")),
            Question(text = "Inflate layout in fragments?",
                    answers = listOf("onCreateView", "onActivityCreated", "onCreateLayout", "onInflateLayout")),
            Question(text = "Build system for Android?",
                    answers = listOf("Gradle", "Griddle", "Grodle", "Groyle")),
            Question(text = "Android vector format?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Android Navigation Component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Registers app with launcher?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "Mark a layout for Data Binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")),
            Question(text = "What are the return values of onStartCommand() in android services?",
                    answers = listOf("All of the above", "START_REDELIVER_INTENT", "START_NOT_STICKY", "START_STICKY")),
            Question(text = "What is log message in android for?",
                    answers = listOf("Used to debug a program", "Same as printf", "Same as Toast()", "None of the above")),
            Question(text = "What is the package name of JSON?",
                    answers = listOf("org.json", "com.android.JSON", "in.json", "json.bloemker")),
            Question(text = "What are the JSON elements in android?",
                    answers = listOf("Number, string, boolean, null, array, object", "null", "boolean", "integer, boolean")),
            Question(text = "Can a class be immutable in android?",
                    answers = listOf("Yes", "No", "Can't make class as final class", "I ain't got no class")),
            Question(text = "Which media format is not supported by Android?",
                    answers = listOf("AVI", "MP4", "MPEG", "FLV")),
            Question(text = "In which directory are XML layout files stored?",
                    answers = listOf("/res/layout", "/src", "/assets", "/res/values")),
            Question(text = "Which Android code is NOT open source?",
                    answers = listOf("WiFi Driver", "Bluetooth Driver", "Device Driver", "Video Driver")),
            Question(text = "How many levels of securities are in Android?",
                    answers = listOf("App and Kernal Level", "Java Level", "Android Level", "None of the Above")),
            Question(text = "Which of the following do not belong to transitions?",
                    answers = listOf("ViewSlider", "ViewSwitcher", "ViewAnimator", "ViewFlipper")),
            Question(text = "What are the functionalities in AyncTask?",
                    answers = listOf("OnPostExecution()", "OnProgressUpdate()", "OnPreExecution", "DoInBackground()")),
            Question(text = "What does AAPT stand for?",
                    answers = listOf("Android Asset Packaging Tool", "Android Asset Providing Tool", "Android Asset Processing Tool", "Android Asset Will of the Dragon")),
            Question(text = "View Pager is used for?",
                    answers = listOf("Swiping Fragments", "Paging Down List Items", "Swiping Activities", "Not Supported By Android SDK")),
            Question(text = "What is JNI?",
                    answers = listOf("Java Native Interface", "Java Network Interface", "Java Interface", "Jello Nougat Icing")),
            Question(text = "Adb stands for what?",
                    answers = listOf("Android Debug Bridge", "Android Drive Bridge", "Android Delete Bridge", "Android Delicious Bridge")),
            Question(text = "Which programming languages are used for Android Application Development?",
                    answers = listOf("Java and Kotlin", "React and JSX", "NodeJs", "C#")),
            Question(text = "What is the program that converts Java byte code into Dalvik byte code?",
                    answers = listOf("Dex Compiler", "Dalvik Converter", "AIC-Android Interpretive Compiler", "MIC-Mobile Interpretive Compiler")),
            Question(text = "Why is Android based on Linux?",
                    answers = listOf("All of these", "Security", "Networking", "Portability")),
            Question(text = "When would contentProvider  be activated?",
                    answers = listOf("when using ContentResolver", "when using Intent", "when using SQLite", "None of these")),
            Question(text = "An activity can be thought of as corresponding to what?",
                    answers = listOf("A Java class", "An object field", "A method call", "A Java project")),
            Question(text = "Which one is NOT related to fragment class?",
                    answers = listOf("CursorFragment", "PreferenceFragment", "DialogFragment", "ListFragment")),
            Question(text = "Which of these is NOT a part of Android’s native libraries?",
                    answers = listOf("Dalvik", "OpenGL", "Webkit", "SQLite")),
            Question(text = "During Activity life-cycle, what is first callback method invoked?",
                    answers = listOf("onCreate()", "onRestore()", "onStart()", "onStop")),
            Question(text = "Requests from Content Provider class are handled by what method?",
                    answers = listOf("ContentResolver", "onSelectC", "onCreate", "onClick"))
    )

    //Questions Template

    //      Question(text = "",
    //              answers = listOf("", "", "", ""))



    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment2())
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
