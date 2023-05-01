package com.example.calculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.LightBlue
import com.example.calculator.ui.theme.popinsFontFamily


class MainActivity : ComponentActivity() {
    private val exprViewModel: ExpressionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculatorTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.padding(28.dp, 8.dp)) {
                        ExpessionBlock(exprViewModel)
                        ButtonsBlock()
                    }
                }
            }
        }

    }
}


@Composable
fun ButtonsBlock() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        horizontalArrangement = Arrangement.spacedBy(17.dp),
        userScrollEnabled = false
    ){
        items(listOf("e", "u", "sin", "deg")) {
            SpecialActionButton(text = it)
        }
    }
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 17.dp)) {
        Column(
            modifier = Modifier
            .weight(3f, true),
        ) {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(17.dp) ,
                verticalArrangement = Arrangement.spacedBy(17.dp),
                reverseLayout = true,
                columns = GridCells.Fixed(3),
                userScrollEnabled = false
            ) {

                items((1..9).toList()){
                    NumButton(it.toString(), Modifier.aspectRatio(1f))
                }
                item{ NumButton("Ac", Modifier.aspectRatio(1f)) }
                item{ NumButton("<-", Modifier.aspectRatio(1f)) }
                item{ActionButton("/", Modifier.aspectRatio(1f))}

            }
            LazyHorizontalGrid(
                modifier = Modifier.padding(top = 17.dp),
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(17.dp),
                userScrollEnabled = false
            ){
                item {
                    NumButton(text = "0", Modifier.width(160.dp))
                }
                item {
                    NumButton(text = ".", Modifier.width(70.dp))
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(start = 14.dp),
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            userScrollEnabled = false
            ) {
            items(listOf("*", "-")){
                ActionButton(text = it.toString(), Modifier.size(70.dp))
            }
            item {
                ActionButton(text = "+", Modifier.size(70.dp, 130.dp))
            }
            item {
                ActionButton(text = "=", Modifier.size(70.dp, 106.dp))
            }
        }
    }
}


@Composable
fun ExpessionBlock(viewModel: ExpressionViewModel){
    Column(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
    horizontalAlignment = Alignment.End) {
        History(viewModel.expressions.collectAsState())
        Expression("100 + 200 = 500")
    }
}

@Composable
fun History(history: State<List<Expression>>){
//    val expressions = (1..10).map { it.toString() }.toList()
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        reverseLayout = true,
        userScrollEnabled = false
        ) {
            itemsIndexed(history.value){ index, expression ->
                HistoryExpression(text = expression.body, index = index)
            }
//        itemsIndexed(expressions){index, expr ->
//            HistoryExpression(text = expr, index = index)
//        }
    }

}

@Composable
fun HistoryExpression(text: String, index: Int = 1){
    Text(
        text = text,
        fontSize = 24.sp,
        fontFamily = popinsFontFamily,

        color = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .alpha(1f / index)
            .padding(top = 10.dp)
    )
}

@Composable
fun Expression(text: String){
    Text(
        text = text,
        fontSize = 35.sp,
        color = MaterialTheme.colorScheme.onPrimary,
        fontFamily = popinsFontFamily,
        fontStyle = FontStyle.Normal,
        modifier = Modifier
            .padding(vertical = 17.dp)
    )
}





