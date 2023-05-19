package com.example.calculator.orientations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.calculator.ActionButton
import com.example.calculator.CalculatorAction
import com.example.calculator.CalculatorOperation
import com.example.calculator.EnterButton
import com.example.calculator.ExpressionViewModel
import com.example.calculator.NumButton
import com.example.calculator.VariantActionButton


@Composable
fun LandscapeCalculator(exprViewModel: ExpressionViewModel){
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)) {
        val (expression, buttons) = createRefs()

        ExpressionBlock(expression = exprViewModel.expression, Modifier.constrainAs(expression){
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            height = Dimension.wrapContent
            width = Dimension.matchParent
            bottom.linkTo(buttons.top)
        })

        Row(Modifier.constrainAs(buttons){
            top.linkTo(expression.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        },
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            ExtensionButtonsBlock(exprViewModel::onAction, modifier = Modifier.weight(1f))
            DefaultButtonsBlock(exprViewModel::onAction, modifier = Modifier.weight(0.7f))

        }


    }
}

@Composable
fun ExtensionButtonsBlock(onAction: (CalculatorAction) -> Unit, modifier: Modifier = Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        verticalArrangement = Arrangement.spacedBy(7.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        modifier = modifier
    ){
        items(30){
            NumButton(text = it.toString(), onClick = { onAction(CalculatorAction.Symbol(it.toString())) }, modifier = Modifier)
        }
    }
}

@Composable
fun DefaultButtonsBlock(onAction: (CalculatorAction) -> Unit, modifier: Modifier = Modifier){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(3f),
            verticalArrangement = Arrangement.spacedBy(7.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            reverseLayout = true,
        ){
            item(span = { GridItemSpan(2) }){ NumButton(text = "0", modifier = Modifier, onClick = {onAction(CalculatorAction.Symbol("0"))})}
            item { NumButton(text = ".", modifier = Modifier, onClick = {onAction(CalculatorAction.Symbol("."))}) }
            items((1..9).toList()){
                NumButton(text = it.toString(), modifier = Modifier, onClick = {onAction(CalculatorAction.Symbol(it.toString()))})
            }
            item{ VariantActionButton(text = "Ac", modifier = Modifier, onClick = { onAction(CalculatorAction.Clear) }) }
            item{ VariantActionButton(text = "<-", modifier = Modifier, onClick = { onAction(CalculatorAction.Delete) }) }
            item{ ActionButton("/", Modifier, onClick = { onAction(CalculatorAction.Operation(
                CalculatorOperation.Divide)) })}

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.weight(1f)
        ){
            ActionButton(text = "*", modifier = Modifier.fillMaxWidth(), onClick = {onAction(
                CalculatorAction.Operation(
                    CalculatorOperation.Multiply)) })
            ActionButton(text = "-", modifier = Modifier.fillMaxWidth(), onClick = {onAction(
                CalculatorAction.Operation(
                    CalculatorOperation.Minus)) })
            ActionButton(text = "+", modifier = Modifier.fillMaxWidth(), onClick = {onAction(
                CalculatorAction.Operation(
                    CalculatorOperation.Plus)) })
            EnterButton(text = "=", modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(.9f),
            onClick = {onAction(CalculatorAction.Calculate)})

        }

    }
}

//@Composable
//fun DefaultButtonsBlock(onAction: (CalculatorAction) -> Unit, modifier: Modifier = Modifier){
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(7.dp)
//
//    ){
//        LazyVerticalGrid(
//            columns =GridCells.Fixed(3),
//            verticalArrangement = Arrangement.spacedBy(7.dp),
//            horizontalArrangement = Arrangement.spacedBy(7.dp),
//            modifier = Modifier.weight(3f)){
//            items(15){
//                NumButton(text = it.toString(), onClick = { onAction(CalculatorAction.Symbol(it.toString())) }, modifier = Modifier)
//            }
//        }
//        Column(
//            Modifier.weight(1f),
//            verticalArrangement = Arrangement.spacedBy(9.dp)
//        ) {
//            ActionButton(text = "*", Modifier.aspectRatio(1.9f), onClick = { onAction(CalculatorAction.Operation(
//                CalculatorOperation.Multiply)) })
//            ActionButton(text = "-", Modifier.aspectRatio(1.9f), onClick = { onAction(CalculatorAction.Operation(
//                CalculatorOperation.Minus)) })
//
//            ActionButton(text = "+", Modifier.aspectRatio(1.5f), onClick = { onAction(CalculatorAction.Operation(
//                CalculatorOperation.Plus)) })
//
//            EnterButton(text = "=",
//                modifier = Modifier.aspectRatio(1f),
//                onClick = { onAction(CalculatorAction.Calculate) })
//        }
//    }
//}