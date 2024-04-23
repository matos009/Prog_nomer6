package commands;

import exceptions.CommandRuntimeError;
import exceptions.ExitObliged;
import exceptions.IllegalArgumentsException;
import rrr.Request;
import rrr.Response;

public interface Executable {
    Response execute(Request request)throws CommandRuntimeError, ExitObliged, IllegalArgumentsException;
}
