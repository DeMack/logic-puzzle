package com.github.demack

import java.util.StringJoiner

const val NUM_OF_LAYERS = 5
const val SIZE = NUM_OF_LAYERS + (NUM_OF_LAYERS - 1)
const val ZERO = '0'
const val ONE = '1'

/** Even numbers are 0, odds are 1 */
private val initialChar = if (NUM_OF_LAYERS % 2 == 0) ZERO else ONE

/** Flips to true once the initial half has been completed */
private var directionFlag = false

fun main() {
  println("This is a test")
  val stringJoiner = StringJoiner("\n")

  calcLine(SIZE, stringJoiner)

  print(stringJoiner.toString())
}

fun prepareNextLine(curRowSize: Int, joiner: StringJoiner) {
  if (!directionFlag && curRowSize < 2) directionFlag = true

  if (curRowSize == SIZE && directionFlag) return

  val nextRowSize = if (directionFlag) curRowSize + 2 else curRowSize - 2

  calcLine(nextRowSize, joiner)
}

fun calcLine(curRowSize: Int, joiner: StringJoiner) {
  val fixSize = calcFixSize(curRowSize)
  val lineBuilder = StringBuilder()
  var nextChar = initialChar

  nextChar = appendFix(fixSize, nextChar, lineBuilder)

  nextChar = appendInner(nextChar, curRowSize, lineBuilder)

  appendFix(fixSize, nextChar, lineBuilder)
  joiner.add(lineBuilder)

  prepareNextLine(curRowSize, joiner)
}

fun calcFixSize(curRowSize: Int) = (SIZE - curRowSize) / 2

fun appendFix(outerSize: Int, initialChar: Char, lineBuilder: StringBuilder): Char {
  var curChar = initialChar
  for (i in outerSize downTo 1) {
    curChar = appendFixChar(curChar, lineBuilder)
  }
  return curChar
}

fun appendFixChar(curChar: Char, lineBuilder: StringBuilder): Char {
  lineBuilder.append(curChar)
  return calcNextChar(curChar)
}

fun appendInner(curChar: Char, curRowSize: Int, lineBuilder: StringBuilder): Char {
  for (i in 1..curRowSize) {
    lineBuilder.append(curChar)
  }
  return calcNextChar(curChar)
}

fun calcNextChar(curChar: Char) = if (curChar == ZERO) ONE else ZERO
