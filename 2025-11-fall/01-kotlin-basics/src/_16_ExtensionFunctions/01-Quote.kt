package _16_ExtensionFunctions.someAppendix

import _16_ExtensionFunctions.doubleQuote
import _16_ExtensionFunctions.singleQuote
import utils.eq

fun main() {
  "Single".singleQuote() eq "'Single'"
  "Double".doubleQuote() eq "\"Double\""
}