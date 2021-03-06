package nustorage.logic.parser;

import static nustorage.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static nustorage.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nustorage.logic.commands.AddCommand;
import nustorage.logic.commands.AddFinanceCommand;
import nustorage.logic.commands.AddInventoryRecordCommand;
import nustorage.logic.commands.ClearCommand;
import nustorage.logic.commands.Command;
import nustorage.logic.commands.DeleteCommand;
import nustorage.logic.commands.DeleteFinanceCommand;
import nustorage.logic.commands.DeleteInventoryRecordCommand;
import nustorage.logic.commands.EditCommand;
import nustorage.logic.commands.EditFinanceCommand;
import nustorage.logic.commands.EditInventoryCommand;
import nustorage.logic.commands.ExitCommand;
import nustorage.logic.commands.FindCommand;
import nustorage.logic.commands.FindInventoryRecordCommand;
import nustorage.logic.commands.HelpCommand;
import nustorage.logic.commands.ListCommand;
import nustorage.logic.commands.ListFinanceRecordsCommand;
import nustorage.logic.commands.ListInventoryCommand;
import nustorage.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class NuStorageParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case ListInventoryCommand.COMMAND_WORD:
            return new ListInventoryCommand();

        case AddInventoryRecordCommand.COMMAND_WORD:
            return new AddInventoryRecordCommandParser().parse(arguments);

        case EditInventoryCommand.COMMAND_WORD:
            return new EditInventoryCommandParser().parse(arguments);

        case DeleteInventoryRecordCommand.COMMAND_WORD:
            return new DeleteInventoryRecordCommandParser().parse(arguments);

        case FindInventoryRecordCommand.COMMAND_WORD:
            return new FindInventoryRecordCommandParser().parse(arguments);

        case AddFinanceCommand.COMMAND_WORD:
            return new AddFinanceCommandParser().parse(arguments);

        case EditFinanceCommand.COMMAND_WORD:
            return new EditFinanceCommandParser().parse(arguments);

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteFinanceCommand.COMMAND_WORD:
            return new DeleteFinanceCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ListFinanceRecordsCommand.COMMAND_WORD:
            return new ListFinanceRecordsCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
