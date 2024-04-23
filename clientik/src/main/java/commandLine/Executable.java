package commandLine;

import exceptions.CommandRuntimeError;
import exceptions.ExitObliged;
import exceptions.IllegalArgumentsException;

public interface Executable {

     void execute(String args) throws CommandRuntimeError, ExitObliged, IllegalArgumentsException;
}
