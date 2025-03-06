package mobile.app.moviesdemo

interface Event

data class NavigateEvent(val id: Long) : Event
data class ShowSnackbarEvent(val message: String) : Event
