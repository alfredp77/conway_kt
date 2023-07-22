package com.conway.actions

import com.conway.game.GameParameters
import kotlin.test.Test

class QuitActionTest {
    @Test
    fun `should set exit flag to true`() {
        val action = QuitAction()

        val result = action.execute( GameParameters())

        assert(result.exit)
    }

}