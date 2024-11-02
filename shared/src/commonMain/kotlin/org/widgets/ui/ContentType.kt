package org.widgets.ui

enum class ContentType {
    LIST_1,
    LIST_2;

    val title: String
        get() = when (this) {
            LIST_1 -> "Movies"
            LIST_2 -> "Distributors"
        }

    val testTag: String
        get() = name.lowercase()

    companion object {
        val sortedValues: List<ContentType> = entries.toList()
    }
}