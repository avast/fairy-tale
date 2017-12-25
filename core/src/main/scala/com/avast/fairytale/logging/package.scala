package com.avast.fairytale

package object logging {


  implicit final class LoggerMessageSyntax(val stringContext: StringContext) extends AnyVal {

    def log(args: Any*): Message = Message(stringContext.parts.toList, args.toList)

  }

}
