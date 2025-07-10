package edu.unibo.tracker.newsAnimation

sealed class Orientation{

    data class Vertical(
        val alignment: VerticalAlignment = VerticalAlignment.TopToBottom,
        val animationStyle: VertAnimationStyle = VertAnimationStyle.ToRight
    ) : Orientation()

    data class Horizontal(
        val alignment: HorizontalAlignment = HorizontalAlignment.StartToEnd,
        val animationStyle: HorAnimationStyle = HorAnimationStyle.FromTop
    ) : Orientation()
}
