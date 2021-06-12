package com.xmartlabs.gong.domain.usecase

import com.xmartlabs.gong.domain.usecase.common.FlowCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.milliseconds

/**
 * Created by mirland on 27/04/20.
 */
class TimeTrackerUseCase(
    dispatcher: CoroutineDispatcher
) : FlowCoroutineUseCase<TimeTrackerUseCase.Params, Duration>(dispatcher) {
  data class Params(val startTime: Date)

  override fun execute(params: Params): Flow<Duration> = flow {
    while (true) {
      @Suppress("MagicNumber")
      delay(1800)
      val duration = Duration.milliseconds((Date().time - params.startTime.time))
      println("Duration: ${duration.toDouble(DurationUnit.MILLISECONDS)} in millis")
      emit(duration)
    }
  }
}
