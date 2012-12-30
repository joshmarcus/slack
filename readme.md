Slack
=====

"Slack is the end result of all quests of a carnal or spiritual nature." -- Philo's Historie (The SubGenius Psychlopaedia of Slack)

Slack is a 2D game library written in scala, intended to be a scala port of Slick2D that provides 
functional reactive programming tools for game programming.  

The project has included the source from scala.React, which isn't "production quality" but is interesting.

Important: There are two scripts for downloading the necessary native libraries for Linux and OSX.

At the moment, there is an initial ReactiveGame which implements Slick's BasicGame and a runnable test
that is primarily for initial experiments with scala.React and Slick.

My next goal is to port over the Scrollable demo to Scala, and then to use components from ReactiveGame,
and then port over the Slick libraries it's using.
