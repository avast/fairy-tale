package com.avast.fairytale

package object logging {

  /** Provides string interpolator `log` to make construction of `Message` easier. */
  implicit final class LoggerMessageSyntax(val stringContext: StringContext) extends AnyVal {

    def log(args: Any*): Message = Message(stringContext.parts.toList, args.toList)

  }

}
