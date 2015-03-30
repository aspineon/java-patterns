/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.wandrell.testing.pattern.test.unit.command.executor.method;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wandrell.pattern.command.Command;
import com.wandrell.pattern.command.CommandExecutor;
import com.wandrell.pattern.command.DefaultCommandExecutor;
import com.wandrell.pattern.command.ReturnCommand;

/**
 * Unit tests for {@link DefaultCommandExecutor}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>When a {@code Command} is received by the {@code execute} method it is
 * executed.</li>
 * <li>When a {@code ReturnCommand} is received by the {@code execute} method it
 * is executed.</li>
 * <li>When a {@code ReturnCommand} is executed the object generated by it is
 * returned.</li>
 * </ol>
 * 
 * @author Bernardo Martínez Garrido
 * @see DefaultCommandExecutor
 */
public final class TestExecuteDefaultCommandExecutor {

    /**
     * Executor being tested.
     * <p>
     * It is created once for all the tests.
     */
    private CommandExecutor executor;

    /**
     * Default constructor.
     */
    public TestExecuteDefaultCommandExecutor() {
        super();
    }

    /**
     * Creates the executor being tested before any test is run.
     */
    @BeforeClass
    public final void initialize() {
        executor = new DefaultCommandExecutor();
    }

    /**
     * Tests that when a {@code Command} is received by the {@code execute}
     * method, it is executed.
     * 
     * @throws Exception
     *             never, this is just a required declaration
     */
    @Test
    public final void testExecute_Command_Executed() throws Exception {
        final Command command;  // The command used for the test

        command = Mockito.mock(Command.class);

        executor.execute(command);

        Mockito.verify(command, Mockito.times(1)).execute();
    }

    /**
     * Tests that when a {@code ReturnCommand} is received by the
     * {@code execute} method it is executed.
     * 
     * @throws Exception
     *             never, this is just a required declaration
     */
    @Test
    public final void testExecute_ReturnCommand_Executed() throws Exception {
        final ReturnCommand<?> command; // The command used for the test

        command = Mockito.mock(ReturnCommand.class);

        executor.execute(command);

        Mockito.verify(command, Mockito.times(1)).execute();
    }

    /**
     * Tests that when a {@code ReturnCommand} is executed the object generated
     * by it is returned.
     * 
     * @throws Exception
     *             never, this is just a required declaration
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testExecute_ReturnCommand_ReturnsExpected()
            throws Exception {
        final ReturnCommand<Boolean> command;   // The command used for the test

        command = Mockito.mock(ReturnCommand.class);

        Mockito.when(command.getResult()).thenReturn(true);

        Assert.assertTrue(executor.execute(command));
    }

}
