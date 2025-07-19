package com.backcube.data.common.utils

infix fun <T> Collection<T>.sameContentWith(other: Collection<T>): Boolean =
    this.size == other.size && this.containsAll(other)

infix fun <T> Collection<T>.notSameContentWith(other: Collection<T>): Boolean =
    this.size != other.size || !this.containsAll(other)