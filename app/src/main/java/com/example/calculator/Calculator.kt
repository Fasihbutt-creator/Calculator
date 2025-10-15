//package com.example.calculator
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun Calculator(
//    viewModel: CalculatorViewModel,
//    state: CalculatorState,
//    modifier: Modifier = Modifier,
//    buttonSpacing: Dp = 8.dp,
//    onAction: (CalculatorActions) -> Unit
//) {
//    Box(modifier = modifier) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter),
//            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
//        ) {
//            // Build display text: show full expression (tokens + current input). If result exists, show result.
//            val displayText: String = state.result.ifEmpty {
//                // join tokens with spaces, then append currentInput (with a leading space if needed)
//                val tokensPart =
//                    if (state.tokens.isEmpty()) "" else state.tokens.joinToString(" ") + " "
//                tokensPart + state.currentInput
//            }
//
//
//
//            // Font size state (keep where it is)
//            var currentFont by remember { mutableStateOf(80.sp) }
//            val maxFont = 80.sp
//            val minFont = 20.sp
//
//// horizontal scroll so user can swipe if needed after shrinking
//            val displayScroll = rememberScrollState()
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .horizontalScroll(displayScroll)
//            ) {
//                // auto-scroll to the end whenever displayText changes
//                LaunchedEffect(displayText) {
//                    // animate to the max scroll position so newest chars are visible
//                    displayScroll.animateScrollTo(displayScroll.maxValue)
//                }
//
//                Text(
//                    text = displayText,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.CenterEnd), // keep text anchored to the right
//                    fontWeight = FontWeight.Light,
//                    fontSize = currentFont,
//                    color = Color.Yellow,
//                    // allow wrapping onto multiple lines so height can grow
//                    maxLines = Int.MAX_VALUE,
//                    softWrap = true,
//                    onTextLayout = { textLayoutResult ->
//                        // If text overflows horizontally, shrink the font (step by ~10%)
//                        if (textLayoutResult.didOverflowWidth && currentFont > minFont) {
//                            val next = (currentFont.value * 0.90f).sp
//                            currentFont = if (next < minFont) minFont else next
//                        } else if (!textLayoutResult.didOverflowWidth && currentFont < maxFont) {
//                            // try to scale back up a little when there's room
//                            val grow = (currentFont.value * 1.05f).sp
//                            currentFont = if (grow > maxFont) maxFont else grow
//                        }
//                    }
//                )
//            }          // ---- Rows of buttons (unchanged logic, kept your layout) ----
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
//            ) {
//                calculatorbutton(
//                    "AC",
//                    modifier = Modifier
//                        .weight(2f)
//                        .aspectRatio(2f)
//                        .background(color = Color.Blue),
//                    onClick = { onAction(CalculatorActions.Clear) }
//                )
//
//                calculatorbutton(
//                    "Del",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.Red),
//                    onClick = { onAction(CalculatorActions.Delete) }
//                )
//
//                calculatorbutton(
//                    "/",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.Green),
//                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Divide)) }
//                )
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
//            ) {
//                calculatorbutton(
//                    "7",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(7)) }
//                )
//                calculatorbutton(
//                    "8",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(8)) }
//                )
//                calculatorbutton(
//                    "9",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(9)) }
//                )
//                calculatorbutton(
//                    "x",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(Color.Green),
//                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Multiply)) }
//                )
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
//            ) {
//                calculatorbutton(
//                    "4",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(4)) }
//                )
//                calculatorbutton(
//                    "5",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(5)) }
//                )
//                calculatorbutton(
//                    "6",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(6)) }
//                )
//                calculatorbutton(
//                    symbol = "+",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.Green),
//                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Add)) }
//                )
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
//            ) {
//                calculatorbutton(
//                    "1",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(1)) }
//                )
//                calculatorbutton(
//                    "2",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(2)) }
//                )
//                calculatorbutton(
//                    "3",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(3)) }
//                )
//                calculatorbutton(
//                    "-",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.Green),
//                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Subtract)) }
//                )
//            }
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
//            ) {
//                calculatorbutton(
//                    "0",
//                    modifier = Modifier
//                        .weight(2f)
//                        .aspectRatio(2f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Number(0)) }
//                )
//                calculatorbutton(
//                    ".",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.DarkGray),
//                    onClick = { onAction(CalculatorActions.Decimal) }
//                )
//                calculatorbutton(
//                    "=",
//                    modifier = Modifier
//                        .weight(1f)
//                        .aspectRatio(1f)
//                        .background(color = Color.Green),
//                    onClick = { onAction(CalculatorActions.Calculate) }
//                )
//            }
//        }
//    }
//}




package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calculator(
    viewModel: CalculatorViewModel,
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorActions) -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Build display text: show full expression (tokens + current input). If result exists, show result.
            val displayText: String = state.result.ifEmpty {
                val tokensPart =
                    if (state.tokens.isEmpty()) "" else state.tokens.joinToString(" ") + " "
                tokensPart + state.currentInput
            }

            // Font size state (keep where it is)
            var currentFont by remember { mutableStateOf(80.sp) }
            val maxFont = 80.sp
            val minFont = 20.sp

//            // horizontal scroll so user can swipe if needed after shrinking
//            val displayScroll = rememberScrollState()
//
//            // INPUT DISPLAY box (auto-scroll to end)
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .horizontalScroll(displayScroll)
//                    .heightIn(min = 72.dp)
//                    .padding(horizontal = 8.dp)
//            ) {
//                // auto-scroll to the end whenever displayText changes
//                LaunchedEffect(displayText) {
//                    displayScroll.animateScrollTo(displayScroll.maxValue)
//                }
//
//                Text(
//                    text = displayText,
//                    textAlign = TextAlign.End,
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    fontWeight = FontWeight.Light,
//                    fontSize = currentFont,
//                    color = Color.Yellow,
//                    maxLines = Int.MAX_VALUE,
//                    softWrap = true,
//                    onTextLayout = { textLayoutResult ->
//                        // If text overflows horizontally, shrink the font (step by ~10%)
//                        if (textLayoutResult.didOverflowWidth && currentFont > minFont) {
//                            val next = (currentFont.value * 0.90f).sp
//                            currentFont = if (next < minFont) minFont else next
//                        } else if (!textLayoutResult.didOverflowWidth && currentFont < maxFont) {
//                            // try to scale back up a little when there's room
//                            val grow = (currentFont.value * 1.05f).sp
//                            currentFont = if (grow > maxFont) maxFont else grow
//                        }
//                    }
//                )
//            }

            // ---------- INPUT DISPLAY (replace previous Box that used horizontalScroll directly) ----------
            val displayScroll = rememberScrollState()

// Outer box keeps full width and reserved height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 72.dp)
                    .padding(horizontal = 8.dp)
            ) {
                // Row is horizontally scrollable; arranging content to the end so Text sits on the right.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(displayScroll),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Text uses wrapContentWidth so it takes only the width it needs, then Row aligns it to the right.
                    Text(
                        text = displayText,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Light,
                        fontSize = currentFont,
                        color = Color.Yellow,
                        maxLines = Int.MAX_VALUE,
                        softWrap = true,
                        onTextLayout = { textLayoutResult ->
                            if (textLayoutResult.didOverflowWidth && currentFont > minFont) {
                                val next = (currentFont.value * 0.90f).sp
                                currentFont = if (next < minFont) minFont else next
                            } else if (!textLayoutResult.didOverflowWidth && currentFont < maxFont) {
                                val grow = (currentFont.value * 1.05f).sp
                                currentFont = if (grow > maxFont) maxFont else grow
                            }
                        }
                    )
                }

                // Auto-scroll to end when displayText changes
                LaunchedEffect(displayText) {
                    displayScroll.animateScrollTo(displayScroll.maxValue)
                }
            }

            // ---------- NEW: Preview/result field (always takes space but hidden until needed) ----------
            // Uses the utility functions you created:
            //   hasAtLeastOneOpAndTwoOperands(tokens, currentInput)
            //   previewEvaluate(tokens, currentInput)
            //
            // The preview will update whenever state.tokens or state.currentInput change.
