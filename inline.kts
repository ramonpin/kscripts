import java.util.concurrent.locks.*

inline fun performHavingLock(lock: Lock, task: () -> Unit) {
  lock.lock()
  try { task() }
  finally { lock.unlock() }
}

performHavingLock(ReentrantLock()) {
  println("Hello world!!!")
}

