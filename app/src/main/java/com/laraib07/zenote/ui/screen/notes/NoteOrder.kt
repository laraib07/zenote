package com.laraib07.zenote.ui.screen.notes

sealed class NoteOrder(val orderType: OrderType) {
    class TITLE(orderType: OrderType) : NoteOrder(orderType)
    class MODIFIED(orderType: OrderType) : NoteOrder(orderType)
    class CREATED(orderType: OrderType) : NoteOrder(orderType)
}

