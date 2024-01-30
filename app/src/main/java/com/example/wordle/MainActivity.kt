package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.color


object FourLetterWordList {
    // List of most common 4 letter words from: https://7esl.com/4-letter-words/
    val fourLetterWords =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

    // Returns a list of four letter words as a list
    fun getAllFourLetterWords(): List<String> {
        return fourLetterWords.split(",")
    }

    // Returns a random four letter word from the list in all caps
    fun getRandomFourLetterWord(): String {
        val allWords = getAllFourLetterWords()
        val randomNumber = (0..allWords.size).shuffled().last()
        return allWords[randomNumber].uppercase()
    }

}



class MainActivity : AppCompatActivity() {

    private lateinit var checkNum : TextView
    private lateinit var checkCorrect : TextView
    private val wordToGuess = FourLetterWordList.getRandomFourLetterWord()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Guess the Word"
        // Setting up
        // Support variables
        val answer        = findViewById<TextView>(R.id.textView17)
        val clearEditText = findViewById<EditText>(R.id.input)
        val button        = findViewById<Button>(R.id.guess)
        val clearButton   = findViewById<Button>(R.id.resetButton)
        val congrat = findViewById<Button>(R.id.congratButton)
        var i             = 0

        answer.text       = wordToGuess
        button.setOnClickListener {
            val userInput = findViewById<EditText>(R.id.input).text.toString().uppercase()

            // Error handling
            userInput.forEach {
                if (it.isDigit()) {
                    Toast.makeText(this, "Please enter only letters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Error handling
            userInput.length.let {
                if (it != 4) {
                    Toast.makeText(this, "Please enter 4 letters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            when (i)
            {   // update the id to match the rhs
                0 -> {
                    checkNum = findViewById(R.id.textView7)
                    checkCorrect = findViewById(R.id.textView8)
                    i++
                }
                1 -> {
                    checkNum = findViewById(R.id.textView9)
                    checkCorrect = findViewById(R.id.textView10)

                    i++
                }
                2 -> {
                    checkNum = findViewById(R.id.textView11)
                    checkCorrect = findViewById(R.id.textView12)
                    answer.visibility = View.VISIBLE
                    button.isClickable = false
                    button.setTextColor(ContextCompat.getColor(this, R.color.black))
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                    Toast.makeText(applicationContext,"You have no more guess!",Toast.LENGTH_SHORT).show()
                    i++
                }
                3 -> {
                    i++
                }
            }
            // update the right hand side of our app
            // and clear text after each submission
            clearEditText.text.clear()
            checkNum.text = userInput

            val s2 = SpannableStringBuilder()
            for (k in 0..3)
            {
                if (userInput[k] == wordToGuess[k])
                {
                    s2.color(Color.GREEN) { append(userInput[k]) }
                }
                else if (wordToGuess.contains(userInput[k]))
                {
                    s2.color(Color.BLUE) { append(userInput[k]) }
                }
                else
                {
                    s2.color(Color.RED) { append(userInput[k]) }
                }
            }
            checkCorrect.text = s2

            if (!button.isClickable)
            {
                clearButton.visibility = View.VISIBLE
            }

            if (userInput == wordToGuess)
            {
                Toast.makeText(applicationContext,"You win!",Toast.LENGTH_SHORT).show()
                clearButton.visibility = View.VISIBLE
                congrat.visibility = View.VISIBLE
            }
        }

        clearButton.setOnClickListener {
            finish()
            startActivity(intent)
            overridePendingTransition(0, 1)
            Toast.makeText(applicationContext,"Game Reset!",Toast.LENGTH_SHORT).show()
            clearButton.visibility = View.INVISIBLE

        }
    }
}

    /**
     * Parameters / Fields:
     * N/a (void)
     * Returns: Hiding the keyboard after the user has submitted their guess
     */
