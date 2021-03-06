package com.chibatching.kotpref.pref

import com.chibatching.kotpref.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class LongPrefTest : BasePrefTest() {

    @Test
    fun longPrefDefaultIs0() {
        assertThat(example.testLong).isEqualTo(0L)
    }

    @Test
    fun setValueToLongPref() {
        example.testLong = 32942L
        assertThat(example.testLong).isEqualTo(32942L)
        assertThat(example.testLong).isEqualTo(examplePref.getLong("testLong", 0L))
    }

    @Test
    fun customDefaultValue() {
        assertThat(customExample.testLong).isEqualTo(Long.MAX_VALUE)
    }

    @Test
    fun useCustomPreferenceKey() {
        customExample.testLong = 296201L
        assertThat(customExample.testLong).isEqualTo(296201L)
        assertThat(customExample.testLong).isEqualTo(customPref.getLong(context.getString(R.string.test_custom_long), 0L))
    }
}
