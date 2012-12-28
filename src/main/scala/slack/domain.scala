package slack

import scala.react.Domain

object slackdom extends SlackDomain

class SlackDomain extends Domain {
  var engine = new Engine
  val scheduler = new ThreadPoolScheduler
}