// Instead of local functions, call viewModel's helpers:
            val shouldPreview = viewModel.hasAtLeastOneOpAndTwoOperands(state.tokens, state.currentInput)
            val previewResult: String? = if (shouldPreview) viewModel.previewEvaluate(state.tokens, state.currentInput) else null

//            // Draw the preview Text. It always occupies a small reserved height; when no preview, it's transparent.
//            val previewTextColor = if (!previewResult.isNullOrEmpty()) Color.LightGray else Color.Transparent
//            val previewString = previewResult ?: ""
//
//            Text(
//                text = previewString,
//                textAlign = TextAlign.End,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp)
//                    .heightIn(min = 20.dp), // reserve space even when empty
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = previewTextColor
//            )


            // ---------- PREVIEW (replace previous preview Text) ----------
//            val previewTextColor = if (!previewResult.isNullOrEmpty()) Color.LightGray else Color.Transparent
//            val previewString = previewResult ?: ""
//
//            Text(
//                text = previewString,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp , vertical = 40.dp)
//                    .heightIn(min = 20.dp)
//                    .wrapContentWidth(Alignment.End), // make content hug and place it at right
//                textAlign = TextAlign.End,
//                fontWeight = FontWeight.Normal,
//                fontSize = 40.sp,
//                color = previewTextColor
//            )
            // ---------- PREVIEW (auto-scroll when overflowing) ----------
            val previewTextColor = if (!previewResult.isNullOrEmpty()) Color.LightGray else Color.Transparent
            val previewString = previewResult ?: ""

