package de.compeople.swn

/**
 * Suspended test functions not yet supported, see https://youtrack.jetbrains.com/issue/KT-22228
 * Using the workaround described there
 */
actual fun <T> coRun(block: suspend () -> T) {}