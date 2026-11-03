package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import com.google.firebase.ai.type.ThinkingLevel
import com.google.firebase.ai.type.generationConfig
import com.google.firebase.ai.type.thinkingConfig

class ExampleUnitTest {
  @Test
  fun testThinkingConfig() {
    val config = generationConfig {
      thinkingConfig = thinkingConfig {
        thinkingLevel = ThinkingLevel.HIGH
      }
    }
    assertNotNull(config)
  }
}