// scroll state for preview
            val previewScroll = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 20.dp)
                    .padding(horizontal = 12.dp, vertical = 40.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(previewScroll),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = previewString,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Normal,
                        fontSize = 40.sp,
                        color = previewTextColor
                    )
                }

                // auto-scroll to the end whenever previewString changes
                LaunchedEffect(previewString) {
                    if (previewString.isNotEmpty()) {
                        previewScroll.animateScrollTo(previewScroll.maxValue)
                    }
                }
            }



            // ---- Rows of buttons (unchanged logic, kept your layout) ----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                calculatorbutton(
                    "AC",
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(2f)
                        .background(color = Color.Red) ,

                    onClick = { onAction(CalculatorActions.Clear) }
                )

                calculatorbutton(
                    "Del",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Delete) }
                )

                calculatorbutton(
                    "/",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Divide)) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                calculatorbutton(
                    "7",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(7)) }
                )
                calculatorbutton(
                    "8",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(8)) }
                )
                calculatorbutton(
                    "9",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(9)) }
                )
                calculatorbutton(
                    "x",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Multiply)) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                calculatorbutton(
                    "4",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(4)) }
                )
                calculatorbutton(
                    "5",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(5)) }
                )
                calculatorbutton(
                    "6",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(6)) }
                )
                calculatorbutton(
                    symbol = "+",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Add)) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                calculatorbutton(
                    "1",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(1)) }
                )
                calculatorbutton(
                    "2",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(2)) }
                )
                calculatorbutton(
                    "3",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(3)) }
                )
                calculatorbutton(
                    "-",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = { onAction(CalculatorActions.Operation(CalculatorOperation.Subtract)) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                calculatorbutton(
                    "0",
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(2f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Number(0)) }
                )
                calculatorbutton(
                    ".",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(color = Color.DarkGray),
                    onClick = { onAction(CalculatorActions.Decimal) }
                )
                calculatorbutton(
                    "=",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.tertiary),
                    onClick = { onAction(CalculatorActions.Calculate) }
                )
            }
        }
    }
}
