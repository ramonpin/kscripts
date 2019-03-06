@file:DependsOn("io.arrow-kt:arrow-data:0.6.1" )
@file:DependsOn("com.google.guava:guava:24.0-jre")

import arrow.data.*
import com.google.common.eventbus.EventBus
import sun.misc.Signal
import sun.misc.SignalHandler
import java.util.concurrent.atomic.AtomicBoolean

// -----------------------------------------------------------------------------------
// Event bus for main events that affect the general execution of a workflow
private object MainEventBus: EventBus("main-events")

// All known events go here...
abstract class MirindaMainEvent {
  abstract val description: String
  @Suppress("MemberVisibilityCanBePrivate")
  data class Kill(val signal: Int): MirindaMainEvent() {
    override val description: String = "killed with signal $signal"
  }
}

// Utility functions
fun registerToMainEvents(subscriber: Any) = MainEventBus.register(subscriber)
fun unregisterToMainEvents(subscriber: Any) = MainEventBus.unregister(subscriber)

// Handle kill signals: converts them to KillEvents in the bus
private object KillSignalHandler: SignalHandler {
  private val handled = AtomicBoolean(false)
  fun postMainEvent(event: MirindaMainEvent) = MainEventBus.post(event)
  fun install(signalName: String): SignalHandler = Signal.handle(Signal(signalName), this)
  override fun handle(signal: Signal) {
    print("\r                        \r") // clean the line
    if(!handled.getAndSet(true)) {
      println("""Mirinda has recieved a kill(${signal.number}) and is going to gracefully shutdown.
                |This will take sometime, please wait until proper finalization.""".trimMargin())
      postMainEvent(MirindaMainEvent.Kill(signal.number))
    }
  }

}

KillSignalHandler.install("INT")
KillSignalHandler.install("TERM")
// -----------------------------------------------------------------------------------
tailrec fun Process.nonblockingWaitFor(delay: Long): Int {
  Thread.sleep(delay)
  val status = Try { this.exitValue() }
  println("status: $status")
  return when(status) {
    is Success -> status.getOrDefault { 0 }
    is Failure -> this.nonblockingWaitFor(delay)
  }
}

val p: Process = ProcessBuilder().command("sleep", "20").start()
val res = p.nonblockingWaitFor(1000)
println("Finished with $res")
