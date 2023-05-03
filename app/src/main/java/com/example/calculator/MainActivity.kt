package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.MainTheme
import com.example.calculator.ui.theme.popinsFontFamily


class MainActivity : ComponentActivity() {
    private val exprViewModel: ExpressionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainTheme() {
                Surface(
                    color = CalculatorTheme.colors.background
                ) {
                    ConstraintLayout(Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 10.dp)) {

                        val (history, expression, buttons) = createRefs()
                        HistoryBlock(history = exprViewModel.expressions.collectAsState(), Modifier.constrainAs(history){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(expression.top)
                            height = Dimension.fillToConstraints
                            width = Dimension.matchParent
                        })
                        ExpressionBlock(expression = exprViewModel.expression, Modifier.constrainAs(expression){
                            top.linkTo(history.bottom)
                            end.linkTo(parent.end)
                            height = Dimension.wrapContent
                            width = Dimension.matchParent
                            bottom.linkTo(buttons.top)
                        })
                        ButtonsBlock(exprViewModel::onAction, Modifier.constrainAs(buttons){
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.wrapContent
                            width = Dimension.matchParent
                        })
                    }
                }
            }
        }

    }//
}

@Composable
fun ButtonsBlock(onAction: (CalculatorAction) -> Unit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Bottom, modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            userScrollEnabled = false
        ){
            items(listOf("(", ")", "^2", "deg")) {
                SpecialActionButton(text = it, onClick = { onAction(CalculatorAction.Symbol(it)) })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)) {
            Column(
                modifier = Modifier
                    .weight(3f),
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(end = 6.dp),
                    reverseLayout = true,
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    userScrollEnabled = false
                ) {

                    items((1..9).toList()){
                        NumButton(it.toString(), Modifier.aspectRatio(1f), onClick = {
                            onAction(CalculatorAction.Symbol(it.toString()))
                        })}
                    item{ VariantActionButton(text = "Ac", modifier = Modifier.aspectRatio(1f), onClick = { onAction(CalculatorAction.Clear) }) }
                    item{ VariantActionButton(text = "<-", modifier = Modifier.aspectRatio(1f), onClick = { onAction(CalculatorAction.Delete) }) }
                    item{ActionButton("/", Modifier.aspectRatio(1f), onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) })}

                }
                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ){
                    NumButton(text = "0",
                        Modifier
                            .weight(2f)
                            .aspectRatio(2.4f), onClick = { onAction(CalculatorAction.Symbol("0")) })
                    NumButton(text = ".",
                        Modifier
                            .weight(1f)
                            .aspectRatio(1.2f), onClick = { onAction(CalculatorAction.Symbol(".")) })
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 11.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                ActionButton(text = "*", Modifier.aspectRatio(1f), onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) })
                ActionButton(text = "-", Modifier.aspectRatio(1f), onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Minus)) })

                ActionButton(text = "+", Modifier.aspectRatio(.9f, true), onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Plus)) })

                EnterButton(text = "=",
                    modifier = Modifier
                        .aspectRatio(.55f, true),
                    onClick = { onAction(CalculatorAction.Calculate) })
            }
        }
    }


}




@Composable
fun HistoryBlock(history: State<List<Expression>>, modifier: Modifier = Modifier){
//    val expressions = (1..10).map { it.toString() }.toList()
    LazyColumn(
        Modifier.then(modifier),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        reverseLayout = true,
        ) {
            itemsIndexed(history.value){ index, expression ->
                HistoryItem(text = expression.body, index = index+1)
            }
//        itemsIndexed(expressions){index, expr ->
//            HistoryExpression(text = expr, index = index)
//        }
    }

}

@Composable
fun HistoryItem(text: String, index: Int = 1){
    Text(
        text = text,
        fontSize = 24.sp,
        fontFamily = popinsFontFamily,

        color = CalculatorTheme.colors.secondaryText,
        modifier = Modifier
            .alpha(1f / index)
            .padding(top = 10.dp)
    )
}

@Composable
fun ExpressionBlock(expression: String, modifier: Modifier = Modifier){
    Text(
        text = expression,
        fontSize = 35.sp,
        overflow = TextOverflow.Ellipsis,
        color = CalculatorTheme.colors.primaryText,
        fontFamily = popinsFontFamily,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.End,
        modifier = Modifier.then(modifier)
            .padding(vertical = 17.dp)
    )
}





