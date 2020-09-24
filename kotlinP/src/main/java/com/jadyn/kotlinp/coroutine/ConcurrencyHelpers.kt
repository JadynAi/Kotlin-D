import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineStart.LAZY
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.yield
import java.util.concurrent.atomic.AtomicReference
import kotlin.DeprecationLevel.ERROR

class SingleRunner {
    private val mutex = Mutex()
    
    /**
     * 顺序执行
     * */
    suspend fun <T> afterPrevious(block: suspend () -> T): T {
        mutex.withLock {
            return block()
        }
    }
}
class ControlledRunner<T> {
    private val activeTask = AtomicReference<Deferred<T>?>(null)

    /**
     * 取消上一个任务，然后再执行
     * */
    suspend fun cancelPreviousThenRun(block: suspend() -> T): T {
        activeTask.get()?.cancelAndJoin()

        return coroutineScope {
            val newTask = async(start = LAZY) {
                block()
            }

            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            val result: T

            while(true) {
                if (!activeTask.compareAndSet(null, newTask)) {
                    // 返回false说明有任务在跑，直接取消掉
                    activeTask.get()?.cancelAndJoin()
                    yield()
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }

    /**
     * 不执行新任务，直接使用上一个任务（如果有的话）
     * */
    suspend fun joinPreviousOrRun(block: suspend () -> T): T {
        activeTask.get()?.let {
            return it.await()
        }
        return coroutineScope {
            val newTask = async(start = LAZY) {
                block()
            }

            newTask.invokeOnCompletion {
                activeTask.compareAndSet(newTask, null)
            }

            val result: T

            while(true) {
                if (!activeTask.compareAndSet(null, newTask)) {
                    val currentTask = activeTask.get()
                    if (currentTask != null) {
                        newTask.cancel()
                        result = currentTask.await()
                        break
                    } else {
                        yield()
                    }
                } else {
                    result = newTask.await()
                    break
                }
            }
            result
        }
    }
}