package edu.unibo.tracker.newsAnimation


import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val rotationValue = 45f
private const val defAnimationDuration = 300

private val defPaddingItems = 8.dp
private val defCardElevation = 4.dp

@Composable
fun CardStack(
    cardContent : @Composable (Int)->Unit,
    cardCount : Int,
    cardElevation : Dp = defCardElevation,
    paddingBetCards : Dp = defPaddingItems,
    animationDuration : Int = defAnimationDuration,
    cardShape: Shape = MaterialTheme.shapes.medium,
    cardColor: Color = MaterialTheme.colors.background,
    cardBorder: BorderStroke? = null,
    onCardClick: ((Int)->Unit)? = null,
    orientation: Orientation = Orientation.Vertical()
    ){
    checkCardCount(cardCount)

    checkPadding(paddingBetCards)

    checkAnimationDuration(animationDuration = animationDuration)

    val runAnimations = animationDuration > 0
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val contentAlignment = getContentAlignment(orientation)
    val rotationValue = getRotation(orientation)

    Box(
        contentAlignment = contentAlignment
    ) {
        (0 until cardCount).forEachIndexed { index, _ ->
            ShowCard(
                coroutineScope = coroutineScope,
                runAnimations = runAnimations,
                selectedIndex = selectedIndex,
                index = index,
                cardCount = cardCount,
                paddingBetweenCards = paddingBetCards,
                animationDuration = animationDuration,
                rotationValue = rotationValue,
                orientation = orientation,
                cardElevation = cardElevation,
                cardColor = cardColor,
                cardShape = cardShape,
                cardBorder = cardBorder,
                onCardClick = onCardClick,
                composable = { cardContent(index) },
                newIndexBlock = { selectedIndex = it }
            )
        }
    }



}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowCard(
    coroutineScope: CoroutineScope,
    runAnimations: Boolean,
    selectedIndex: Int,
    index: Int,
    cardCount: Int,
    paddingBetweenCards: Dp,
    animationDuration: Int,
    rotationValue: Float,
    orientation: Orientation,
    cardElevation: Dp,
    cardShape: Shape,
    cardColor :Color,
    cardBorder: BorderStroke?,
    onCardClick: ((Int) -> Unit)? = null,
    composable: @Composable (Int) -> Unit,
    newIndexBlock: (Int) -> Unit
) {

    var itemPxSize = 0

    val padding = when {
        selectedIndex == index -> 0.dp
        selectedIndex < index -> ((index - selectedIndex) * paddingBetweenCards.value).dp
        selectedIndex > index -> ((cardCount - selectedIndex + index) * paddingBetweenCards.value).dp
        else -> throw IllegalStateException()
    }

    val paddingAnimation by animateDpAsState(padding, tween(animationDuration, easing = FastOutSlowInEasing))
    val offsetAnimation = remember { Animatable(0f) }
    val rotateAnimation = remember { Animatable(0f) }

    val offsetValues = when (orientation) {
        is Orientation.Vertical -> {
            IntOffset(
                if (orientation.animationStyle == VertAnimationStyle.ToRight)
                    offsetAnimation.value.toInt()
                else
                    -offsetAnimation.value.toInt(), 0
            )
        }
        is Orientation.Horizontal -> {
            IntOffset(
                0, if (orientation.animationStyle == HorAnimationStyle.FromTop)
                    -offsetAnimation.value.toInt()
                else
                    offsetAnimation.value.toInt()
            )
        }
    }

    val paddingModifier = when {
        orientation is Orientation.Vertical && orientation.alignment == VerticalAlignment.TopToBottom -> PaddingValues(top = paddingAnimation)
        orientation is Orientation.Vertical && orientation.alignment == VerticalAlignment.BottomToTop -> PaddingValues(bottom = paddingAnimation)
        orientation is Orientation.Horizontal && orientation.alignment == HorizontalAlignment.StartToEnd -> PaddingValues(start = paddingAnimation)
        else -> PaddingValues(end = paddingAnimation)
    }

    val modifier = Modifier
        .padding(paddingModifier)
        .zIndex(-padding.value)
        .offset { offsetValues }
        .rotate(rotateAnimation.value)
        .onSizeChanged {
            itemPxSize = if (orientation is Orientation.Vertical) {
                if (itemPxSize > it.width)
                    itemPxSize
                else
                    it.width
            } else {
                if (itemPxSize > it.height)
                    itemPxSize
                else
                    it.height
            }
        }

    Card(elevation = cardElevation,
        shape = cardShape,
        modifier = modifier,
        border = cardBorder,
        backgroundColor = cardColor,
        onClick =  {
            if(cardCount > 1 && selectedIndex == index) {
                onCardClick?.invoke(index)
                animateOnClick(coroutineScope, itemPxSize, runAnimations, animationDuration, rotationValue, index, cardCount, offsetAnimation, rotateAnimation, newIndexBlock)
            }
        }
    ) {
        composable.invoke(index)
    }

}

fun animateOnClick(
    coroutineScope: CoroutineScope,
    pxValue: Int,
    runAnimations: Boolean,
    animationDuration: Int,
    rotationValue: Float,
    index: Int,
    cardCount: Int,
    offsetAnimation: Animatable<Float, AnimationVector1D>,
    rotateAnimation: Animatable<Float, AnimationVector1D>,
    newIndexBlock: (Int) -> Unit
) {

    val spec: TweenSpec<Float> = tween(animationDuration, easing = FastOutLinearInEasing)

    coroutineScope.launch {
        if (runAnimations)
            offsetAnimation.animateTo(pxValue.toFloat(), spec)

        val newIndex = if (cardCount > index + 1)
            index + 1
        else
            0

        newIndexBlock.invoke(newIndex)

        if(runAnimations) {
            rotateAnimation.animateTo(rotationValue, spec)
            launch { rotateAnimation.animateTo(0f, spec) }
            launch { offsetAnimation.animateTo(0f, spec) }
        }
    }

}


private fun checkCardCount(cardCount:Int){
    if(cardCount<2)
        throw IllegalArgumentException("Can't use1or less card count.")
}
private fun checkPadding(paddingBetweenCards: Dp) {
    if (paddingBetweenCards <= 0.dp)
        throw IllegalArgumentException("Can't use 0 or less for padding between cards.")
}

private fun checkAnimationDuration(animationDuration: Int) {
    if (animationDuration < 1)
        throw java.lang.IllegalArgumentException("Can't use 0 or less for animation duration.")
}

private fun getContentAlignment(orientation: Orientation): Alignment {
    return when (orientation) {
        is Orientation.Vertical -> {
            if (orientation.alignment == VerticalAlignment.TopToBottom)
                Alignment.TopCenter
            else
                Alignment.BottomCenter
        }
        is Orientation.Horizontal -> {
            if (orientation.alignment == HorizontalAlignment.StartToEnd)
                Alignment.CenterStart
            else
                Alignment.CenterEnd
        }
    }
}

private fun getRotation(orientation: Orientation): Float {
    return when (orientation) {
        is Orientation.Vertical -> {
            if (orientation.animationStyle == VertAnimationStyle.ToRight)
                rotationValue
            else
                -rotationValue
        }

        is Orientation.Horizontal -> {
            if (orientation.animationStyle == HorAnimationStyle.FromTop)
                -rotationValue
            else
                rotationValue
        }
    }
}
