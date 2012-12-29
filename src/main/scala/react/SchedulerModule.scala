package scala.react

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.{ Executors, ExecutorService }
import javax.swing.SwingUtilities

trait SchedulerModule { self: Domain =>


  def schedule(r: Runnable) { engine.schedule(r) }
  def schedule(op: => Unit) { 
    println("scheduling op with engine")
    engine.schedule(op) 
  }

  /**
   * The scheduler is responsible for scheduling propagation turns. Turns are not necessarily run
   * immediately, but are scheduled with `ensureTurnIsScheduled()`.
   */
  abstract class Scheduler {
    /**
     * Ensure that a turn will be started at the discretion of this scheduler.
     * Repeated invocations of this method before the turn is started may not result
     * in additional turns being scheduled.
     */
    def ensureTurnIsScheduled()

    /**
     * Starts this scheduler. Called exactly once before any other method on this
     * scheduler is invoked.
     */
    def start() {
      ensureTurnIsScheduled()
    }
  }

  class ManualScheduler extends Scheduler {
    def ensureTurnIsScheduled() {}
  }

  /**
   * A scheduler with a thread-safe API.
   */
  abstract class ThreadSafeScheduler extends Scheduler {
    private val needsToBeScheduled = new AtomicBoolean(false)
    private val isRunning = new AtomicBoolean(false)
    private val isScheduled = new AtomicBoolean(false)

    private val runnable = new Runnable {
      def run() = {
        println("3. in run of runnable")
        if (isRunning.compareAndSet(false,true)) {
          println("4. isRunnin was false, now is true")
          println("before runTurn")
          isRunning.set(false)
          engine.runTurn()
          println("after runTurn")
          //if(needsToBeScheduled.compareAndSet(true,false)) {
          //  println("needs to be scheduled is now true, so ensuringTurnIsScheduled")
          //  ensureTurnIsScheduled()
          //}
        }
        println(s" -- in turn runnable: isScheduled: ${isScheduled}")
        println(s" -- in turn runnable: isRunning: ${isRunning}")
      }
    }

    def ensureTurnIsScheduled() {
      println("1. ensureTurnIsScheduled running")
      println(s"isScheduled: ${isScheduled}")
      println(s"isRunning: ${isRunning}")
      //if (isScheduled.compareAndSet(false, true)) {
        println("2. isScheduled was false, now is true.  running a new turn.")
        schedule(runnable)
      //} else {
      //  println("isScheduled is true -- needs to be scheduled set to true")
      //  needsToBeScheduled.set(true)
      //}
    }

    /**
     * To be implemented by subclasses.
     *
     * This uses a Runnable and not a Scala closure to avoid wrapping them when interacting
     * with existing Java frameworks.
     */
    protected def schedule(r: Runnable)
  }

  /**
   * A scheduler running turns on a java.util.concurrent thread-pool.
   */
  class ThreadPoolScheduler(pool: ExecutorService) extends ThreadSafeScheduler {
    def this() = this(Executors.newCachedThreadPool())
    protected def schedule(r: Runnable) = pool.execute(r)
  }

  /**
   * A scheduler running turns on the Swing event dispatcher thread (EDT).
   */
  class SwingScheduler extends ThreadSafeScheduler {
    def schedule(r: Runnable) = SwingUtilities.invokeLater(r)
  }
}
