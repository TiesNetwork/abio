/**
 * Copyright © 2017 Anton Filatov (ya-enot@mail.ru)
 *
 * This file is part of ABIO.
 *
 * ABIO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ABIO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ABIO.  If not, see <https://www.gnu.org/licenses/lgpl-3.0>.
 */
package one.utopic.abio.api;

import java.io.EOFException;

/**
 * <h1>State table</h1>
 * <p>
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <th>
 * <td>{@link #isOpened() isOpened}</td>
 * <td>{@link #isReady() isReady}</td>
 * <td>{@link #isBusy() isBusy}</td>
 * <td>{@link #isFinished() isFinished}</td>
 * <td>{@link #isClosed() isClosed}</td>
 * <td>{@link #isError() isError}</td></th>
 * <tr>
 * <td>{@link ResourceState#CREATED CREATED}</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#OPENING OPENING}</td>
 * <td>False</td>
 * <td>False</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#OPENED OPENED}</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#READY READY}</td>
 * <td><b>True</b></td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#PROCESSING PROCESSING}</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#FINISHED FINISHED}</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#CLOSING CLOSING}</td>
 * <td>False</td>
 * <td>False</td>
 * <td><b>True</b></td>
 * <td><b>True</b></td>
 * <td>False</td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#CLOSED CLOSED}</td>
 * <td>False</td>
 * <td>False</td>
 * <td>False</td>
 * <td><b>True</b></td>
 * <td><b>True</b></td>
 * <td>False</td>
 * </tr>
 * <tr>
 * <td>{@link ResourceState#ERROR ERROR}</td>
 * <td>?</td>
 * <td>?</td>
 * <td>?</td>
 * <td>?</td>
 * <td>?</td>
 * <td><b>True</b></td>
 * </tr>
 * </table>
 * </blockquote>
 * </p>
 *
 */
public interface Stateful {

    interface State {
	String getName();
    }

    State getState();

    boolean isOpened();

    boolean isReady();

    boolean isFinished();

    boolean isClosed();

    boolean isBusy();

    boolean isError();

    enum ResourceState implements Stateful.State {
	/**
	 * Resource is just created <br/>
	 * <b>Next:</b> {@link #OPENING}, {@link #OPENED}
	 */
	CREATED,

	/**
	 * Resource opening in progress <br/>
	 * <b>Next:</b> {@link #OPENED}
	 */
	OPENING,

	/**
	 * Resource is opened successfully <br/>
	 * <b>Next:</b> {@link #READY}
	 */
	OPENED,

	/**
	 * Resource is ready for processing <br/>
	 * <b>Next:</b> {@link #PROCESSING}, {@link #FINISHED}, {@link #CLOSED}
	 */
	READY,

	/**
	 * Resource is waiting for IO operations to be completed <br/>
	 * <b>Next:</b> {@link #READY}, {@link #FINISHED}, {@link #CLOSING},
	 * {@link #CLOSED}
	 */
	PROCESSING,

	/**
	 * Resource is at its logical end. <br/>
	 * Any IO attempt in this state should throw an {@link EOFException} <br/>
	 * <b>Next:</b> {@link #READY}, {@link #CLOSING}, {@link #CLOSED}
	 */
	FINISHED,

	/**
	 * Resource closing in progress <br/>
	 * <b>Next:</b> {@link #CLOSED}
	 */
	CLOSING,

	/**
	 * Resource is closed successfully <br/>
	 * <b>Next:</b> {@link #DESTROYED}
	 */
	CLOSED,

	/**
	 * Resource is freed and ready for being garbage collected <br/>
	 */
	DESTROYED,

	/**
	 * Resource is broken and/or its state could not be determined <br/>
	 */
	ERROR;

	public String getName() {
	    return name();
	}
    }
}
