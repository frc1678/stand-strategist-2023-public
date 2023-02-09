package ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.files.settings
import ui.navigation.Destination

@OptIn(ExperimentalAnimationApi::class)
val defaultContentTransform = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
    scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)) with
    fadeOut(animationSpec = tween(90))

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentByScreen(
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(Destination) -> Unit
) = AnimatedContent(targetState = settings!!.screen, content = content, modifier = modifier, transitionSpec = {
    when {
        targetState == initialState.back().destination && targetState.next()?.destination == initialState ->
            slideInHorizontally { -it } with slideOutHorizontally { it }

        targetState == initialState.next()?.destination && targetState.back().destination == initialState ->
            slideInHorizontally { it } with slideOutHorizontally { -it }

        else -> defaultContentTransform
    }.using(SizeTransform(clip = false))
})

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedSmallContentByScreen(
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(Destination) -> Unit
) = AnimatedContent(targetState = settings!!.screen, content = content, modifier = modifier, transitionSpec = {
    when {
        targetState == initialState.back().destination && targetState.next()?.destination == initialState ->
            slideInHorizontally { -it / 3 } + fadeIn() with slideOutHorizontally { it / 3 } + fadeOut()

        targetState == initialState.next()?.destination && targetState.back().destination == initialState ->
            slideInHorizontally { it / 3 } + fadeIn() with slideOutHorizontally { -it / 3 } + fadeOut()

        else -> defaultContentTransform
    }.using(SizeTransform(clip = false))
})
