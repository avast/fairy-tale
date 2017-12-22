package com.avast.fairytale.logging

import scala.collection.immutable.Seq

final case class Message(parts: Seq[String], args: Seq[Any])
