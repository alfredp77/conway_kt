import com.conway.actions.MenuAction
import com.conway.actions.QuitAction
import com.conway.game.BasicGameRunner
import com.conway.game.GameController
import com.conway.game.GameParameters
import com.conway.inputProcessors.InputGridSizeProcessor
import com.conway.inputProcessors.InputLiveCellsProcessor
import com.conway.inputProcessors.InputNumberOfGenerationsProcessor
import com.conway.inputProcessors.RunProcessor
import com.conway.tools.ConsoleLiveCellsPrinter
import com.conway.tools.ConsoleUserInputOutput

fun main() {
    val userInputOutput = ConsoleUserInputOutput()
    val inputGridSize = MenuAction(userInputOutput, InputGridSizeProcessor())
    val inputGenerations = MenuAction(userInputOutput, InputNumberOfGenerationsProcessor())
    val inputLiveCells = MenuAction(userInputOutput, InputLiveCellsProcessor())
    val run = MenuAction(userInputOutput, RunProcessor(BasicGameRunner(), ConsoleLiveCellsPrinter()))
    val quit = QuitAction()
    val menuActions = listOf(inputGridSize, inputGenerations, inputLiveCells, run, quit)
    val controller = GameController(userInputOutput, menuActions)
    controller.run(GameParameters())
}