package com.example.quizapp.ui

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizapp.R
import com.example.quizapp.model.Question
import com.example.quizapp.utils.Constants

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgress: TextView
    private lateinit var textViewQuestion: TextView
    private lateinit var flagImage: ImageView

    private lateinit var textViewOptionOne: TextView
    private lateinit var textViewOptionTwo: TextView
    private lateinit var textViewOptionThree: TextView
    private lateinit var textViewOptionFour: TextView

    private lateinit var checkButton: Button

    private lateinit var questionsList: MutableList<Question>
    private var questionsCounter = 0
    private lateinit var currentQuestion: Question
    private var answered = false

    private var selectedAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        progressBar = findViewById(R.id.progress_bar)
        textViewProgress = findViewById(R.id.progress_text_view)
        textViewQuestion = findViewById(R.id.question_text_view)
        flagImage = findViewById(R.id.image_flag)
        checkButton = findViewById(R.id.check_button)

        textViewOptionOne = findViewById(R.id.text_view_option_one)
        textViewOptionTwo = findViewById(R.id.text_view_option_two)
        textViewOptionThree = findViewById(R.id.text_view_option_three)
        textViewOptionFour = findViewById(R.id.text_view_option_four)

        textViewOptionOne.setOnClickListener(this)
        textViewOptionTwo.setOnClickListener(this)
        textViewOptionThree.setOnClickListener(this)
        textViewOptionFour.setOnClickListener(this)
        checkButton.setOnClickListener(this)


        questionsList = Constants.getQuestions()
        Log.d("QuestionSize", "${questionsList.size}")

        showNextQuestion()
    }

    private fun showNextQuestion() {
        resetOptions()
        val question = questionsList[questionsCounter]
        flagImage.setImageResource(question.image)
        progressBar.progress = questionsCounter
        textViewProgress.text = "$questionsCounter/${progressBar.max}"
        textViewQuestion.text = question.question
        textViewOptionOne.text = question.optionOne
        textViewOptionTwo.text = question.optionTwo
        textViewOptionThree.text = question.optionThree
        textViewOptionFour.text = question.optionFour

        if (questionsCounter < questionsList.size) {
            checkButton.text = "CHECK"
            currentQuestion = questionsList[questionsCounter]
        } else {
            checkButton.text = "FINISH"
        }

        questionsCounter++
        answered = false
    }

    private fun resetOptions() {
        val options = mutableListOf<TextView>()
        options.add(textViewOptionOne)
        options.add(textViewOptionTwo)
        options.add(textViewOptionThree)
        options.add(textViewOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOption(textView: TextView, selectedOptionNumber: Int) {
        resetOptions()

        selectedAnswer = selectedOptionNumber
        textView.setTextColor(Color.parseColor("#363A43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun checkAnswer() {
        answered = true
        Log.d("Selected/Correct", "${selectedAnswer}/${currentQuestion.correctAnswer}")
        if (selectedAnswer == currentQuestion.correctAnswer) {
            when (selectedAnswer) {
                1 -> {
                    textViewOptionOne.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                }
                2 -> {
                    textViewOptionTwo.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                }
                3 -> {
                    textViewOptionThree.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                }
                4 -> {
                    textViewOptionFour.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                }
            }
        } else {
            when (selectedAnswer) {
                1 -> {
                    textViewOptionOne.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                }
                2 -> {
                    textViewOptionTwo.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                }
                3 -> {
                    textViewOptionThree.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                }
                4 -> {
                    textViewOptionFour.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                }
            }
        }

        checkButton.text = "NEXT"
        showSolution()
    }

    private fun showSolution() {
        selectedAnswer = currentQuestion.correctAnswer
        when (selectedAnswer) {
            1 -> {
                textViewOptionOne.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            }
            2 -> {
                textViewOptionTwo.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            }
            3 -> {
                textViewOptionThree.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            }
            4 -> {
                textViewOptionFour.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.text_view_option_one -> {
                selectedOption(textViewOptionOne, 1)
            }
            R.id.text_view_option_two -> {
                selectedOption(textViewOptionTwo, 2)
            }
            R.id.text_view_option_three -> {
                selectedOption(textViewOptionThree, 3)
            }
            R.id.text_view_option_four -> {
                selectedOption(textViewOptionFour, 4)
            }
            R.id.check_button -> {
                if (!answered) {
                    checkAnswer()
                } else {
                    showNextQuestion()
                }
                selectedAnswer = 0
            }
        }
    }
}